package Dao;

import Helpers.ConnectionFile;
import Models.Transaction;
import Models.TransactionItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionItemDao {
    private static final Connection connection;
    ProductDao productDao = new ProductDao();


    static {
        try {
            connection = ConnectionFile.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean addTransactionItem(TransactionItem transactionItem) {
        try {
            int currentQuantity = productDao.getAvailableQuantity(String.valueOf((int)(transactionItem.getProductID())));

            int newQuantity = currentQuantity - transactionItem.getQuantity();

            if (newQuantity < 0) {
                return false;
            }
            if (productDao.updateProductQuantity(transactionItem.getProductID(), newQuantity)) {
                // If the quantity update is successful, add the transaction item
                String sql = "INSERT INTO TransactionItems (TransactionID, ProductID, Quantity) VALUES (?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, transactionItem.getTransactionID());
                    statement.setInt(2, transactionItem.getProductID());
                    statement.setInt(3, transactionItem.getQuantity());

                    int rowsAffected = statement.executeUpdate();
                    return rowsAffected > 0;
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }





}
