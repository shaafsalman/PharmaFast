package Controllers;
import Dao.ProductDao;

public class CashierController
{

    private final ProductDao productDao;
    public final float VAT;

    public CashierController() {
        this.productDao = new ProductDao();
        this.VAT = AdminController.getVatRate();
    }

    public CashierController(ProductDao productDao) {
        this.productDao = productDao;
        this.VAT = AdminController.getVatRate();
    }

    public boolean productExists(String productID) {
        if (!isValidProductId(productID)) {
            return false;
        }
        return productDao.productExists(productID);
    }

    public String getProductName(String productID) {
        if (!isValidProductId(productID)) {
            return null;
        }
        return productDao.getProductName(productID);
    }

    public double getPrice(String productID) {
        if (!isValidProductId(productID)) {
            return 0;
        }
        return productDao.getPrice(productID);
    }

    public boolean isQuantityAvailable(String productID, int requiredQuantity) {
        if (!isValidProductId(productID) || requiredQuantity < 0) {
            return false;
        }
        return productDao.isQuantityAvailable(productID, requiredQuantity);
    }

    public int getAvailableQuantity(String productID) {
        if (!isValidProductId(productID)) {
            return 0;
        }
        return productDao.getAvailableQuantity(productID);
    }
    public String getCategoryName(String productID) {
        if (!isValidProductId(productID)) {
            return null;
        }
        return productDao.getCategoryName(productID);
    }

    private boolean isValidProductId(String productID) {
        return productID != null && !productID.trim().isEmpty();
    }
}
