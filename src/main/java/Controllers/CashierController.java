package Controllers;

import Dao.ProductDao;
import Helpers.ConnectionFile;
import org.bouncycastle.asn1.x509.sigi.PersonalData;

import java.sql.*;

public class CashierController{

        ProductDao productDao = new ProductDao();
        public static float vat = 5.0F;


    public boolean productExists(String productID) {return productDao.productExists(productID);}
    public boolean isQuantityAvailable(String productID, int requiredQuantity) {return productDao.isQuantityAvailable(productID,requiredQuantity);}
    public String getProductName(String productID) {return productDao.getProductName(productID);}
    public String getCategoryName(String productID) {return  productDao.getCategoryName(productID);}
    public double getPrice(String productID) {
        return productDao.getPrice(productID);
    }
    public int getAvailableQuantity(String productID) {
       return productDao.getAvailableQuantity(productID);
    }
}