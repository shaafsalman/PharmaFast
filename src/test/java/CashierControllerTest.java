import Controllers.CashierController;
import Dao.ProductDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CashierControllerTest {

    @Mock
    private ProductDao productDao;

    private CashierController cashierController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        cashierController = new CashierController(productDao);
    }

    @Test
    void getProductNameValid() {
        when(productDao.getProductName("123")).thenReturn("Product Name");
        assertEquals("Product Name", cashierController.getProductName("123"));
    }

    @Test
    void getProductNameInvalid() {
        assertNull(cashierController.getProductName(""));
    }

    @Test
    void getCategoryNameValid() {
        when(productDao.getCategoryName("123")).thenReturn("Category Name");
        assertEquals("Category Name", cashierController.getCategoryName("123"));
    }

    @Test
    void getCategoryNameInvalid() {
        assertNull(cashierController.getCategoryName(""));
    }

    @Test
    void getPriceValid() {
        when(productDao.getPrice("123")).thenReturn(100.0);
        assertEquals(100.0, cashierController.getPrice("123"));
    }

    @Test
    void getPriceInvalid() {
        assertEquals(0, cashierController.getPrice(""));
    }

    @Test
    void getAvailableQuantityValid() {
        when(productDao.getAvailableQuantity("123")).thenReturn(10);
        assertEquals(10, cashierController.getAvailableQuantity("123"));
    }

    @Test
    void getAvailableQuantityInvalid() {
        assertEquals(0, cashierController.getAvailableQuantity(""));
    }

    @Test
    void productExistsValid() {
        when(productDao.productExists("123")).thenReturn(true);
        assertTrue(cashierController.productExists("123"));
    }

    @Test
    void productExistsInvalid() {
        assertFalse(cashierController.productExists(""));
    }

    @Test
    void isQuantityAvailableValid() {
        when(productDao.isQuantityAvailable("123", 10)).thenReturn(true);
        assertTrue(cashierController.isQuantityAvailable("123", 10));
    }

    @Test
    void isQuantityAvailableInvalidProductId() {
        assertFalse(cashierController.isQuantityAvailable("", 10));
    }

    @Test
    void isQuantityAvailableNegativeQuantity() {
        assertFalse(cashierController.isQuantityAvailable("123", -1));
    }


}
