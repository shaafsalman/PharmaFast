import Models.Product;
import org.junit.jupiter.api.Test;
import java.sql.Date;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void getProductID() {
        Product product = new Product(1, "ProdTest", 10.0, 20.0, 50, 1, Date.valueOf("2023-11-28"));
        assertEquals(1, product.getProductID());
    }

    @Test
    void getProductName() {
        Product product = new Product(1, "ProdTest", 10.0, 20.0, 50, 1, Date.valueOf("2023-11-28"));
        assertEquals("ProdTest", product.getProductName());
    }

    @Test
    void getCostPrice() {
        Product product = new Product(1, "ProdTest", 10.0, 20.0, 50, 1, Date.valueOf("2023-11-28"));
        assertEquals(10.0, product.getCostPrice());
    }

    @Test
    void getSellingPrice() {
        Product product = new Product(1, "ProdTest", 10.0, 20.0, 50, 1, Date.valueOf("2023-11-28"));
        assertEquals(20.0, product.getSellingPrice());
    }

    @Test
    void getQuantity() {
        Product product = new Product(1, "ProdTest", 10.0, 20.0, 50, 1, Date.valueOf("2023-11-28"));
        assertEquals(50, product.getQuantity());
    }

    @Test
    void getCategoryID() {
        Product product = new Product(1, "ProdTest", 10.0, 20.0, 50, 1, Date.valueOf("2023-11-28"));
        assertEquals(1, product.getCategoryID());
    }

    @Test
    void getExpiryDate() {
        Date expiryDate = Date.valueOf("2023-11-28");
        Product product = new Product(1, "ProdTest", 10.0, 20.0, 50, 1, expiryDate);
        assertEquals(expiryDate, product.getExpiryDate());
    }

    @Test
    void setCategoryID() {
        Product product = new Product(1, "ProdTest", 10.0, 20.0, 50, 1, Date.valueOf("2023-11-28"));
        product.setCategoryID(2);
        assertEquals(2, product.getCategoryID());
    }

    @Test
    void setExpiryDate() {
        Date newExpiryDate = Date.valueOf("2023-02-01");
        Product product = new Product(1, "ProdTest", 10.0, 20.0, 50, 1, Date.valueOf("2023-11-28"));
        product.setExpiryDate(newExpiryDate);
        assertEquals(newExpiryDate, product.getExpiryDate());
    }

    @Test
    void setName() {
        Product product = new Product(1, "ProdTest", 10.0, 20.0, 50, 1, Date.valueOf("2023-11-28"));
        product.setName("NewTestProduct");
        assertEquals("NewTestProduct", product.getName());
    }

    @Test
    void setCostPrice() {
        Product product = new Product(1, "ProdTest", 10.0, 20.0, 50, 1, Date.valueOf("2023-11-28"));
        product.setCostPrice(15.0);
        assertEquals(15.0, product.getCostPrice());
    }

    @Test
    void setSellingPrice() {
        Product product = new Product(1, "ProdTest", 10.0, 20.0, 50, 1, Date.valueOf("2023-11-28"));
        product.setSellingPrice(25.0);
        assertEquals(25.0, product.getSellingPrice());
    }

    @Test
    void setQuantity() {
        Product product = new Product(1, "ProdTest", 10.0, 20.0, 50, 1, Date.valueOf("2023-11-28"));
        product.setQuantity(60);
        assertEquals(60, product.getQuantity());
    }

}
