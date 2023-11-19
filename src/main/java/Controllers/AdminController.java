package Controllers;

import Dao.CategoryDao;
import Dao.ProductDao;
import Helpers.AdminConfig;
import Helpers.ConnectionFile;
import Helpers.UtilityFunctions;
import Models.Category;
import Models.Product;
import com.itextpdf.text.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.Font;
import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class AdminController {

    private  CategoryDao categoryDao;
    private  ProductDao productDao;
    private  UtilityFunctions uFunctions;
    private static final String CONFIG_FILE = "adminConfig.ser";
    private static AdminConfig adminConfig = new AdminConfig(5.0f, 1122); // Default values

    public static void saveConfig() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CONFIG_FILE))) {
            oos.writeObject(adminConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadConfig() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CONFIG_FILE))) {
            adminConfig = (AdminConfig) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void setVatRate(float newVatRate) {
        adminConfig.setVatRate(newVatRate);
        saveConfig(); // Save changes
    }

    public static float getVatRate() {
        return adminConfig.getVatRate();
    }

    public static void setAdminCode(int newAdminCode) {
        adminConfig.setAdminCode(newAdminCode);
        saveConfig(); // Save changes
    }

    public static int getAdminCode() {
        return adminConfig.getAdminCode();
    }



    public AdminController() throws SQLException
    {
        this.categoryDao = new CategoryDao();
        this.productDao = new ProductDao();
        this.uFunctions = new UtilityFunctions();
    }

    public AdminController(CategoryDao categoryDao, ProductDao productDao)
    {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
        this.uFunctions = new UtilityFunctions();
    }




    public boolean addCategory(Category newCategory) {
        if (newCategory.getCategoryName() == null || newCategory.getCategoryName().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Category name cannot be empty.");
            return false;
        }
        return categoryDao.addCategory(newCategory.getCategoryName());
    }
    public boolean editCategory(Category updatedCategory) {
        if (updatedCategory.getCategoryName() == null || updatedCategory.getCategoryName().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Category name cannot be empty.");
            return false;
        }
        return categoryDao.editCategoryName(updatedCategory.getCategoryID(), updatedCategory.getCategoryName());
    }
    public boolean deleteCategory(int categoryID) {
        return categoryDao.deleteCategory(categoryID);
    }
    public boolean getCategoryData(Map<Integer, String> categoryData) {
        return categoryDao.getCategoryData(categoryData);
    }
    public int getCategoryIDByName(String name) {
        return categoryDao.getCategoryIDByName(name);
    }




    // Product-related methods
    public boolean addProduct(Product product) {
        if (!isValidProduct(product)) {
            return false;
        }
        return productDao.addProduct(product);
    }
    public boolean updateProduct(Product product) {
        if (!isValidProduct(product)) {
            return false;
        }
        return productDao.updateProduct(product);
    }
    public boolean deleteProduct(int productId) {
        return productDao.deleteProduct(productId);
    }
    private boolean isValidProduct(Product product) {
        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Product name cannot be empty.");
            return false;
        }
        if (product.getSellingPrice() <= product.getCostPrice()) {
            JOptionPane.showMessageDialog(null, "Selling price must be greater than cost price.");
            return false;
        }
        if (product.getQuantity() < 0) {
            JOptionPane.showMessageDialog(null, "Quantity cannot be negative.");
            return false;
        }
        return true;
    }





    public void initializeStockTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        try {
            ResultSet resultSet = productDao.getProductsTotalQuantityGreaterThanZero();
            while (resultSet.next()) {
                String productName = resultSet.getString("ProductName");
                String category = resultSet.getString("CategoryName");
                int quantity = resultSet.getInt("TotalQuantity");

                model.addRow(new Object[]{productName, category, quantity});
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        table.setModel(model);
        uFunctions.initializeUForTable(table);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
    }
    public void initializeDeadStockTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        try {
            ResultSet resultSet =productDao.getProductsZeroQuantity();
            while (resultSet.next()) {
                String productName = resultSet.getString("ProductName");
                String category = resultSet.getString("CategoryName");
                int quantity = resultSet.getInt("Quantity");

                model.addRow(new Object[]{productName, category, quantity});
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        table.setModel(model);
        uFunctions.initializeUForTable(table);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
    }
    public void initializeProductsTable(JTable table) {

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        try {

            ResultSet resultSet = productDao.getAllProductData();
            model.setRowCount(0);
            while (resultSet.next()) {
                Object[] rowData = {
                        resultSet.getInt("ProductID"),
                        resultSet.getString("CategoryName"),
                        resultSet.getString("ProductName"),
                        resultSet.getDouble("Price"),
                        resultSet.getDouble("SellingPrice"),
                        resultSet.getInt("Quantity"),
                        resultSet.getDate("ExpiryDate")

                };
                model.addRow(rowData);
            }
            table.setModel(model);
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        table.setModel(model);
        uFunctions.initializeUForTable(table);

    }
    public void initializeCategoryTable(JTable table) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("ID");
        model.addColumn("Category");
        model.addColumn("Products");

        Map<Integer, String> categoryData = new HashMap<>();
        if (categoryDao.getCategoryData(categoryData))
        {
            for (Map.Entry<Integer, String> entry : categoryData.entrySet())
            {
                int productCount = categoryDao.getNumberOfProductsInCategory(entry.getKey());
                model.addRow(new Object[]{entry.getKey(), entry.getValue(), productCount});
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Database Connection Not Found");
        }

        table.setModel(model);
        uFunctions.initializeUForTable(table);
        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(400);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);

    }

    public static void main(String[] args) {
        //String yearlyReport = generateReport("yearly", "2023");
        //String monthlyReport = generateReport("monthly", "2023-10");
        //String dailyReport = generateReport("daily", "2023-10-15");

    }



}




