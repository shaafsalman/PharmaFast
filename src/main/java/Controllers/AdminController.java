package Controllers;

import Dao.CategoryDao;
import Dao.ProductDao;
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

    private final CategoryDao categoryDao;
    private final ProductDao productDao;
    private final UtilityFunctions uFunctions;



    public AdminController() throws SQLException
    {
        this.categoryDao = new CategoryDao();
        this.uFunctions = new UtilityFunctions();
        this.productDao = new ProductDao();

    }

    public boolean editCategory(Category updatedCategory) {return categoryDao.editCategoryName(updatedCategory.getCategoryID(), updatedCategory.getCategoryName());}
    public boolean addCategory(Category newCategory) {
        return categoryDao.addCategory(newCategory.getCategoryName());
    }
    public boolean deleteCategory(int categoryID) {
        return categoryDao.deleteCategory(categoryID);
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
    public boolean getCategoryData(Map<Integer, String> categoryData) {
        return categoryDao.getCategoryData(categoryData);
    }
    public int getCategoryIDByName(String name){return categoryDao.getCategoryIDByName(name);}
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
    public boolean addProduct(Product product) {return productDao.addProduct(product);}
    public boolean updateProduct(Product product) {return productDao.updateProduct(product);}
    public boolean deleteProduct(int productId) {return productDao.deleteProduct(productId);}
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


    public static void main(String[] args) {
        //String yearlyReport = generateReport("yearly", "2023");
        //String monthlyReport = generateReport("monthly", "2023-10");
        //String dailyReport = generateReport("daily", "2023-10-15");

    }



}




