package Controllers;
import Dao.CategoryDao;
import Dao.ProductDao;
import Dao.UserDao;
import Helpers.AdminConfig;
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
import java.util.HashMap;
import java.util.Map;

public class AdminController extends Component {

    private CategoryDao categoryDao;
    private ProductDao productDao;
    private UserDao userDao;
    private UtilityFunctions uFunctions;
    private static final String CONFIG_FILE = "src/main/resources/Settings/adminConfig.ser";
    private static AdminConfig adminConfig;


    public AdminController() throws SQLException
    {
        this.categoryDao = new CategoryDao();
        this.productDao = new ProductDao();
        this.uFunctions = new UtilityFunctions();
        this.userDao = new UserDao();
        loadConfig();
    }

    public AdminController(CategoryDao categoryDao, ProductDao productDao)
    {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
        this.uFunctions = new UtilityFunctions();
        this.userDao = new UserDao();
        loadConfig();
    }


    public static void saveConfig()
    {
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




    public static void main(String[] args) {
        //String yearlyReport = generateReport("yearly", "2023");
        //String monthlyReport = generateReport("monthly", "2023-10");
        //String dailyReport = generateReport("daily", "2023-10-15");

    }


    public boolean deleteUser(int userID) {return userDao.deleteUser(userID);}

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

}




