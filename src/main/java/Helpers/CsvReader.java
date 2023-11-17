package Helpers;

import Dao.CategoryDao;
import Dao.ProductDao;
import Models.Product;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class CsvReader
{
    ProductDao  productDao = new ProductDao();
    CategoryDao categoryDao = new CategoryDao();


    public String processCSVFile(File file) {
        StringBuilder errorLog = new StringBuilder();

        String logFilePath = createLogFilePath();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean headerSkipped = false;

            while ((line = br.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }

                String[] data = line.split(",");

                if (data.length < 6) {
                    errorLog.append("Invalid CSV format. Some columns are missing.\n");
                    continue;
                }

                try {
                    Product product = createProductFromCSV(data);
                    processProduct(product, errorLog);
                } catch (NumberFormatException e) {
                    errorLog.append("Invalid numeric format in the CSV.\n");
                } catch (IllegalArgumentException e) {
                    errorLog.append("Invalid Date format in the CSV.\n");
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
            errorLog.append("Error processing the file.\n");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath))) {
            writer.write(errorLog.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return logFilePath;
    }
    private Product createProductFromCSV(String[] data) {

        String productName = data[0];
        double costPrice = Double.parseDouble(data[1]);
        double sellingPrice = Double.parseDouble(data[2]);
        int quantity = Integer.parseInt(data[3]);
        int categoryID = Integer.parseInt(data[4]);
        Date expiryDate = parseDate(data[5]);

        return new Product(0, productName, costPrice, sellingPrice, quantity, categoryID, expiryDate);
    }
    private void processProduct(Product product, StringBuilder errorLog) {
        if (! categoryDao.isCategoryExists(product.getCategoryID())) {
            errorLog.append("CategoryID ").append(product.getCategoryID()).append(" does not exist in the Category table.\n");
            return;
        }

        int productID = productDao.getProductID(product.getProductName(), product.getExpiryDate());
        if (productID != -1) {
            boolean quantityUpdated = productDao.updateProductQuantity(productID, product.getQuantity());
            if (!quantityUpdated) {
                errorLog.append("Failed to update quantity for product: ").append(product.getProductName()).append("\n");
            } else {
                errorLog.append("Product: ").append(product.getProductName()).append(" details have been updated.\n");
            }
        } else {
            boolean productAdded = productDao.addProduct(product);
            if (!productAdded) {
                errorLog.append("Failed to add product: ").append(product.getProductName()).append("\n");
            } else {
                errorLog.append("Product: ").append(product.getProductName()).append(" details have been added.\n");
            }
        }
    }
    private Date parseDate(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            java.util.Date utilDate = dateFormat.parse(dateString);
            return new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    private String createLogFilePath() {
        String directory = "src/main/resources/StockLogs";
        File folder = new File(directory);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String date = LocalDate.now().toString();
        String logFileName = "Log_" + date + ".txt";

        return directory + "\\" + logFileName;
    }
    public void readProductLogFile(JTable table, String filePath) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                model.addRow(new Object[]{line});
            }
        } catch (IOException e) {
            e.printStackTrace();
            model.addRow(new Object[]{"Error reading " + filePath + ": " + e.getMessage()});
        }

        table.setModel(model);

        class HeaderRenderer extends DefaultTableCellRenderer {
            public HeaderRenderer() {
                setHorizontalAlignment(SwingConstants.CENTER);
            }
        }
        table.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        table.setRowHeight(40);
        table.setOpaque(false);
        table.setShowGrid(false);
        table.setBackground(new Color(255, 255, 255));
        table.setSelectionBackground(new Color(52, 52, 52));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Century Gothic", Font.BOLD, 18));
        header.setBackground(new Color(47, 47, 47));
        header.setForeground(Color.darkGray);
        header.setBorder(new EmptyBorder(20, 10, 20, 10));

        TableCellRenderer headerRenderer = new HeaderRenderer();
        table.getTableHeader().setDefaultRenderer(headerRenderer);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
    }

}
