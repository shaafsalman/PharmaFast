package Helpers;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

public class UtilityFunctions {

    public JComboBox<String> createDayComboBox() {
        JComboBox<String> dayComboBox = new JComboBox<>();

        for (int i = 1; i <= 31; i++) {
            dayComboBox.addItem(String.format("%02d", i));
        }

        return dayComboBox;
    }

    public JComboBox<String> createMonthComboBox() {
        JComboBox<String> monthComboBox = new JComboBox<>();

        for (int i = 1; i <= 12; i++) {
            monthComboBox.addItem(String.format("%02d", i));
        }

        return monthComboBox;
    }

    public JComboBox<String> createYearComboBox() {
        JComboBox<String> yearComboBox = new JComboBox<>();

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        for (int i = currentYear; i >= currentYear - 20; i--) {
            yearComboBox.addItem(String.valueOf(i));
        }

        return yearComboBox;
    }


    public JComboBox<String> createExpiryYearComboBox() {
        JComboBox<String> yearComboBox = new JComboBox<>();

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        for (int i = currentYear; i <= currentYear + 10; i++) {
            yearComboBox.addItem(String.valueOf(i));
        }

        return yearComboBox;
    }

    public static boolean validateEmail(String email) {
        return email.contains("@");
    }


    public static boolean validatePassword(String password, String repeatPassword) {
        return password.equals(repeatPassword);
    }

    public static boolean isValidDate(int year, int month, int day) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        String dateString = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);

        try {
            dateFormat.parse(dateString);

            if (year < 1 || year > 9999 || month < 1 || month > 12 || day < 1 || day > 31) {
                return false;
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setLenient(false);
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month - 1);
            calendar.set(Calendar.DATE, day);

            calendar.getTime();
            return true;
        } catch (IllegalArgumentException | ParseException e) {
            return false;
        }
    }
    public void displayReport(String filePath) {
        if (filePath != null && !filePath.isEmpty()) {
            File reportFile = new File(filePath);

            if (reportFile.exists()) {
                try {
                    Desktop.getDesktop().open(reportFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "The generated report file does not exist.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No report file generated.");
        }
    }
    public static boolean placeRestockOrder(String productName, String category, int currentQuantity, int newQuantity) {
        LocalDate today = LocalDate.now();
        String filePath = "src/main/resources/StockRequests";
        File fileDirectory = new File(filePath);

        if (!fileDirectory.exists()) {
            fileDirectory.mkdirs();
        }

        String fileName = "StockRequest_" + today + ".txt";
        String orderDetails = productName + " (" + category + "): Restock order - " + newQuantity + " Requested on " + today;

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + File.separator + fileName, true)); // Append mode
            writer.write(orderDetails);
            writer.newLine();
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void initializeUForTable(JTable table) {


        table.setRowHeight(40);

        table.setOpaque(false);
        table.setShowGrid(false);
        table.setBackground(new Color(255, 255, 255));
        table.setSelectionBackground(new Color(52, 52, 52));

        class HeaderRenderer extends DefaultTableCellRenderer {
            public HeaderRenderer() {
                setHorizontalAlignment(SwingConstants.CENTER);
            }
        }

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Century Gothic", Font.PLAIN, 18));
        header.setBackground(new Color(47, 47, 47));
        header.setForeground(Color.darkGray);
        header.setBorder(new EmptyBorder(20, 10, 20, 10));

        TableCellRenderer headerRenderer = new HeaderRenderer();
        table.getTableHeader().setDefaultRenderer(headerRenderer);

        table.setFont(new Font("Century Gothic", Font.PLAIN, 14));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

    }

}
