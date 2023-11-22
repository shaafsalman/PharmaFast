package Dao;

import Helpers.ConnectionFile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class TransactionDao {


    private static final Connection connection;

    static {
        try {
            connection = ConnectionFile.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
                "MIN(t.TransactionDate) AS FirstTransactionDate " + // The first transaction date of the month
                "FROM TransactionItems ti " +
                "JOIN Transactions t ON ti.TransactionID = t.TransactionID " +
                "JOIN Products p ON ti.ProductID = p.ProductID " +
                "WHERE YEAR(t.TransactionDate) = ? " +
                "GROUP BY MONTH(t.TransactionDate)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, Integer.parseInt(year)); // Year for the report
        return statement;
    }

}
