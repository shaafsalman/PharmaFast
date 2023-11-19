
import Controllers.AdminController;
import Dao.CategoryDao;
import Dao.ProductDao;
import Models.Category;
import Models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminControllerTest {

    @Mock
    private CategoryDao categoryDao;
    @Mock
    private ProductDao productDao;

    private AdminController adminController;

    @BeforeEach
    void setUp() throws SQLException
    {
        MockitoAnnotations.initMocks(this);
        adminController = new AdminController(categoryDao, productDao);
    }

    @Test
    void addValidCategory() {
        Category category = new Category(1, "Electronics");
        when(categoryDao.addCategory(anyString())).thenReturn(true);

        assertTrue(adminController.addCategory(category));
        verify(categoryDao, times(1)).addCategory("Electronics");
    }

    @Test
    void addInvalidCategory() {
        Category category = new Category(1, "");
        assertFalse(adminController.addCategory(category));
    }

    @Test
    void editValidCategory() {
        Category category = new Category(1, "Electronics");
        when(categoryDao.editCategoryName(anyInt(), anyString())).thenReturn(true);

        assertTrue(adminController.editCategory(category));
        verify(categoryDao, times(1)).editCategoryName(1, "Electronics");
    }

    @Test
    void editInvalidCategory() {
        Category category = new Category(1, "");
        assertFalse(adminController.editCategory(category));
    }

    @Test
    void deleteCategory() {
        int categoryId = 1;
        when(categoryDao.deleteCategory(anyInt())).thenReturn(true);

        assertTrue(adminController.deleteCategory(categoryId));
        verify(categoryDao, times(1)).deleteCategory(categoryId);
    }

    @Test
    void addValidProduct() {
        Product product = new Product(1, "Laptop", 500.0, 700.0, 10, 1, null);
        when(productDao.addProduct(any(Product.class))).thenReturn(true);

        assertTrue(adminController.addProduct(product));
        verify(productDao, times(1)).addProduct(product);
    }

    @Test
    void addInvalidProduct() {
        Product product = new Product(1, "Laptop", 700.0, 500.0, 10, 1, null);
        assertFalse(adminController.addProduct(product));
    }

    @Test
    void updateValidProduct() {
        Product product = new Product(1, "Laptop", 500.0, 700.0, 10, 1, null);
        when(productDao.updateProduct(any(Product.class))).thenReturn(true);

        assertTrue(adminController.updateProduct(product));
        verify(productDao, times(1)).updateProduct(product);
    }

    // Test for updating an invalid product (empty product name)
    @Test
    void updateInvalidProduct() {
        Product product = new Product(1, "", 500.0, 700.0, 10, 1, null);
        assertFalse(adminController.updateProduct(product));
    }

    // Test for deleting a product
    @Test
    void deleteProduct() {
        int productId = 1;
        when(productDao.deleteProduct(anyInt())).thenReturn(true);

        assertTrue(adminController.deleteProduct(productId));
        verify(productDao, times(1)).deleteProduct(productId);
    }

}
