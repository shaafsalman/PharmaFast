import Helpers.UtilityFunctions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


//verified
class UtilityFunctionsTest {

    private UtilityFunctions utilityFunctions;

    @BeforeEach
    void init() {
        utilityFunctions = new UtilityFunctions();
    }

    @Test
    void createDayComboBox() {
        JComboBox<String> dayComboBox = utilityFunctions.createDayComboBox();
        assertNotNull(dayComboBox);
        assertEquals(31, dayComboBox.getItemCount());
        assertEquals("01", dayComboBox.getItemAt(0));
        assertEquals("31", dayComboBox.getItemAt(30));
    }

    @Test
    void createMonthComboBox() {
        JComboBox<String> monthComboBox = utilityFunctions.createMonthComboBox();
        assertNotNull(monthComboBox);
        assertEquals(12, monthComboBox.getItemCount());
        assertEquals("01", monthComboBox.getItemAt(0));
        assertEquals("12", monthComboBox.getItemAt(11));
    }

    @Test
    void createYearComboBox() {
        JComboBox<String> yearComboBox = utilityFunctions.createYearComboBox();
        assertNotNull(yearComboBox);
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        assertEquals(21, yearComboBox.getItemCount());
        assertEquals(String.valueOf(currentYear), yearComboBox.getItemAt(0));
        assertEquals(String.valueOf(currentYear - 20), yearComboBox.getItemAt(20));
    }

    @Test
    void createExpiryYearComboBox() {
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
        // Arrange
        UtilityFunctions utilityFunctions = new UtilityFunctions();
        String productName = "TestProduct";
        String category = "TestCategory";
        int currentQuantity = 5;
        int newQuantity = 10;

        boolean result = utilityFunctions.placeRestockOrder(productName, category, currentQuantity, newQuantity);

        assertTrue(result);

        File tempDirectory = new File("src/main/resources/StockRequests");
        File[] files = tempDirectory.listFiles();

        assertNotNull(files);
        assertEquals(1, files.length);

        String expectedContent = productName + " (" + category + "): Restock order - " + newQuantity + " Requested on " + LocalDate.now();
        String fileContent = new String(Files.readAllBytes(Path.of(files[0].getPath())));

        assertTrue(fileContent.contains(expectedContent));
    }




}
