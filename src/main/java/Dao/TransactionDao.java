package Dao;

import Helpers.ConnectionFile;
import Models.Transaction;

import java.sql.*;
import java.time.LocalDate;
import java.util.Date;

public class TransactionDao {


    private static final Connection connection;

    static {
        try {
            connection = ConnectionFile.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addTransaction(int userID, double totalCost, Date transactionDate) throws SQLException {

    }


    public static PreparedStatement prepareStatementForDailyReport(String date) throws Exception {
        String sql = "SELECT t.TransactionDate, ti.ProductID, ti.Quantity, " +
                "p.Price AS CostPrice, p.SellingPrice, " +
                "(p.SellingPrice - p.Price) * ti.Quantity AS Profit " +
                "FROM TransactionItems ti " +
                "JOIN Transactions t ON ti.TransactionID = t.TransactionID " +
                "JOIN Products p ON ti.ProductID = p.ProductID " +
                "WHERE CAST(t.TransactionDate AS DATE) = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDate(1, java.sql.Date.valueOf(LocalDate.parse(date)));
        return statement;
    }
    public static PreparedStatement prepareStatementForMonthlyReport(String date) throws Exception {
        String sql = "SELECT YEAR(t.TransactionDate) AS Year, MONTH(t.TransactionDate) AS Month, " +
                "SUM(p.Price * ti.Quantity) AS TotalCostPrice, " +
                "SUM(p.SellingPrice * ti.Quantity) AS TotalSellingPrice " +
                "FROM TransactionItems ti " +
                "JOIN Transactions t ON ti.TransactionID = t.TransactionID " +
                "JOIN Products p ON ti.ProductID = p.ProductID " +
                "WHERE YEAR(t.TransactionDate) = ? AND MONTH(t.TransactionDate) = ? " +
                "GROUP BY YEAR(t.TransactionDate), MONTH(t.TransactionDate)";
        PreparedStatement statement = connection.prepareStatement(sql);
        String[] parts = date.split("-");
        statement.setInt(1, Integer.parseInt(parts[0]));
        statement.setInt(2, Integer.parseInt(parts[1]));
        return statement;
    }
    public static PreparedStatement prepareStatementForYearlyReport(String year) throws Exception {
        String sql = "SELECT MONTH(t.TransactionDate) AS Month, " +
                "SUM(p.Price * ti.Quantity) AS TotalCostPrice, " +
                "SUM(p.SellingPrice * ti.Quantity) AS TotalSellingPrice, " +
                "MIN(t.TransactionDate) AS FirstTransactionDate " +
                "FROM TransactionItems ti " +
                "JOIN Transactions t ON ti.TransactionID = t.TransactionID " +
                "JOIN Products p ON ti.ProductID = p.ProductID " +
                "WHERE YEAR(t.TransactionDate) = ? " +
                "GROUP BY MONTH(t.TransactionDate)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, Integer.parseInt(year));
        return statement;
    }

    public boolean addTransaction(Transaction transaction) {
        String sql = "INSERT INTO Transactions (UserID, TotalCost, TransactionDate) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, transaction.getUserID());
            statement.setDouble(2, transaction.getTotalCost());
            statement.setTimestamp(3, new Timestamp(transaction.getTransactionDate().getTime()));

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedID = generatedKeys.getInt(1);
                        transaction.setTransactionID(generatedID);
                        return true;
                    } else {
                        // No generated keys were returned
                        return false;
                    }
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
