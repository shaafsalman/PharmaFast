package Helpers;

import javax.swing.*;
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
    public JComboBox<String> createYearComboBox()
    {
        JComboBox<String> yearComboBox = new JComboBox<>();

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        for (int i = currentYear; i >= currentYear - 20; i--) {
            yearComboBox.addItem(String.valueOf(i));
        }

        return yearComboBox;
    }
    public JComboBox<String> createExpiryYearComboBox()
    {
        JComboBox<String> yearComboBox = new JComboBox<>();

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        for (int i = currentYear; i <= currentYear + 10; i++) {
            yearComboBox.addItem(String.valueOf(i));
        }

        return yearComboBox;
    }
    public JComboBox<String> createMonthComboBox()
    {
        JComboBox<String> monthComboBox = new JComboBox<>();

        // Populate the months (1 to 12)
        for (int i = 1; i <= 12; i++) {
            monthComboBox.addItem(String.format("%02d", i));
        }

        return monthComboBox;
    }
    public JComboBox<String> createDayComboBox() {
        JComboBox<String> dayComboBox = new JComboBox<>();

        for (int i = 1; i <= 31; i++) {
            dayComboBox.addItem(String.format("%02d", i));
        }

        return dayComboBox;
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

            // Use Calendar to check for specific month lengths (considering leap years for February)
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
            // Assuming the filePath is a path to the generated report file
            File reportFile = new File(filePath);

            if (reportFile.exists()) {
                // If the file exists, you can open it or perform any action here
                try {
                    Desktop.getDesktop().open(reportFile); // Opens the file using the system default application
                } catch (IOException e) {
                    e.printStackTrace(); // Handle the error if the file cannot be opened
                }
            } else {
                // Handle case where the file does not exist
                JOptionPane.showMessageDialog(null, "The generated report file does not exist.");
            }
        } else {
            // Handle case where the file path is empty or null
            JOptionPane.showMessageDialog(null, "No report file generated.");
        }
    }
    public static boolean placeRestockOrder(String productName, String category, int currentQuantity, int newQuantity) {
        LocalDate today = LocalDate.now();
        String filePath = "src/main/resources/StockRequests";
        File fileDirectory = new File(filePath);

        // Create the directory if it doesn't exist
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



}
