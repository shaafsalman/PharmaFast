package Helpers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;

public class ReportGenerator {
    private static final Connection connection;

    static {
        try {
            connection = ConnectionFile.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateReport(String reportType, String date) {
        LocalDate day = LocalDate.now();
        String pdfOutputFile = "src/main/resources/Reports/" + reportType + "for" + date + "_" + day + ".pdf";
        String sql = "";

        try {

            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfOutputFile));
            document.open();

            Image logo = Image.getInstance("src/main/resources/Material/apple-touch-icon.png");
            logo.setAlignment(Element.ALIGN_CENTER);
            logo.scaleAbsolute(45, 45);
            document.add(logo);

            Paragraph header = new Paragraph("PharmaFast - " + reportType + " Report - " + date + "\n\n",
                    FontFactory.getFont("Century Gothic", 18, BaseColor.BLACK));
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);


            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.addCell(getHeaderCell("Transaction ID"));
            table.addCell(getHeaderCell("UserID"));
            table.addCell(getHeaderCell("TotalCost"));
            table.addCell(getHeaderCell("Transaction Date"));

            PreparedStatement statement;
            ResultSet resultSet;

            if (reportType.equals("daily")) {
                sql = "SELECT TransactionID, UserID, TotalCost, TransactionDate " +
                        "FROM Transactions " +
                        "WHERE CONVERT(DATE, TransactionDate) = ?";

            } else if (reportType.equals("monthly")) {
                sql = "SELECT TransactionID, UserID, TotalCost, TransactionDate " +
                        "FROM Transactions " +
                        "WHERE YEAR(TransactionDate) = ? AND MONTH(TransactionDate) = ?";

            } else if (reportType.equals("yearly")) {
                sql = "SELECT TransactionID, UserID, TotalCost, TransactionDate " +
                        "FROM Transactions " +
                        "WHERE YEAR(TransactionDate) = YEAR(?)";
            }

            statement = connection.prepareStatement(sql);

            if (reportType.equals("daily")) {
                statement.setString(1, date);
            } else if (reportType.equals("monthly")) {
                String[] parts = date.split("-");
                String year = parts[0];
                String month = parts[1];
                System.out.println(Integer.parseInt(year));
                System.out.println(Integer.parseInt(month));

                statement.setInt(1, Integer.parseInt(year));
                statement.setInt(2, Integer.parseInt(month));
            } else if (reportType.equals("yearly")) {
                statement.setString(1, date);
            }


            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                table.addCell(getCell(String.valueOf(resultSet.getInt("TransactionID"))));
                table.addCell(getCell(String.valueOf(resultSet.getInt("UserID"))));
                table.addCell(getCell(String.valueOf(resultSet.getDouble("TotalCost"))));
                table.addCell(getCell(String.valueOf(resultSet.getTimestamp("TransactionDate"))));
            }

            document.add(table);

            Paragraph footer = new Paragraph("Report generated on: " + LocalDate.now(),
                    FontFactory.getFont("Century Gothic", 12, BaseColor.BLACK));
            footer.setAlignment(Element.ALIGN_RIGHT);
            document.add(footer);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pdfOutputFile;
    }

    private static PdfPCell getHeaderCell(String content) {
        com.itextpdf.text.Font font = FontFactory.getFont("Century Gothic", 14, BaseColor.WHITE);
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setBackgroundColor(BaseColor.DARK_GRAY);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    private static PdfPCell getCell(String content) {
        PdfPCell cell = new PdfPCell(new Phrase(content, FontFactory.getFont("Century Gothic", 12, BaseColor.BLACK)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    public String generateReceipt(JTable tableCart, String total, String subTotal, String vat, String paidAmount, String change) {
        LocalDate day = LocalDate.now();
        String pdfOutputFile = "src/main/resources/Recipts/Report_" + day + ".pdf";

        try {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfOutputFile));
            document.open();

            Image logo = Image.getInstance("src/main/resources/Material/apple-touch-icon.png");
            logo.setAlignment(Element.ALIGN_CENTER);
            logo.scaleAbsolute(45, 45);
            document.add(logo);

            Paragraph title = new Paragraph("PharmaFast - Recipt\n\n",
                    FontFactory.getFont("Century Gothic", 18, BaseColor.BLACK));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            PdfPTable cartTable = new PdfPTable(6); // Change the number of columns based on your cart table
            cartTable.setWidthPercentage(100);

            cartTable.addCell(getHeaderCell("Product ID"));
            cartTable.addCell(getHeaderCell("Product Name"));
            cartTable.addCell(getHeaderCell("Category"));
            cartTable.addCell(getHeaderCell("Price"));
            cartTable.addCell(getHeaderCell("Quantity"));
            cartTable.addCell(getHeaderCell("Total Price"));

            DefaultTableModel model = (DefaultTableModel) tableCart.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                cartTable.addCell(getCell(model.getValueAt(i, 0).toString())); // Product ID
                cartTable.addCell(getCell(model.getValueAt(i, 1).toString())); // Product Name
                cartTable.addCell(getCell(model.getValueAt(i, 2).toString())); // Category
                cartTable.addCell(getCell(model.getValueAt(i, 3).toString())); // Price
                cartTable.addCell(getCell(model.getValueAt(i, 4).toString())); // Quantity
                cartTable.addCell(getCell(model.getValueAt(i, 5).toString())); // Total Price
            }

            document.add(cartTable);


            Paragraph totalParagraph = new Paragraph("Total: " + total);
            totalParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(totalParagraph);

            Paragraph subTotalParagraph = new Paragraph("Subtotal: " + subTotal);
            subTotalParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(subTotalParagraph);

            Paragraph vatParagraph = new Paragraph("VAT: " + vat);
            vatParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(vatParagraph);

            Paragraph paidAmountParagraph = new Paragraph("Paid Amount: " + paidAmount);
            paidAmountParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(paidAmountParagraph);

            Paragraph changeParagraph = new Paragraph("Change: " + change);
            changeParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(changeParagraph);

            Paragraph footer = new Paragraph("\n\n\nReport generated on: " + LocalDate.now(),
                    FontFactory.getFont("Century Gothic", 12, BaseColor.BLACK));
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pdfOutputFile;
    }

    public static String generateStockRequest(String pathtoLogFile) {
        String outputDirectory = "src/main/resources/StockRequestsOutputs";
        LocalDate day = LocalDate.now();
        String pdfOutputFile = outputDirectory + "/StockRequest_" + day + ".pdf";

        try {
            Path outputPath = Paths.get(outputDirectory);
            if (Files.notExists(outputPath)) {
                Files.createDirectories(outputPath);
            }

            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfOutputFile));
            document.open();

            Image logo = Image.getInstance("src/main/resources/Material/apple-touch-icon.png");
            logo.setAlignment(Element.ALIGN_CENTER);
            logo.scaleAbsolute(45, 45);
            document.add(logo);

            Paragraph title = new Paragraph("Stock Request\n\n",
                    FontFactory.getFont("Century Gothic", 18, BaseColor.BLACK));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Add table for displaying main body data
            PdfPTable dataTable = new PdfPTable(3);
            dataTable.setWidthPercentage(100);

            // Headers
            dataTable.addCell(getHeaderCell("Product"));
            dataTable.addCell(getHeaderCell("Category"));
            dataTable.addCell(getHeaderCell("Quantity"));

            // Read stock requests from a file and populate the table
            try (BufferedReader br = new BufferedReader(new FileReader(pathtoLogFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    // Split the line based on commas
                    String[] parts = line.trim().split("\\s*,\\s*");

                    // Assuming the format is "Product,Category,Quantity,Restock order"
                    if (parts.length == 4 && parts[3].equalsIgnoreCase("Restock order")) {
                        String product = parts[0];
                        String category = parts[1];
                        String quantity = parts[2];

                        // Add data to the table
                        dataTable.addCell(getCell(product));
                        dataTable.addCell(getCell(category));
                        dataTable.addCell(getCell(quantity));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            document.add(dataTable);

            Paragraph footer = new Paragraph("\n\n\nRequest generated on: " + LocalDate.now(),
                    FontFactory.getFont("Century Gothic", 12, BaseColor.BLACK));
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pdfOutputFile;
    }
//    public static void main(String[] args) {
//        String generatedFile = generateStockRequest("src/main/resources/StockRequests/StockRequest_2023-11-25.txt");
//        System.out.println("Stock Request generated successfully: " + generatedFile);
//     }




}
