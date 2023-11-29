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
    private boolean isValidProductName(Product product) {
        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Product name cannot be empty.");
            return false;
        }
        return true;
    }
    private boolean isValidProductPrice(Product product) {

        if (product.getSellingPrice() <= product.getCostPrice()) {
            JOptionPane.showMessageDialog(null, "Selling price must be greater than cost price.");
            return false;
        }
        return true;
    }
    private boolean isValidProductQuantity(Product product) {

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
    public void initializeExpiredProductsTable(JTable table) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Product ID");
        model.addColumn("Product Name");
        model.addColumn("Quantity");
        model.addColumn("Expiry Date");

        ResultSet resultSet = null;

        try {
            resultSet = productDao.getExpiredProducts();

            if (resultSet != null) {
                while (resultSet.next()) {
                    Object[] rowData = {
                            resultSet.getInt("ProductID"),
                            resultSet.getString("ProductName"),
                            resultSet.getInt("Quantity"),
                            resultSet.getDate("ExpiryDate")
                    };
                    model.addRow(rowData);
                }
            }

            table.setModel(model);

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
        }

        UtilityFunctions.initializeUForTable(table);
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
    public boolean showAddProductDialog() {
        JPanel panel = new JPanel(new GridLayout(8, 2));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        Font centuryGothicFont = new Font("Century Gothic", Font.PLAIN, 14);
        panel.setFont(centuryGothicFont);

        JTextField nameField = new JTextField();
        panel.add(new JLabel("Enter Product Name:"));
        panel.add(nameField);

        Map<Integer, String> categoryData = new HashMap<>();
        boolean success = getCategoryData(categoryData);

        if (!success) {
            JOptionPane.showMessageDialog(null, "Failed to retrieve category data. Please try again later.");
            return false;
        }

        JComboBox<String> categoryComboBox = new JComboBox<>(categoryData.values().toArray(new String[0]));
        panel.add(new JLabel("Select a Category:"));
        panel.add(categoryComboBox);

        JSpinner costPriceSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 0.1));
        costPriceSpinner.setPreferredSize(new Dimension(100, costPriceSpinner.getPreferredSize().height));
        panel.add(new JLabel("Enter Cost Price:"));
        panel.add(costPriceSpinner);

        JSpinner sellingPriceSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 0.1));
        sellingPriceSpinner.setPreferredSize(new Dimension(100, sellingPriceSpinner.getPreferredSize().height));
        panel.add(new JLabel("Enter Selling Price:"));
        panel.add(sellingPriceSpinner);

        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        quantitySpinner.setPreferredSize(new Dimension(100, quantitySpinner.getPreferredSize().height));
        panel.add(new JLabel("Enter Quantity:"));
        panel.add(quantitySpinner);

        JComboBox<String> yearComboBox = uFunctions.createExpiryYearComboBox();
        JComboBox<String> monthComboBox = uFunctions.createMonthComboBox();
        JComboBox<String> dayComboBox = uFunctions.createDayComboBox();

        JPanel expiryPanel = new JPanel();
        expiryPanel.add(yearComboBox);
        expiryPanel.add(monthComboBox);
        expiryPanel.add(dayComboBox);

        panel.add(new JLabel("Select Expiry Date:"));
        panel.add(expiryPanel);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add a Product", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.CANCEL_OPTION) {
            return false;
        }

        String productName = nameField.getText();
        int categoryID = categoryComboBox.getSelectedIndex() != -1 ? categoryComboBox.getSelectedIndex() + 1 : -1;
        double costPrice = (double) costPriceSpinner.getValue();
        double sellingPrice = (double) sellingPriceSpinner.getValue();
        int quantity = (int) quantitySpinner.getValue();

        String selectedYear = (String) yearComboBox.getSelectedItem();
        String selectedMonth = (String) monthComboBox.getSelectedItem();
        String selectedDay = (String) dayComboBox.getSelectedItem();
        String selectedExpiryDate = selectedYear + "-" + selectedMonth + "-" + selectedDay;

        try {
            if (!uFunctions.isValidField(sellingPriceSpinner.toString().trim())) {
                JOptionPane.showMessageDialog(null, "Please add selling price in Numbers!");
                return false;
            }

            if (!uFunctions.isValidField(costPriceSpinner.toString().trim())) {

                JOptionPane.showMessageDialog(null, "Please add Cost Price price in Numbers!");
                return false;
            }

            if(!uFunctions.isValidField(quantitySpinner.toString().trim()))
            {
                JOptionPane.showMessageDialog(null, "Please add Quantity price in Numbers!");
                return false;
            }


            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = dateFormat.parse(selectedExpiryDate);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            Product newProduct = new Product(0, productName, costPrice, sellingPrice, quantity, categoryID, sqlDate);
            if(productName.isEmpty())
            {
                return false;
            }
            if(productDao.isProductAlreadyExists(productName,sqlDate))
            {
                JOptionPane.showMessageDialog(null, "Product " + productName + " already exists.");
                return false;
            }

            boolean addResult = addProduct(newProduct);

            if (addResult) {
                JOptionPane.showMessageDialog(null, "Product " + productName + " added Successfully.");
               return true;
            } else
            {
                JOptionPane.showMessageDialog(null, "Failed to add product!");
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error parsing the selected date.");
            return false;
        }
    }
    public boolean showModifyProductDialog(Product existingProduct) {
        JPanel panel = new JPanel(new GridLayout(4, 2));

        JTextField nameField = new JTextField(existingProduct.getName());
        panel.add(new JLabel("Product Name:"));
        panel.add(nameField);

        JSpinner costPriceSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 0.1));
        costPriceSpinner.setPreferredSize(new Dimension(100, costPriceSpinner.getPreferredSize().height));
        panel.add(new JLabel("Enter Cost Price:"));
        panel.add(costPriceSpinner);

        JSpinner sellingPriceSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 0.1));
        sellingPriceSpinner.setPreferredSize(new Dimension(100, sellingPriceSpinner.getPreferredSize().height));
        panel.add(new JLabel("Enter Selling Price:"));
        panel.add(sellingPriceSpinner);

        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(existingProduct.getQuantity(), 0, Integer.MAX_VALUE, 1));
        panel.add(new JLabel("Quantity:"));
        panel.add(quantitySpinner);

        boolean exitCheck = false;
        int result = JOptionPane.showConfirmDialog(null, panel, "Modify Product", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.CANCEL_OPTION) {
            exitCheck = true;
            return false;
        }


        String modifiedProductName = nameField.getText();
        String inputCost = costPriceSpinner.toString().trim();
        String inputSelling = sellingPriceSpinner.toString().trim();
        int modifiedQuantity = (int) quantitySpinner.getValue();

        try {

            if(!uFunctions.isValidField(inputSelling))
            {

                JOptionPane.showMessageDialog(null, "Please add Selling price in Numbers!");
                return false;
            }

            if(!uFunctions.isValidField(inputCost))
            {
                JOptionPane.showMessageDialog(null, "Please add Cost price in Numbers!");
                return false;
            }

            if(!uFunctions.isValidField(quantitySpinner.toString()))
            {
                JOptionPane.showMessageDialog(null, "Please add Quantity in Numbers!");
                return false;
            }

            double modifiedCostPrice = inputCost.isEmpty() ? existingProduct.getCostPrice() : Double.parseDouble(inputCost);
            double modifiedSellingPrice = inputSelling.isEmpty() ? existingProduct.getSellingPrice() : Double.parseDouble(inputSelling);

            existingProduct.setName(modifiedProductName);
            existingProduct.setCostPrice(modifiedCostPrice);
            existingProduct.setSellingPrice(modifiedSellingPrice);
            existingProduct.setQuantity(modifiedQuantity);

            boolean updateResult = updateProduct(existingProduct);

            if(exitCheck)
            {
                return true;
            }

            if (updateResult) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update product details for " + existingProduct.getName());
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input for price. Please enter valid numbers.");
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

    public static void main(String[] args) {
        //String yearlyReport = generateReport("yearly", "2023");
        //String monthlyReport = generateReport("monthly", "2023-10");
        //String dailyReport = generateReport("daily", "2023-10-15");

    }


}




