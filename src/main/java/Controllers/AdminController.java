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


    //////////////////////////////////////////////////////////////////////////////////////////////
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

  /////////////////////////////////////////////////////////////////////////////////////////////




//
//    public boolean addCustomer(String custUsername, String custEmail, String custAddress) {
//        String insertSql = "INSERT INTO customers (customer_id, name, email, address) VALUES (?, ?, ?, ?)";
//
//        try (Connection connection = DriverManager.getConnection(url, username, password);
//             PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
//
//            int newCustomerId = getMaxCustomerId() + 1;
//
//            // Set the values in the prepared statement
//            insertStatement.setInt(1, newCustomerId); // Customer ID
//            insertStatement.setString(2, custUsername); // Name
//            insertStatement.setString(3, custEmail); // Email
//            insertStatement.setString(4, custAddress); // Address
//
//            insertStatement.executeUpdate();
//            return true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//    public int getMaxCustomerId() {
//        String sql = "SELECT MAX(customer_id) FROM customers";
//        try (Connection connection = DriverManager.getConnection(url, username, password);
//             PreparedStatement statement = connection.prepareStatement(sql);
//             ResultSet resultSet = statement.executeQuery()) {
//
//            if (resultSet.next()) {
//                return resultSet.getInt(1);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
//    public void setupCustomerComboBox(JComboBox<String> comboBox) {
//        String sql = "SELECT name FROM customers";
//        try (Connection connection = DriverManager.getConnection(url, username, password);
//             Statement statement = connection.createStatement();
//             ResultSet resultSet = statement.executeQuery(sql)) {
//
//            while (resultSet.next()) {
//                String customerName = resultSet.getString("name");
//                comboBox.addItem(customerName);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    public void setupBookComboBox(JComboBox<String> comboBox) {
//        String sql = "SELECT title FROM books";
//        try (Connection connection = DriverManager.getConnection(url, username, password);
//             Statement statement = connection.createStatement();
//             ResultSet resultSet = statement.executeQuery(sql)) {
//
//            while (resultSet.next()) {
//                String bookTitle = resultSet.getString("title");
//                comboBox.addItem(bookTitle);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    public void inititlazeCustomertable(JTable table) {
//        try (Connection connection = DriverManager.getConnection(url, username, password)) {
//            String sql = "SELECT * FROM customers";
//            PreparedStatement statement = connection.prepareStatement(sql);
//            ResultSet resultSet = statement.executeQuery();
//
//            DefaultTableModel model = (DefaultTableModel) table.getModel();
//            model.setRowCount(0); // Clear the table before populating it with new data
//
//            while (resultSet.next()) {
//                int customer_id = resultSet.getInt("customer_id");
//                String name = resultSet.getString("name");
//                String email = resultSet.getString("email");
//                String address = resultSet.getString("address");
//
//                model.addRow(new Object[]{customer_id, name, email, address});
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    public boolean addBook(String title, String author, double price, int stock) {
//        int newBookId = getMaxBookId() + 1;
//
//        String sql = "INSERT INTO books (book_id, title, author, price, stock) VALUES (?, ?, ?, ?, ?)";
//        try (Connection connection = DriverManager.getConnection(url, username, password);
//             PreparedStatement statement = connection.prepareStatement(sql)) {
//
//            statement.setInt(1, newBookId); // Set the new book ID
//            statement.setString(2, title);
//            statement.setString(3, author);
//            statement.setDouble(4, price);
//            statement.setInt(5, stock);
//
//            int rowsInserted = statement.executeUpdate();
//            return rowsInserted > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//    public void inititlazeBookstable(JTable table) {
//        String sql = "SELECT * FROM books";
//        try (Connection connection = DriverManager.getConnection(url, username, password);
//             Statement statement = connection.createStatement();
//             ResultSet resultSet = statement.executeQuery(sql)) {
//
//            DefaultTableModel model = (DefaultTableModel) table.getModel();
//            model.setRowCount(0);
//
//            while (resultSet.next()) {
//                int id = resultSet.getInt("book_id");
//                String title = resultSet.getString("title");
//                String author = resultSet.getString("author");
//                double price = resultSet.getDouble("price");
//                int stock = resultSet.getInt("stock");
//
//                model.addRow(new Object[]{id, title, author, price, stock});
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    public int getMaxBookId() {
//        String sql = "SELECT MAX(book_id) FROM books";
//        try (Connection connection = DriverManager.getConnection(url, username, password);
//             PreparedStatement statement = connection.prepareStatement(sql);
//             ResultSet resultSet = statement.executeQuery()) {
//
//            if (resultSet.next()) {
//                return resultSet.getInt(1);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
//    public void initializeOrderHistoryTable(JTable table) {
//        DefaultTableModel model = (DefaultTableModel) table.getModel();
//        model.setRowCount(0);
//
//        try (Connection connection = DriverManager.getConnection(url, username, password)) {
//            String sql = "SELECT o.order_id, c.name AS customer_name, o.order_date, c.address AS customer_address, o.total " +
//                    "FROM orders o " +
//                    "JOIN customers c ON o.customer_id = c.customer_id";
//            PreparedStatement statement = connection.prepareStatement(sql);
//            ResultSet resultSet = statement.executeQuery();
//
//            while (resultSet.next()) {
//                int orderId = resultSet.getInt("order_id");
//                String customerName = resultSet.getString("customer_name");
//                String orderDate = resultSet.getString("order_date");
//                String customerAddress = resultSet.getString("customer_address");
//                double total = resultSet.getDouble("total");
//
//                model.addRow(new Object[]{orderId, customerName, orderDate, customerAddress, total});
//            }
//
//            table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//                @Override
//                public void valueChanged(ListSelectionEvent e) {
//                    if (!e.getValueIsAdjusting()) {
//                        int selectedRow = table.getSelectedRow();
//                        if (selectedRow != -1) {
//                            int orderId = (int) model.getValueAt(selectedRow, 0); // Retrieve order_id
//                            displayOrderDetails(orderId);
//                        }
//                    }
//                }
//            });
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    public void displayOrderDetails(int orderId) {
//        try (Connection connection = DriverManager.getConnection(url, username, password)) {
//            String sql = "SELECT od.book_id, od.quantity, od.price, b.title FROM order_details od " +
//                    "JOIN books b ON od.book_id = b.book_id " +
//                    "WHERE od.order_id = ?";
//            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.setInt(1, orderId);
//            ResultSet resultSet = statement.executeQuery();
//
//            StringBuilder orderDetails = new StringBuilder();
//            while (resultSet.next()) {
//                int bookId = resultSet.getInt("book_id");
//                int quantity = resultSet.getInt("quantity");
//                double price = resultSet.getDouble("price");
//                String title = resultSet.getString("title");
//
//                orderDetails.append("Book ID: ").append(bookId).append(", Title: ").append(title)
//                        .append(", Quantity: ").append(quantity).append(", Price: ").append(price).append("\n");
//            }
//
//            if (orderDetails.length() > 0) {
//                JOptionPane.showMessageDialog(null, orderDetails.toString(), "Order Details", JOptionPane.INFORMATION_MESSAGE);
//            } else {
//                JOptionPane.showMessageDialog(null, "No details found for this order", "Order Details", JOptionPane.INFORMATION_MESSAGE);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    public double getBookPrice(String bookTitle) {
//        // Query the database to get the price of the selected book
//        String sql = "SELECT price FROM books WHERE title = ?";
//        try (Connection connection = DriverManager.getConnection(url, username, password);
//             PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setString(1, bookTitle);
//            ResultSet resultSet = statement.executeQuery();
//
//            if (resultSet.next()) {
//                return resultSet.getDouble("price");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return 0.0;
//    }
//    public int getCustomerIdByName(String customerName) {
//        int customerId = 0;
//
//        String sql = "SELECT customer_id FROM customers WHERE name = ?";
//        try (Connection connection = DriverManager.getConnection(url, username, password);
//             PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setString(1, customerName);
//            ResultSet resultSet = statement.executeQuery();
//
//            if (resultSet.next()) {
//                customerId = resultSet.getInt("customer_id");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return customerId;
//    }
//    public int getBookIdByTitle(String bookTitle) {
//        int bookId = 0;
//
//        String sql = "SELECT book_id FROM books WHERE title = ?";
//        try (Connection connection = DriverManager.getConnection(url, username, password);
//             PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setString(1, bookTitle);
//            ResultSet resultSet = statement.executeQuery();
//
//            if (resultSet.next()) {
//                bookId = resultSet.getInt("book_id");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return bookId;
//    }
//    public int getMaxOrderId() {
//        String sql = "SELECT MAX(order_id) FROM orders";
//        try (Connection connection = DriverManager.getConnection(url, username, password);
//             PreparedStatement statement = connection.prepareStatement(sql);
//             ResultSet resultSet = statement.executeQuery()) {
//
//            if (resultSet.next()) {
//                return resultSet.getInt(1); // Return the maximum order ID
//            }
//        } catch (SQLException e) {
//            e.printStackTrace(); // Handle or log the exception as needed
//        }
//        return 0;
//    }
//    public boolean saveOrderToDatabase(String customerName, ArrayList<OrderDetail> orderDetails, double totalAmount) {
//        try (Connection connection = DriverManager.getConnection(url, username, password)) {
//            connection.setAutoCommit(false); // Start a database transaction
//
//            int customerId = getCustomerIdByName(customerName);
//            int newOrderId = getMaxOrderId() + 1; // Get the next available order ID
//
//            String insertOrderSql = "INSERT INTO orders (order_id, customer_id, order_date, total) VALUES (?, ?, GETDATE(), ?)";
//            try (PreparedStatement orderStatement = connection.prepareStatement(insertOrderSql)) {
//                orderStatement.setInt(1, newOrderId);
//                orderStatement.setInt(2, customerId);
//                orderStatement.setDouble(3, totalAmount);
//                orderStatement.executeUpdate();
//
//                // Insert the order details into the order_details table
//                String insertOrderDetailsSql = "INSERT INTO order_details (order_id, book_id, quantity, price) VALUES (?, ?, ?, ?)";
//                try (PreparedStatement detailsStatement = connection.prepareStatement(insertOrderDetailsSql)) {
//                    for (OrderDetail detail : orderDetails) {
//                        int bookId = getBookIdByTitle(detail.getBookTitle());
//                        detailsStatement.setInt(1, newOrderId);
//                        detailsStatement.setInt(2, bookId);
//                        detailsStatement.setInt(3, detail.getQuantity());
//                        detailsStatement.setDouble(4, detail.getBookPrice());
//                        detailsStatement.executeUpdate();
//                    }
//                }
//
//                connection.commit(); // Commit the transaction
//                return true;
//            } catch (SQLException e) {
//                e.printStackTrace();
//                connection.rollback(); // Rollback the transaction if any SQL error occurs
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
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
            e.printStackTrace(); // Handle the exception appropriately in a real application
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




