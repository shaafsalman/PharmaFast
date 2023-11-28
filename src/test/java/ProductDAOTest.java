import Dao.ProductDao;
import Models.Product;
import org.junit.jupiter.api.Test;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoTest {

    private final ProductDao productDao = new ProductDao();

    @Test
    void getProduct() {
        Product product = productDao.getProduct(1);
        assertNotNull(product);
        assertEquals("TestProduct", product.getProductName());
    }

    @Test
    void addProduct() {
        Product product = new Product(0, "NewProduct", 10.0, 15.0, 20, 1, Date.valueOf("2023-11-28"));
        assertTrue(productDao.addProduct(product));
    }

    @Test
    void updateProduct() {
        Product product = new Product(1, "UpdatedProduct", 12.0, 18.0, 25, 1, Date.valueOf("2023-11-28"));
        assertTrue(productDao.updateProduct(product));
    }

    @Test
    void deleteProduct() {
        assertTrue(productDao.deleteProduct(1));
    }

    @Test
    void getProductName() {
        String productName = productDao.getProductName("1");
        assertEquals("TestProduct", productName);
    }
    

    @Test
    void productExists() {
        assertTrue(productDao.productExists("1"));
        assertFalse(productDao.productExists("-1"));
    }

    @Test
    void isQuantityAvailable() {
        assertTrue(productDao.isQuantityAvailable("1", 5));
        assertFalse(productDao.isQuantityAvailable("1", 100));
    }

    @Test
    void getAvailableQuantity() {
        int availableQuantity = productDao.getAvailableQuantity("1");
        assertTrue(availableQuantity >= 0);
    }
}
