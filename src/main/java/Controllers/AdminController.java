package Controllers;

import Helpers.ConnectionFile;
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
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.time.format.DateTimeFormatter;

public class AdminController {

    // JDBC URL, username, and password of SQL Server
    private static final String url = ConnectionFile.dbURL;
    private static final String username =  ConnectionFile.DBusername;
    private static final String password = ConnectionFile.DBpassword;

    Connection connection = DriverManager.getConnection(url, username, password);

    public AdminController() throws SQLException
    {
    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    public boolean getCategoryData(Map<Integer, String> categoryData) {
        categoryData.clear(); // Clear the map to ensure it's empty before populating

        try {
            // Establish a database connection
            Connection connection = DriverManager.getConnection(url, username, password);

            // SQL query to retrieve data from the Categories table
            String query = "SELECT CategoryID, CategoryName FROM Categories";

            // Create a PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process the query result and populate the HashMap
            while (resultSet.next()) {
                int categoryID = resultSet.getInt("CategoryID");
                String categoryName = resultSet.getString("CategoryName");
                categoryData.put(categoryID, categoryName);
            }

            // Close the database resources
            resultSet.close();
            preparedStatement.close();
            connection.close();

            return true; // Data retrieval and population were successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // An error occurred
        }
    }
    public int getNumberOfProductsInCategory(int categoryID) {
        try {
            // Establish a database connection
            Connection connection = DriverManager.getConnection(url, username, password);

            // SQL query to count the number of products in the specified category
            String query = "SELECT COUNT(ProductID) AS ProductCount FROM Products WHERE CategoryID = ?";

            // Create a PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, categoryID);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            int productCount = 0;
            if (resultSet.next()) {
                productCount = resultSet.getInt("ProductCount");
            }

            // Close the database resources
            resultSet.close();
            preparedStatement.close();
            connection.close();

            return productCount; // Return the number of products
        } catch (SQLException e) {
            e.printStackTrace();
            return 0; // An error occurred
        }
    }
    public boolean editCategoryName(int categoryID, String newName) {
        try {
            // Establish a database connection
            Connection connection = DriverManager.getConnection(url, username, password);

            // SQL query to update the category name
            String query = "UPDATE Categories SET CategoryName = ? WHERE CategoryID = ?";

            // Create a PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, categoryID);

            // Execute the query
            int rowsUpdated = preparedStatement.executeUpdate();

            // Close the database resources
            preparedStatement.close();
            connection.close();

            return rowsUpdated > 0; // Check if any rows were updated
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false; // An error occurred
        }
    }
    public boolean deleteCategory(int categoryID) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            connection.setAutoCommit(false);

            String productDeletionQuery = "DELETE FROM Products WHERE CategoryID = ?";
            int productsDeleted;
            try (PreparedStatement productDeletionStatement = connection.prepareStatement(productDeletionQuery)) {
                productDeletionStatement.setInt(1, categoryID);
                productsDeleted = productDeletionStatement.executeUpdate();
            } // Try-with-resources will automatically close the productDeletionStatement

            String categoryDeletionQuery = "DELETE FROM Categories WHERE CategoryID = ?";
            int categoryDeleted;
            try (PreparedStatement categoryDeletionStatement = connection.prepareStatement(categoryDeletionQuery)) {
                categoryDeletionStatement.setInt(1, categoryID);
                categoryDeleted = categoryDeletionStatement.executeUpdate();
            } // Try-with-resources will automatically close the categoryDeletionStatement

            connection.commit();
            return (productsDeleted > 0) || (categoryDeleted > 0); // Check if any products and the category were deleted

        } catch (SQLException e)
        {
            e.printStackTrace();
            return false; // An error occurred
        }
    }
    public boolean addCategory(String categoryName) {
        Connection connection = null;
        PreparedStatement categoryInsertStatement = null;

        try {
            connection = DriverManager.getConnection(url, username, password);

            int categoryId = getLargestCategoryID(connection) + 1;

            String categoryInsertQuery = "INSERT INTO Categories (CategoryName) VALUES (?)";
            categoryInsertStatement = connection.prepareStatement(categoryInsertQuery);
            categoryInsertStatement.setString(1, categoryName);

            // Execute the category insertion query
            int categoryInserted = categoryInsertStatement.executeUpdate();

            return categoryInserted > 0; // Check if the category was inserted
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // An error occurred
        } finally {
            // Close the database resources in the finally block to ensure they're always closed
            if (categoryInsertStatement != null) {
                try {
                    categoryInsertStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public int getLargestCategoryID(Connection connection) {
        int largestCategoryID = 0;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            String query = "SELECT MAX(CategoryID) AS LargestID FROM Categories";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                largestCategoryID = resultSet.getInt("LargestID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return largestCategoryID;
    }
    public void initializeCategoryTable(JTable table)
    {        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("ID");
        model.addColumn("Category");
        model.addColumn("Products");

        Map<Integer, String> categoryData = new HashMap<>();
        if (getCategoryData(categoryData)) {
            for (Map.Entry<Integer, String> entry : categoryData.entrySet()) {
                int productCount = getNumberOfProductsInCategory(entry.getKey());
                model.addRow(new Object[]{entry.getKey(), entry.getValue(), productCount});
            }
        } else {
            JOptionPane.showMessageDialog(null, "Database Connection Not Found");
        }

        table.setModel(model);

        table.setRowHeight(40);

        table.setOpaque(false);
        table.setShowGrid(false);
        table.setBackground(new Color(255, 255, 255));
        table.setSelectionBackground(new Color(52, 52, 52));

        class HeaderRenderer extends DefaultTableCellRenderer {
            public HeaderRenderer() {
                setHorizontalAlignment(SwingConstants.CENTER);
            }
        }

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Century Gothic", Font.PLAIN, 18));
        header.setBackground(new Color(47, 47, 47));
        header.setForeground(Color.darkGray);
        header.setBorder(new EmptyBorder(20, 10, 20, 10));

        TableCellRenderer headerRenderer = new HeaderRenderer();
        table.getTableHeader().setDefaultRenderer(headerRenderer);

        table.setFont(new Font("Century Gothic", Font.PLAIN, 14));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(400);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);

        table.getColumnModel().getColumn(0).setHeaderValue("ID");
        table.getColumnModel().getColumn(1).setHeaderValue("Category");
        table.getColumnModel().getColumn(2).setHeaderValue("Products");
    }



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

/////////////////////////////////////////////////////////////////////////////////////////////






    /////////////////////////////////////////////////////////////////////////////////////////////////
    public void initializeProductsTable(JTable table) {
        String[] columnNames = {
                    "Product ID", "Category Name", "Product", "Cost Price", "Selling Price", "Quantity"
            };

            DefaultTableModel model = (DefaultTableModel) table.getModel();
        try {
            // Create a statement
            Statement statement = connection.createStatement();

            // Execute a query to fetch the data
            ResultSet resultSet = statement.executeQuery("SELECT Products.ProductID, Categories.CategoryName, Products.ProductName, Products.Price, Products.SellingPrice, Products.Quantity FROM Products INNER JOIN Categories ON Products.CategoryID = Categories.CategoryID");

            // Clear any existing rows from the table
            model.setRowCount(0);

            // Iterate through the result set and populate the table
            while (resultSet.next()) {
                Object[] rowData = {
                        resultSet.getInt("ProductID"),
                        resultSet.getString("CategoryName"),
                        resultSet.getString("ProductName"),
                        resultSet.getDouble("Price"),
                        resultSet.getDouble("SellingPrice"),
                        resultSet.getInt("Quantity")
                };
                model.addRow(rowData);
            }

            // Set the column names and the model for the JTable
            model.setColumnIdentifiers(columnNames);
            table.setModel(model);

            // Close the result set and statement
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in a real application
        }

        model.setColumnIdentifiers(columnNames);
        table.setModel(model);



        class HeaderRenderer extends DefaultTableCellRenderer {
            public HeaderRenderer() {
                setHorizontalAlignment(SwingConstants.CENTER); // Center-align the text
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
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);

    }
    public boolean addProduct(String productName, double costPrice, double sellingPrice, int quantity, int categoryID, java.sql.Date expiryDate) {
        PreparedStatement preparedStatement = null;
        boolean addSuccess = false;

        try {
            String insertQuery = "INSERT INTO Products (ProductName, Price, SellingPrice, Quantity, CategoryID, ExpiryDate) VALUES (?, ?, ?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setString(1, productName);
            preparedStatement.setDouble(2, costPrice);
            preparedStatement.setDouble(3, sellingPrice);
            preparedStatement.setInt(4, quantity);
            preparedStatement.setInt(5, categoryID);
            preparedStatement.setDate(6, expiryDate);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Product added successfully.");
                addSuccess = true;
            } else {
                System.out.println("Failed to add product.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return addSuccess;
    }
    public boolean updateProduct(int productId, String productName, double costPrice, double sellingPrice, int quantity) {
        // Assuming you have a connection object named 'connection'
        PreparedStatement preparedStatement = null;
        boolean updateSuccess = false;

        try {
            // SQL update statement
            String updateQuery = "UPDATE Products SET ProductName = ?, Price = ?, SellingPrice = ?, Quantity = ? WHERE ProductID = ?";

            // Create a prepared statement with the update query
            preparedStatement = connection.prepareStatement(updateQuery);

            // Set the parameters
            preparedStatement.setString(1, productName);
            preparedStatement.setDouble(2, costPrice);
            preparedStatement.setDouble(3, sellingPrice);
            preparedStatement.setInt(4, quantity);
            preparedStatement.setInt(5, productId); // Replace productId with the ID of the product to update

            // Execute the update statement
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Product details updated successfully.");
                updateSuccess = true;
            } else {
                System.out.println("Product not found or no changes made.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in a real application
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception appropriately in a real application
            }
        }

        return updateSuccess;
    }
    public boolean deleteProduct(int productId) {
        PreparedStatement preparedStatement = null;
        boolean deletionSuccess = false;

        try {
            String deleteQuery = "DELETE FROM Products WHERE ProductID = ?";
            preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, productId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Product with ID " + productId + " deleted successfully.");
                deletionSuccess = true;
            } else {
                System.out.println("Product with ID " + productId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return deletionSuccess;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
//    public String processCSVFile(File file) {
//        StringBuilder errorLog = new StringBuilder();
//
//        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
//            String line;
//            boolean headerSkipped = false;
//
//            while ((line = br.readLine()) != null) {
//                if (!headerSkipped) {
//                    headerSkipped = true;
//                    continue; // Skip the header
//                }
//
//                String[] data = line.split(","); // Split the line by comma (modify based on your file format)
//
//                // Check for missing columns in the CSV
//                if (data.length < 6) {
//                    errorLog.append("Invalid CSV format. Some columns are missing.\n");
//                    continue;
//                }
//
//                String productName = data[0];
//                double costPrice;
//                double sellingPrice;
//                int quantity;
//                int categoryID;
//                Date expiryDate;
//
//                try {
//                    costPrice = Double.parseDouble(data[1]); // Assuming costPrice is the 2nd column
//                    sellingPrice = Double.parseDouble(data[2]); // Assuming sellingPrice is the 3rd column
//                    quantity = Integer.parseInt(data[3]); // Assuming quantity is the 4th column
//                    categoryID = Integer.parseInt(data[4]); // Assuming categoryID is the 5th column
//
//                    String inputDateFormat = "M/d/yyyy"; // Modify according to your date format in CSV
//                    String outputDateFormat = "yyyy-MM-dd"; // Modify according to your required format
//
//                    String originalDate = data[5]; // Assuming the date is the 6th column
//                    String formattedDate = convertDateFormat(originalDate, inputDateFormat, outputDateFormat);
//
//                    expiryDate = Date.valueOf(formattedDate);
//                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
//                    errorLog.append("Invalid numeric format or Date format in the CSV.\n");
//                    continue;
//                }
//
//                // Validate if the CategoryID exists in the Category table
//                if (!isCategoryExists(categoryID)) {
//                    errorLog.append("CategoryID ").append(categoryID).append(" does not exist in the Category table.\n");
//                    continue; // Skip processing this entry and move to the next
//                }
//
//                // Check if the product with the same name and expiry date exists in the database
//                int productID = getProductID(productName, expiryDate);
//                if (productID != -1) {
//                    // Product exists
//                    boolean quantityUpdated = updateProductQuantity(productID, quantity);
//                    if (!quantityUpdated)
//                    {
//                        errorLog.append("Failed to update quantity for product: ").append(productName).append("\n");
//                    } else {
//                        errorLog.append("Product: ").append(productName).append(" details have been updated.\n");
//                    }
//                } else {
//                    // Product doesn't exist with the same expiry date, add it to the database
//                    boolean productAdded = addProduct(productName, costPrice, sellingPrice, quantity, categoryID, expiryDate);
//                    if (!productAdded) {
//                        errorLog.append("Failed to add product: ").append(productName).append("\n");
//                    } else {
//                        errorLog.append("Product: ").append(productName).append(" details have been added.\n");
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            errorLog.append("Error processing the file.\n");
//        }
//
//        // Write the error log to a file
//        String logFileName = "ProductLog.txt";
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFileName))) {
//            writer.write(errorLog.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return errorLog.toString();
//    }

    public String processCSVFile(File file) {
        StringBuilder errorLog = new StringBuilder();

        String logFilePath = createLogFilePath();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean headerSkipped = false;

            while ((line = br.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue; // Skip the header
                }

                String[] data = line.split(","); // Split the line by comma (modify based on your file format)

                // Check for missing columns in the CSV
                if (data.length < 6) {
                    errorLog.append("Invalid CSV format. Some columns are missing.\n");
                    continue;
                }

                String productName = data[0];
                double costPrice;
                double sellingPrice;
                int quantity;
                int categoryID;
                Date expiryDate;

                try {
                    costPrice = Double.parseDouble(data[1]); // Assuming costPrice is the 2nd column
                    sellingPrice = Double.parseDouble(data[2]); // Assuming sellingPrice is the 3rd column
                    quantity = Integer.parseInt(data[3]); // Assuming quantity is the 4th column
                    categoryID = Integer.parseInt(data[4]); // Assuming categoryID is the 5th column

                    String inputDateFormat = "M/d/yyyy"; // Modify according to your date format in CSV
                    String outputDateFormat = "yyyy-MM-dd"; // Modify according to your required format

                    String originalDate = data[5]; // Assuming the date is the 6th column
                    String formattedDate = convertDateFormat(originalDate, inputDateFormat, outputDateFormat);

                    expiryDate = Date.valueOf(formattedDate);
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                    errorLog.append("Invalid numeric format or Date format in the CSV.\n");
                    continue;
                }

                // Validate if the CategoryID exists in the Category table
                if (!isCategoryExists(categoryID)) {
                    errorLog.append("CategoryID ").append(categoryID).append(" does not exist in the Category table.\n");
                    continue; // Skip processing this entry and move to the next
                }

                // Check if the product with the same name and expiry date exists in the database
                int productID = getProductID(productName, expiryDate);
                if (productID != -1) {
                    // Product exists
                    boolean quantityUpdated = updateProductQuantity(productID, quantity);
                    if (!quantityUpdated)
                    {
                        errorLog.append("Failed to update quantity for product: ").append(productName).append("\n");
                    } else {
                        errorLog.append("Product: ").append(productName).append(" details have been updated.\n");
                    }
                } else {
                    // Product doesn't exist with the same expiry date, add it to the database
                    boolean productAdded = addProduct(productName, costPrice, sellingPrice, quantity, categoryID, expiryDate);
                    if (!productAdded) {
                        errorLog.append("Failed to add product: ").append(productName).append("\n");
                    } else {
                        errorLog.append("Product: ").append(productName).append(" details have been added.\n");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            errorLog.append("Error processing the file.\n");
        }

        // Write the error log to a file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath))) {
            writer.write(errorLog.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return logFilePath;
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
    private boolean isCategoryExists(int categoryID) {
        boolean categoryExists = false;
        try {
            // Establish a database connection
            Connection connection = DriverManager.getConnection(url, username, password);

            // Prepare a statement to check if the category ID exists in the Categories table
            String query = "SELECT * FROM Categories WHERE CategoryID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, categoryID);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if the category exists
            if (resultSet.next()) {
                categoryExists = true;
            }

            // Close the database resources
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQL exception
        }
        return categoryExists;
    }
    public boolean updateProductQuantity(int productID, int newQuantity) {
        try {
            String selectQuery = "SELECT Quantity FROM Products WHERE ProductID = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setInt(1, productID);

            // Execute the select query to get the current quantity
            ResultSet resultSet = selectStatement.executeQuery();

            int currentQuantity = 0;
            if (resultSet.next()) {
                currentQuantity = resultSet.getInt("Quantity");
            }

            int totalQuantity = currentQuantity + newQuantity;

            // Update the database with the total quantity
            String updateQuery = "UPDATE Products SET Quantity = ? WHERE ProductID = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setInt(1, totalQuantity);
            updateStatement.setInt(2, productID);

            // Execute the update query
            int rowsUpdated = updateStatement.executeUpdate();

            // Close the database resources
            resultSet.close();
            selectStatement.close();
            updateStatement.close();

            return rowsUpdated > 0; // Return true if rows were updated
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQL exception
            return false; // Return false for an error
        }
    }
    private int getProductID(String productName, Date expiryDate) {
        int productID = -1; // Assuming -1 indicates no product found

        try {
            // Establish the database connection (Assuming 'connection' is an existing connection)
            String query = "SELECT productID FROM Products WHERE productName = ? AND expiryDate = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, productName);
            statement.setDate(2, expiryDate);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                productID = resultSet.getInt("productID");
            }

            // Close the connections
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQL exception
        }

        return productID;
    }
    public static String convertDateFormat(String inputDate, String inputFormat, String outputFormat) {
        SimpleDateFormat inputFormatter = new SimpleDateFormat(inputFormat);
        SimpleDateFormat outputFormatter = new SimpleDateFormat(outputFormat);
        String outputDate = "";

        try {
            java.util.Date date = inputFormatter.parse(inputDate);
            outputDate = outputFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return outputDate;
    }
    public void readProductLogFile(JTable table, String filePath) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                model.addRow(new Object[]{line}); // Add a row to the table
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Log an error message in case reading the file fails
            model.addRow(new Object[]{"Error reading " + filePath + ": " + e.getMessage()});
        }

        table.setModel(model);

        class HeaderRenderer extends DefaultTableCellRenderer {
            public HeaderRenderer() {
                setHorizontalAlignment(SwingConstants.CENTER); // Center-align the text
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
        //table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void initializeStockTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear existing table data

        try {
            String query = "SELECT P.ProductName, C.CategoryName, SUM(P.Quantity) AS TotalQuantity " +
                    "FROM Products P " +
                    "JOIN Categories C ON P.CategoryID = C.CategoryID " +
                    "GROUP BY P.ProductName, C.CategoryName " +
                    "HAVING SUM(P.Quantity) > 0 " +
                    "ORDER BY TotalQuantity ASC";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String productName = resultSet.getString("ProductName");
                String category = resultSet.getString("CategoryName");
                int quantity = resultSet.getInt("TotalQuantity");

                model.addRow(new Object[]{productName, category, quantity});
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        table.setModel(model);

        class HeaderRenderer extends DefaultTableCellRenderer {
            public HeaderRenderer() {
                setHorizontalAlignment(SwingConstants.CENTER); // Center-align the text
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
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
    }
    public void initializeDeadStockTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear existing table data

        try {
            String query = "SELECT P.ProductName, C.CategoryName, P.Quantity " +
                    "FROM Products P " +
                    "JOIN Categories C ON P.CategoryID = C.CategoryID " +
                    "WHERE P.Quantity = 0";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String productName = resultSet.getString("ProductName");
                String category = resultSet.getString("CategoryName");
                int quantity = resultSet.getInt("Quantity");

                model.addRow(new Object[]{productName, category, quantity});
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        table.setModel(model);



        class HeaderRenderer extends DefaultTableCellRenderer {
            public HeaderRenderer() {
                setHorizontalAlignment(SwingConstants.CENTER); // Center-align the text
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
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static String generateReport(String reportType, String date) {
        LocalDate day = LocalDate.now();
        String pdfOutputFile = "src/main/resources/Reports/" + reportType + "for" + date + "_" + day + ".pdf";
        String sql = "";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfOutputFile));
            document.open();

            // Add logo
            Image logo = Image.getInstance("src/main/resources/Material/apple-touch-icon.png");
            logo.setAlignment(Element.ALIGN_CENTER);
            logo.scaleAbsolute(45, 45);
            document.add(logo);

            // Header
            Paragraph header = new Paragraph("PharmaFast - " + reportType + " Report - " + date + "\n\n",
                    FontFactory.getFont("Century Gothic", 18, BaseColor.BLACK));
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);

            // SQL details in table format
            PdfPTable table = new PdfPTable(4); // Change the number of columns to 4
            table.setWidthPercentage(100);
            table.addCell(getHeaderCell("Transaction ID"));
            table.addCell(getHeaderCell("UserID"));
            table.addCell(getHeaderCell("TotalCost"));
            table.addCell(getHeaderCell("Transaction Date"));

            PreparedStatement statement;
            ResultSet resultSet;

            if (reportType.equals("daily")) {
                sql = "SELECT TransactionID, UserID, TotalCost, TransactionDate " +
                        "FROM Transactions " +
                        "WHERE CONVERT(DATE, TransactionDate) = ?";

            }
            else if (reportType.equals("monthly"))
            {
                sql = "SELECT TransactionID, UserID, TotalCost, TransactionDate " +
                        "FROM Transactions " +
                        "WHERE YEAR(TransactionDate) = ? AND MONTH(TransactionDate) = ?";

            }
            else if (reportType.equals("yearly")) {
                sql = "SELECT TransactionID, UserID, TotalCost, TransactionDate " +
                        "FROM Transactions " +
                        "WHERE YEAR(TransactionDate) = YEAR(?)";
            }

            statement = connection.prepareStatement(sql);

            if (reportType.equals("daily")) {
                statement.setString(1, date);
            }
            else if (reportType.equals("monthly"))
            {
                String[] parts = date.split("-");
                String year = parts[0];
                String month = parts[1];
                System.out.println(Integer.parseInt(year));
                System.out.println(Integer.parseInt(month));

                statement.setInt(1, Integer.parseInt(year));
                statement.setInt(2, Integer.parseInt(month));
            }
            else if (reportType.equals("yearly"))
            {
                statement.setString(1, date);
            }


            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                table.addCell(getCell(String.valueOf(resultSet.getInt("TransactionID"))));
                table.addCell(getCell(String.valueOf(resultSet.getInt("UserID"))));
                table.addCell(getCell(String.valueOf(resultSet.getDouble("TotalCost"))));
                table.addCell(getCell(String.valueOf(resultSet.getTimestamp("TransactionDate"))));
            }

            document.add(table);

            // Footer with date
            Paragraph footer = new Paragraph("Report generated on: " + LocalDate.now(),
                    FontFactory.getFont("Century Gothic", 12, BaseColor.BLACK));
            footer.setAlignment(Element.ALIGN_RIGHT);
            document.add(footer);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pdfOutputFile;
    }
    private static PdfPCell getCell(String content) {
        PdfPCell cell = new PdfPCell(new Phrase(content, FontFactory.getFont("Century Gothic", 12, BaseColor.BLACK)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }
    private static PdfPCell getHeaderCell(String content) {
        com.itextpdf.text.Font font = FontFactory.getFont("Century Gothic", 14, BaseColor.WHITE);
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setBackgroundColor(BaseColor.DARK_GRAY);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }
    public String generateRecipt(JTable tableCart, String total, String subTotal, String vat, String paidAmount, String change) {
        LocalDate day = LocalDate.now();
        String pdfOutputFile = "src/main/resources/Recipts/Report_" + day + ".pdf";

        try {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfOutputFile));
            document.open();

            // Add logo
            Image logo = Image.getInstance("src/main/resources/Material/apple-touch-icon.png");
            logo.setAlignment(Element.ALIGN_CENTER);
            logo.scaleAbsolute(45, 45);
            document.add(logo);

            // Header with Title
            Paragraph title = new Paragraph("PharmaFast - Recipt\n\n",
                    FontFactory.getFont("Century Gothic", 18, BaseColor.BLACK));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Add Table Contents
            PdfPTable cartTable = new PdfPTable(6); // Change the number of columns based on your cart table
            cartTable.setWidthPercentage(100);

            // Add Table Headers
            cartTable.addCell(getHeaderCell("Product ID"));
            cartTable.addCell(getHeaderCell("Product Name"));
            cartTable.addCell(getHeaderCell("Category"));
            cartTable.addCell(getHeaderCell("Price"));
            cartTable.addCell(getHeaderCell("Quantity"));
            cartTable.addCell(getHeaderCell("Total Price"));

            // Fetch cart details from JTable
            DefaultTableModel model = (DefaultTableModel) tableCart.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                cartTable.addCell(getCell(model.getValueAt(i, 0).toString())); // Product ID
                cartTable.addCell(getCell(model.getValueAt(i, 1).toString())); // Product Name
                cartTable.addCell(getCell(model.getValueAt(i, 2).toString())); // Category
                cartTable.addCell(getCell(model.getValueAt(i, 3).toString())); // Price
                cartTable.addCell(getCell(model.getValueAt(i, 4).toString())); // Quantity
                cartTable.addCell(getCell(model.getValueAt(i, 5).toString())); // Total Price
            }

            document.add(cartTable);

            // Other Details

            Paragraph totalParagraph = new Paragraph("Total: " + total);
            totalParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(totalParagraph);

            Paragraph subTotalParagraph = new Paragraph("Subtotal: " + subTotal);
            subTotalParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(subTotalParagraph);

            Paragraph vatParagraph = new Paragraph("VAT: " + vat);
            vatParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(vatParagraph);

            Paragraph paidAmountParagraph = new Paragraph("Paid Amount: " + paidAmount);
            paidAmountParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(paidAmountParagraph);

            Paragraph changeParagraph = new Paragraph("Change: " + change);
            changeParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(changeParagraph);


            // Footer with date
            Paragraph footer = new Paragraph("\n\n\nReport generated on: " + LocalDate.now(),
                    FontFactory.getFont("Century Gothic", 12, BaseColor.BLACK));
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pdfOutputFile;
    }

//    private void updateDatabase(JTable tableCart) {
//        DefaultTableModel model = (DefaultTableModel) tableCart.getModel();
//
//        try (Connection connection = DriverManager.getConnection(url, username, password)) {
//            // Add the sold items to the Transactions and TransactionItems tables
//            String insertTransactionQuery = "INSERT INTO Transactions (UserID, TotalCost, TransactionDate) VALUES (?, ?, ?)";
//            String insertTransactionItemQuery = "INSERT INTO TransactionItems (TransactionID, ProductID, Quantity) VALUES (?, ?, ?)";
//
//            PreparedStatement insertTransactionStatement = connection.prepareStatement(insertTransactionQuery, Statement.RETURN_GENERATED_KEYS);
//            PreparedStatement insertTransactionItemStatement = connection.prepareStatement(insertTransactionItemQuery);
//
//            // Set parameters for the Transactions table
//            insertTransactionStatement.setInt(1, userId); // Replace userId with the actual user ID
//            insertTransactionStatement.setDouble(2, Double.parseDouble(total));
//            insertTransactionStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
//
//            // Execute the insert statement for Transactions
//            int rowsAffected = insertTransactionStatement.executeUpdate();
//
//            if (rowsAffected > 0) {
//                // Retrieve the generated transaction ID
//                ResultSet generatedKeys = insertTransactionStatement.getGeneratedKeys();
//                int transactionID = -1;
//                if (generatedKeys.next()) {
//                    transactionID = generatedKeys.getInt(1);
//                }
//
//                if (transactionID != -1) {
//                    // Iterate through the items in the cart and add them to the TransactionItems table
//                    for (int i = 0; i < model.getRowCount(); i++) {
//                        int productID = Integer.parseInt(model.getValueAt(i, 0).toString());
//                        int quantity = Integer.parseInt(model.getValueAt(i, 4).toString());
//
//                        // Set parameters for the TransactionItems table
//                        insertTransactionItemStatement.setInt(1, transactionID);
//                        insertTransactionItemStatement.setInt(2, productID);
//                        insertTransactionItemStatement.setInt(3, quantity);
//
//                        // Execute the insert statement for TransactionItems
//                        insertTransactionItemStatement.executeUpdate();
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }


    public static void main(String[] args) {
        //String yearlyReport = generateReport("yearly", "2023");
        //String monthlyReport = generateReport("monthly", "2023-10");
        //String dailyReport = generateReport("daily", "2023-10-15");

    }



}




