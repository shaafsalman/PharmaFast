package Dao;

import Helpers.ConnectionFile;
import Models.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {

    private static final Connection connection;

    static {
        try {
            connection = ConnectionFile.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean addProduct(Product product) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO Products (ProductName, Price, SellingPrice, Quantity, CategoryID, ExpiryDate) VALUES (?, ?, ?, ?, ?, ?)")) {

            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setDouble(2, product.getCostPrice());
            preparedStatement.setDouble(3, product.getSellingPrice());
            preparedStatement.setInt(4, product.getQuantity());
            preparedStatement.setInt(5, product.getCategoryID());
            preparedStatement.setDate(6, product.getExpiryDate());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateProduct(Product product) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE Products SET ProductName = ?, Price = ?, SellingPrice = ?, Quantity = ? WHERE ProductID = ?")) {

            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setDouble(2, product.getCostPrice());
            preparedStatement.setDouble(3, product.getSellingPrice());
            preparedStatement.setInt(4, product.getQuantity());
            preparedStatement.setInt(5, product.getProductID());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteProduct(int productId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM Products WHERE ProductID = ?")) {

            preparedStatement.setInt(1, productId);
            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public ResultSet getAllProductData() throws SQLException {
        String query = "SELECT Products.ProductID, Categories.CategoryName, Products.ProductName, " +
                "Products.Price, Products.SellingPrice, Products.Quantity, Products.ExpiryDate " +
                "FROM Products INNER JOIN Categories ON Products.CategoryID = Categories.CategoryID";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        return preparedStatement.executeQuery();
    }
    public int getProductID(String productName, Date expiryDate) {
        int productID = -1;

        try
        {
            String query = "SELECT productID FROM Products WHERE productName = ? AND expiryDate = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, productName);
            statement.setDate(2, expiryDate);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                productID = resultSet.getInt("productID");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productID;
    }
    public boolean updateProductQuantity(int productID, int newQuantity) {
        try {
            String selectQuery = "SELECT Quantity FROM Products WHERE ProductID = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setInt(1, productID);

            ResultSet resultSet = selectStatement.executeQuery();

            int currentQuantity = 0;
            if (resultSet.next()) {
                currentQuantity = resultSet.getInt("Quantity");
            }

            int totalQuantity = currentQuantity + newQuantity;

            String updateQuery = "UPDATE Products SET Quantity = ? WHERE ProductID = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setInt(1, totalQuantity);
            updateStatement.setInt(2, productID);

            int rowsUpdated = updateStatement.executeUpdate();

            resultSet.close();
            selectStatement.close();
            updateStatement.close();

            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public ResultSet getProductsTotalQuantityGreaterThanZero() throws SQLException {
        String query = "SELECT P.ProductName, C.CategoryName, SUM(P.Quantity) AS TotalQuantity " +
                "FROM Products P " +
                "JOIN Categories C ON P.CategoryID = C.CategoryID " +
                "GROUP BY P.ProductName, C.CategoryName " +
                "HAVING SUM(P.Quantity) > 0 " +
                "ORDER BY TotalQuantity ASC";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        return preparedStatement.executeQuery();
    }
    public ResultSet getProductsZeroQuantity() throws SQLException {
        String query = "SELECT P.ProductName, C.CategoryName, P.Quantity " +
                "FROM Products P " +
                "JOIN Categories C ON P.CategoryID = C.CategoryID " +
                "WHERE P.Quantity = 0";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        return preparedStatement.executeQuery();
    }
    public boolean productExists(String productID) {
        String query = "SELECT * FROM Products WHERE ProductID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, productID);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
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

}
