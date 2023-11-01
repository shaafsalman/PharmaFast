package Controllers;

import Helpers.ConnectionFile;

import java.sql.*;

public class CashierController{
        private final String url = ConnectionFile.dbURL;
        private final String username = ConnectionFile.DBusername;
        private final String password = ConnectionFile.DBpassword;
        public Connection connection;

        public static float vat = 5.0F;

        public CashierController() {
            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Check if a product exists in the database
        public boolean productExists(String productID) {
            String query = "SELECT * FROM Products WHERE ProductID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, productID);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next(); // Returns true if a row is found
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }

        // Check available quantity for a product
        public boolean isQuantityAvailable(String productID, int requiredQuantity) {
            String query = "SELECT Quantity FROM Products WHERE ProductID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, Integer.parseInt(productID));
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int availableQuantity = resultSet.getInt("Quantity");
                        return availableQuantity >= requiredQuantity;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }

        // Update quantity after a purchase
        public boolean updateQuantity(String productID, int purchasedQuantity) {
            String updateQuery = "UPDATE Products SET Quantity = Quantity - ? WHERE ProductID = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                updateStatement.setInt(1, purchasedQuantity);
                updateStatement.setString(2, productID);
                int rowsAffected = updateStatement.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }
    public String getProductName(String productID) {
        String query = "SELECT ProductName FROM Products WHERE ProductID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, productID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("ProductName");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
    public String getCategoryName(String productID) {
        String query = "SELECT CategoryName FROM Categories " +
                "INNER JOIN Products ON Categories.CategoryID = Products.CategoryID " +
                "WHERE ProductID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, productID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("CategoryName");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
    public double getPrice(String productID) {
        String query = "SELECT Price FROM Products WHERE ProductID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, productID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("Price");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
    public int getAvailableQuantity(String productID) {
        String query = "SELECT Quantity FROM Products WHERE ProductID = ?";
        int availableQuantity = 0;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, productID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    availableQuantity = resultSet.getInt("Quantity");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableQuantity;
    }






    // Additional SQL functions can be added here based on your application requirements
}