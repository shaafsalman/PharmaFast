package Controllers;
import Dao.CategoryDao;
import Dao.ProductDao;
import Dao.UserDao;
import Helpers.AdminConfig;
import Helpers.SessionManager;
import Helpers.UtilityFunctions;
import Models.Category;
import Models.Product;
import Models.User;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class AdminController extends Component {

    private CategoryDao categoryDao;
    private ProductDao productDao;
    private UserDao userDao;
    private UtilityFunctions uFunctions;
    private static final String CONFIG_FILE = "src/main/resources/Settings/adminConfig.ser";
    private static AdminConfig adminConfig;
    private  SessionManager sessionManager;


    public AdminController() throws SQLException {
        this.categoryDao = new CategoryDao();
        this.productDao = new ProductDao();
        this.uFunctions = new UtilityFunctions();
        this.userDao = new UserDao();
        this.sessionManager = new SessionManager();
        adminConfig = new AdminConfig();
        loadConfig();
    }
    public AdminController(CategoryDao categoryDao, ProductDao productDao) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
        this.uFunctions = new UtilityFunctions();
        this.userDao = new UserDao();
        this.sessionManager = new SessionManager();
        adminConfig = new AdminConfig();
        loadConfig();
    }


    public static void saveConfig() {
        File directory = new File("src/main/resources/Settings");
        if (!directory.exists()) {
            if (!directory.mkdir()) {
                System.err.println("Unable to create the 'Settings' directory.");
                return;
            }
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CONFIG_FILE))) {
            oos.writeObject(adminConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void loadConfig() {
        File directory = new File("src/main/resources/Settings");
        if (!directory.exists()) {
            if (!directory.mkdir()) {
                System.err.println("Unable to create the 'Settings' directory.");
                return;
            }
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CONFIG_FILE))) {
            adminConfig = (AdminConfig) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            adminConfig = new AdminConfig(5.0f, 1122);
            saveConfig();
        }
    }

    public static float getVatRate() {
        return adminConfig.getVatRate();
    }

    public static void setVatRate(float newVatRate) {
        adminConfig.setVatRate(newVatRate);
        saveConfig();
    }

    public static int getAdminCode() {
        return adminConfig.getAdminCode();
    }

    public static void setAdminCode(int newAdminCode) {
        adminConfig.setAdminCode(newAdminCode);
        saveConfig();
    }




    public boolean getCategoryData(Map<Integer, String> categoryData) {
        return categoryDao.getCategoryData(categoryData);
    }
    public int getCategoryIDByName(String name) {
        return categoryDao.getCategoryIDByName(name);
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
    public Product getProductByID(int productID)
    {
        return productDao.getProduct( productID);
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

    //Initialising Tables
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
    public void initializeUsersTable(JTable table) {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"UserID", "Username", "Role"}, 0);
        ResultSet resultSet = null;

        if (userDao.getUserData(model))
        {
            uFunctions.initializeUForTable(table);
            table.setModel(model);
            table.getColumnModel().getColumn(0).setPreferredWidth(80);
            table.getColumnModel().getColumn(1).setPreferredWidth(200);
            table.getColumnModel().getColumn(2).setPreferredWidth(200);
        } else {
            JOptionPane.showMessageDialog(null, "Database Error");
        }
    }


    public void showAddUserDialog() {
        JComboBox<String> cboRoles;
        String username = JOptionPane.showInputDialog(null, "Enter username:");
        String email = JOptionPane.showInputDialog(null, "Enter email:");
        String password = JOptionPane.showInputDialog(null, "Enter password:");
        String repeatPassword = JOptionPane.showInputDialog(null, "Repeat password:");

        String[] roles = {"Sales Assistant", "Manager"};
        cboRoles = new JComboBox<>(roles);

        Object[] message = {
                "Username:", username,
                "Email:", email,
                "Password:", password,
                "Repeat Password:", repeatPassword,
                "Role:", cboRoles
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Add User", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION)
        {
            if (UtilityFunctions.validateEmail(email) && UtilityFunctions.validatePassword(password, repeatPassword)) {
                String selectedRole = (String) cboRoles.getSelectedItem();
                User newUser = new User(0, username, password, selectedRole);
                addUser(newUser);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid email or password.");
            }
        }
    }
    public void addUser(User newUser)
    {
        userDao.addUser(newUser);
    }
    public boolean deleteUser(int userID) {return userDao.deleteUser(userID);}
    public void setUser(JLabel usernameLabel)
    {
        usernameLabel.setText(sessionManager.getName());
    }
    public void setRole(JLabel lblUserRoles) {lblUserRoles.setText(sessionManager.getRole());
    }


    public void showDeleteUserDialog(Component parentComponent, JTable tblUsers, int selectedRow) {
        if (selectedRow != -1) {
            int userId = (int) tblUsers.getValueAt(selectedRow, 0);
            String username = (String) tblUsers.getValueAt(selectedRow, 1);
            int confirmation = JOptionPane.showConfirmDialog(
                    parentComponent,  // Pass the parentComponent here
                    "Are you sure you want to delete user '" + username + "'?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmation == JOptionPane.YES_OPTION) {
                if (deleteUser(userId)) {
                    initializeUsersTable(tblUsers);
                    JOptionPane.showMessageDialog(parentComponent, "User '" + username + "' deleted successfully.");
                } else {
                    JOptionPane.showMessageDialog(parentComponent, "Failed to delete user '" + username + "'.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(parentComponent, "Please select a row to delete.");
        }
    }
    public boolean showAddProductDialog() {
        String productName = JOptionPane.showInputDialog(null, "Enter Product Name:");
        double costPrice = 0.0;
        double sellingPrice = 0.0;
        int quantity = 0;

        Map<Integer, String> categoryData = new HashMap<>();
        boolean success = getCategoryData(categoryData);

        if (!success) {
            JOptionPane.showMessageDialog(null, "Failed to retrieve category data. Please try again later.");
            return false;
        }

        JComboBox<String> categoryComboBox = new JComboBox<>(categoryData.values().toArray(new String[0]));
        int result = JOptionPane.showConfirmDialog(null, categoryComboBox, "Select a Category", JOptionPane.OK_CANCEL_OPTION);

        int categoryID = -1;
        if (result == JOptionPane.OK_OPTION && categoryComboBox.getSelectedIndex() != -1) {
            String selectedCategory = (String) categoryComboBox.getSelectedItem();

            for (Map.Entry<Integer, String> entry : categoryData.entrySet()) {
                if (entry.getValue().equals(selectedCategory)) {
                    categoryID = entry.getKey();
                    break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Category not selected. Please select a category.");
            return false;
        }

        String inputCost = JOptionPane.showInputDialog(null, "Enter Cost Price (Skip to set default as 0.0):");
        if (inputCost != null && !inputCost.isEmpty()) {
            costPrice = Double.parseDouble(inputCost);
        }

        String inputSelling = JOptionPane.showInputDialog(null, "Enter Selling Price (Skip to set default as 0.0):");
        if (inputSelling != null && !inputSelling.isEmpty()) {
            sellingPrice = Double.parseDouble(inputSelling);
        }

        String inputQuantityStr = JOptionPane.showInputDialog(null, "Enter Quantity (Skip to set default as 0):");
        if (inputQuantityStr != null && !inputQuantityStr.isEmpty()) {
            quantity = Integer.parseInt(inputQuantityStr);
        }

        JComboBox<String> yearComboBox = uFunctions.createExpiryYearComboBox();
        JComboBox<String> monthComboBox = uFunctions.createMonthComboBox();
        JComboBox<String> dayComboBox = uFunctions.createDayComboBox();

        JPanel expiryPanel = new JPanel();
        expiryPanel.add(yearComboBox);
        expiryPanel.add(monthComboBox);
        expiryPanel.add(dayComboBox);

        int expiryResult;
        boolean validDate = false;
        String selectedExpiryDate = null;

        while (!validDate) {
            expiryResult = JOptionPane.showConfirmDialog(null, expiryPanel, "Select Expiry Date", JOptionPane.OK_CANCEL_OPTION);

            if (expiryResult == JOptionPane.OK_OPTION) {
                String selectedYear = (String) yearComboBox.getSelectedItem();
                String selectedMonth = (String) monthComboBox.getSelectedItem();
                String selectedDay = (String) dayComboBox.getSelectedItem();

                validDate = uFunctions.isValidDate(Integer.parseInt(selectedYear), Integer.parseInt(selectedMonth), Integer.parseInt(selectedDay));

                if (!validDate) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid date.");
                } else {
                    selectedExpiryDate = selectedYear + "-" + selectedMonth + "-" + selectedDay;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Expiry date not selected. Please select a date.");
                return false;
            }
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = dateFormat.parse(selectedExpiryDate);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            Product newProduct = new Product(0, productName, costPrice, sellingPrice, quantity,categoryID,sqlDate);

            boolean addResult = addProduct(newProduct);

            if (addResult) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Failed to add product " + productName);
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error parsing the selected date.");
            return false;
        }
    }
    public boolean showModifyProductDialog(Product existingProduct) {
        // Show existing values in input dialogs
        String modifiedProductName = JOptionPane.showInputDialog(null, "Enter Product Name (Skip to keep existing):", existingProduct.getName());

        if (modifiedProductName == null) {
            // User clicked Cancel
            return false;
        }

        double modifiedCostPrice = existingProduct.getCostPrice();
        String inputCost = JOptionPane.showInputDialog(null, "Enter Cost Price (Skip to keep existing):", modifiedCostPrice);

        double modifiedSellingPrice = existingProduct.getSellingPrice();
        String inputSelling = JOptionPane.showInputDialog(null, "Enter Selling Price (Skip to keep existing):", modifiedSellingPrice);


        int modifiedQuantity = existingProduct.getQuantity();
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(modifiedQuantity, 0, Integer.MAX_VALUE, 1));
        quantitySpinner.setEditor(new JSpinner.NumberEditor(quantitySpinner, "#"));
        int quantityDialogResult = JOptionPane.showOptionDialog(
                null,
                quantitySpinner,
                "Enter Quantity",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null
        );

        if (quantityDialogResult == JOptionPane.CANCEL_OPTION) {
            // User clicked Cancel
            return false;
        }

        if (inputCost != null && !inputCost.isEmpty()) {
            modifiedCostPrice = Double.parseDouble(inputCost);
            existingProduct.setCostPrice(modifiedCostPrice);
        }

        if (inputSelling != null && !inputSelling.isEmpty()) {
            modifiedSellingPrice = Double.parseDouble(inputSelling);
            existingProduct.setSellingPrice(modifiedSellingPrice);
        }

        existingProduct.setName(modifiedProductName);
        existingProduct.setQuantity((int) quantitySpinner.getValue());

        // Update the existing product
        boolean updateResult = updateProduct(existingProduct);

        if (updateResult) {
            JOptionPane.showMessageDialog(null, "Product " + existingProduct.getName() + " details updated successfully.");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Failed to update product details for " + existingProduct.getName());
            return false;
        }
    }
    public boolean showDeleteProductDialog(int productID, String productName) {
        int confirmResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the product " + productName + "?");

        if (confirmResult == JOptionPane.YES_OPTION) {
            boolean deleteResult = deleteProduct(productID);

            if (deleteResult) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Failed to delete product " + productName);
                return false;
            }
        } else {
            return false;
        }
    }
    public void showModifyUserDialog(int userID) {
        User existingUser = userDao.getUser(userID);

        if (existingUser != null) {
            JTextField txtUsername = new JTextField(existingUser.getUsername());
            JPasswordField txtPassword = new JPasswordField(existingUser.getPassword());
            JPasswordField txtRepeatPassword = new JPasswordField(existingUser.getPassword()); // For password confirmation
            JComboBox<String> cboRoles = new JComboBox<>(new String[]{"Sales Assistant", "Manager"});
            cboRoles.setSelectedItem(existingUser.getRole());

            Object[] message = {
                    "Username:", txtUsername,
                    "Password:", txtPassword,
                    "Repeat Password:", txtRepeatPassword,
                    "Role:", cboRoles
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Modify User", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                // Get values from the input fields
                String newUsername = txtUsername.getText();
                String newPassword = new String(txtPassword.getPassword());
                String repeatedPassword = new String(txtRepeatPassword.getPassword());
                String selectedRole = (String) cboRoles.getSelectedItem();

                // Validate input if needed (e.g., check if passwords match)
                if (!newPassword.equals(repeatedPassword)) {
                    JOptionPane.showMessageDialog(null, "Passwords do not match.");
                    return;
                }

                // Update the existing user object
                existingUser.setUsername(newUsername);
                existingUser.setPassword(newPassword);
                existingUser.setRole(selectedRole);

                // Update the user in the database
                if (userDao.updateUser(existingUser)) {
                    JOptionPane.showMessageDialog(null, "User modified successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to modify user.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "User not found.");
        }
    }

    public static void main(String[] args) {
        //String yearlyReport = generateReport("yearly", "2023");
        //String monthlyReport = generateReport("monthly", "2023-10");
        //String dailyReport = generateReport("daily", "2023-10-15");

    }


}




