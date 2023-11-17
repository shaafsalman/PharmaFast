package Dao;

import Helpers.ConnectionFile;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class CategoryDao {

    private static final Connection connection;

    static {
        try {
            connection = ConnectionFile.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean getCategoryData(Map<Integer, String> categoryData) {
        categoryData.clear();

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT CategoryID, CategoryName FROM Categories");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int categoryID = resultSet.getInt("CategoryID");
                String categoryName = resultSet.getString("CategoryName");
                categoryData.put(categoryID, categoryName);
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public int getNumberOfProductsInCategory(int categoryID) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(ProductID) AS ProductCount FROM Products WHERE CategoryID = ?")) {
            preparedStatement.setInt(1, categoryID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("ProductCount");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
    public boolean editCategoryName(int categoryID, String newName) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Categories SET CategoryName = ? WHERE CategoryID = ?")) {
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, categoryID);

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteCategory(int categoryID) {
        try {
            connection.setAutoCommit(false);

            try (PreparedStatement productDeletionStatement = connection.prepareStatement("DELETE FROM Products WHERE CategoryID = ?");
                 PreparedStatement categoryDeletionStatement = connection.prepareStatement("DELETE FROM Categories WHERE CategoryID = ?")) {

                productDeletionStatement.setInt(1, categoryID);
                int productsDeleted = productDeletionStatement.executeUpdate();

                categoryDeletionStatement.setInt(1, categoryID);
                int categoryDeleted = categoryDeletionStatement.executeUpdate();

                connection.commit();
                return (productsDeleted > 0) || (categoryDeleted > 0);
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean addCategory(String categoryName) {
        try (PreparedStatement categoryInsertStatement = connection.prepareStatement("INSERT INTO Categories (CategoryName) VALUES (?)")) {
            int categoryId = getLargestCategoryID() + 1;

            categoryInsertStatement.setString(1, categoryName);
            int categoryInserted = categoryInsertStatement.executeUpdate();

            return categoryInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public int getLargestCategoryID() {
        try (PreparedStatement statement = connection.prepareStatement("SELECT MAX(CategoryID) AS LargestID FROM Categories");
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt("LargestID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
    public int getCategoryIDByName(String categoryName) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT CategoryID FROM Categories WHERE CategoryName = ?")) {
            preparedStatement.setString(1, categoryName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("CategoryID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
    public boolean isCategoryExists(int categoryID) {
        boolean categoryExists = false;
        try {
            String query = "SELECT * FROM Categories WHERE CategoryID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, categoryID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                categoryExists = true;
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryExists;
    }

}
