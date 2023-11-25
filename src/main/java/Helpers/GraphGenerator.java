package Helpers;

import Dao.TransactionDao;
import Models.Transaction;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;  // Add this import for YearMonth
import java.time.format.DateTimeFormatter;
import java.util.Date;
public class GraphGenerator {

    public static String generateGraph(String reportType, String date) {
        LocalDate day = LocalDate.now();
        String imagePath = "src/main/resources/Graphs/" + reportType + "for" + date + "_" + day + ".jpeg";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {
            PreparedStatement statement = prepareStatementForReportType(reportType, date);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                LocalDate dataDate;
                double totalCost;
                double totalSales;
                double profit;

                switch (reportType) {
                    case "daily":
                        dataDate = resultSet.getDate("TransactionDate").toLocalDate();
                        totalCost = resultSet.getDouble("CostPrice");
                        totalSales = resultSet.getDouble("SellingPrice");
                        profit = resultSet.getDouble("Profit");
                        break;
                    case "monthly":
                        int month = resultSet.getInt("Month");
                        int year = resultSet.getInt("Year");
                        dataDate = YearMonth.of(year, month).atDay(1);
                        totalCost = resultSet.getDouble("TotalCostPrice");
                        totalSales = resultSet.getDouble("TotalSellingPrice");
                        profit = totalSales - totalCost;
                        break;
                    case "yearly":
                        month = resultSet.getInt("Month");
                        year = Integer.parseInt(date);
                        dataDate = YearMonth.of(year, month).atDay(1);
                        totalCost = resultSet.getDouble("TotalCostPrice");
                        totalSales = resultSet.getDouble("TotalSellingPrice");
                        profit = totalSales - totalCost;
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid report type: " + reportType);
                }

                // Add the values to the dataset
                dataset.addValue(totalCost, "Cost Price", dataDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
                dataset.addValue(totalSales, "Selling Price", dataDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
                dataset.addValue(profit, "Profit", dataDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
            }

            JFreeChart chart;

            // Select the type of chart based on the report type
            if (reportType.equals("daily")) {
                chart = ChartFactory.createStackedBarChart(
                        reportType + " Profit/Loss Report",
                        "Date",
                        "Amount",
                        dataset
                );
            } else if (reportType.equals("monthly")) {
                chart = ChartFactory.createBarChart(
                        reportType + " Profit/Loss Report",
                        "Date",
                        "Amount",
                        dataset
                );
            } else { // "yearly"
                chart = ChartFactory.createStackedBarChart(
                        reportType + " Profit/Loss Report",
                        "Date",
                        "Amount",
                        dataset
                );
            }
            PreparedStatement statement2 = prepareStatementForReportType(reportType, date);
            ResultSet resultSet2 = statement.executeQuery();
            String subtitle = generateSubtitle(resultSet2,reportType);
            chart.addSubtitle(new TextTitle(subtitle));

            ChartUtils.saveChartAsJPEG(new File(imagePath), chart, 800, 600);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imagePath;
    }
    private static String generateSubtitle(ResultSet resultSet, String reportType) throws SQLException {
        double totalProfit = 0.0;
        double totalCost = 0.0;
        double totalSales = 0.0;

        while (resultSet.next()) {
            double costPrice;
            double sellingPrice;

            switch (reportType) {
                case "daily":
                    costPrice = resultSet.getDouble("CostPrice");
                    sellingPrice = resultSet.getDouble("SellingPrice");
                    break;
                case "monthly":
                case "yearly":
                    costPrice = resultSet.getDouble("TotalCostPrice");
                    sellingPrice = resultSet.getDouble("TotalSellingPrice");
                    break;
                default:
                    throw new IllegalArgumentException("Invalid report type: " + reportType);
            }

            totalProfit += (sellingPrice - costPrice);
            totalCost += costPrice;
            totalSales += sellingPrice;
        }

        switch (reportType) {
            case "daily":
                return String.format("Daily Report - Total Profit: PKR%.2f | Total Cost: PKR%.2f | Total Sales: PKR%.2f",
                        totalProfit, totalCost, totalSales);
            case "monthly":
                return String.format("Monthly Report - Total Profit: PKR%.2f | Total Cost: PKR%.2f | Total Sales: PKR%.2f",
                        totalProfit, totalCost, totalSales);
            case "yearly":
                return String.format("Yearly Report - Total Profit: PKR%.2f | Total Cost: PKR%.2f | Total Sales: PKR%.2f",
                        totalProfit, totalCost, totalSales);
            default:
                throw new IllegalArgumentException("Invalid report type: " + reportType);
        }
    }
    private static PreparedStatement prepareStatementForReportType(String reportType, String date) throws Exception {
        if (reportType.equals("daily")) {
            return TransactionDao.prepareStatementForDailyReport(date);
        } else if (reportType.equals("monthly")) {
            return TransactionDao.prepareStatementForMonthlyReport(date);
        } else if (reportType.equals("yearly")) {
            return TransactionDao.prepareStatementForYearlyReport(date);
        } else {
            throw new IllegalArgumentException("Invalid report type");
        }
    }


    public static void main(String[] args)
    {
        String dailyGraphPath = GraphGenerator.generateGraph("daily", "2022-01-01");
        System.out.println("Daily report graph generated at: " + dailyGraphPath);

        String monthlyGraphPath = GraphGenerator.generateGraph("monthly", "2022-11");
        System.out.println("Monthly report graph generated at: " + monthlyGraphPath);

        String yearlyGraphPath = GraphGenerator.generateGraph("yearly", "2022");
        System.out.println("Yearly report graph generated at: " + yearlyGraphPath);
    }
}
