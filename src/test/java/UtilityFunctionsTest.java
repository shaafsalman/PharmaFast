import Helpers.UtilityFunctions;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class UtilityFunctionsTest {

    @Test
    void createDayComboBox() {
        UtilityFunctions utilityFunctions = new UtilityFunctions();
        JComboBox<String> dayComboBox = utilityFunctions.createDayComboBox();
        assertNotNull(dayComboBox);
        assertEquals(31, dayComboBox.getItemCount());
        assertEquals("01", dayComboBox.getItemAt(0));
        assertEquals("31", dayComboBox.getItemAt(30));
    }

    @Test
    void createMonthComboBox() {
        UtilityFunctions utilityFunctions = new UtilityFunctions();
        JComboBox<String> monthComboBox = utilityFunctions.createMonthComboBox();
        assertNotNull(monthComboBox);
        assertEquals(12, monthComboBox.getItemCount());
        assertEquals("01", monthComboBox.getItemAt(0));
        assertEquals("12", monthComboBox.getItemAt(11));
    }

    @Test
    void createYearComboBox() {
        UtilityFunctions utilityFunctions = new UtilityFunctions();
        JComboBox<String> yearComboBox = utilityFunctions.createYearComboBox();
        assertNotNull(yearComboBox);
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        assertEquals(21, yearComboBox.getItemCount());
        assertEquals(String.valueOf(currentYear), yearComboBox.getItemAt(0));
        assertEquals(String.valueOf(currentYear - 20), yearComboBox.getItemAt(20));
    }

    @Test
    void createExpiryYearComboBox() {
        UtilityFunctions utilityFunctions = new UtilityFunctions();
        JComboBox<String> yearComboBox = utilityFunctions.createExpiryYearComboBox();
        assertNotNull(yearComboBox);
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        assertEquals(11, yearComboBox.getItemCount());
        assertEquals(String.valueOf(currentYear), yearComboBox.getItemAt(0));
        assertEquals(String.valueOf(currentYear + 10), yearComboBox.getItemAt(10));
    }

    @Test
    void validateEmail() {
        assertTrue(UtilityFunctions.validateEmail("test@example.com"));
        assertFalse(UtilityFunctions.validateEmail("invalid-email"));
    }

    @Test
    void validatePassword() {
        assertTrue(UtilityFunctions.validatePassword("password", "password"));
        assertFalse(UtilityFunctions.validatePassword("password", "different-password"));
    }

    @Test
    void isValidDate() {
        assertTrue(UtilityFunctions.isValidDate(2023, 11, 24));
        assertFalse(UtilityFunctions.isValidDate(2023, 13, 24)); // Invalid month
        assertFalse(UtilityFunctions.isValidDate(2023, 11, 32)); // Invalid day
        assertFalse(UtilityFunctions.isValidDate(-1, 11, 24));   // Invalid year
    }


    @Test
    void placeRestockOrder() throws IOException {
        UtilityFunctions utilityFunctions = new UtilityFunctions();
        File tempDirectory = Files.createTempDirectory("test-stock-requests").toFile();
        utilityFunctions.placeRestockOrder("Product1", "Category1", 10, 20);

        File[] files = tempDirectory.listFiles();
        assertNotNull(files);
        assertEquals(1, files.length);

        String content = new String(Files.readAllBytes(Path.of(files[0].getPath())));
        assertTrue(content.contains("Product1 (Category1): Restock order - 20 Requested on"));
    }


}
