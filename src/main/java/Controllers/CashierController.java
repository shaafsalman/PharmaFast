package Controllers;
import Dao.ProductDao;
import Dao.TransactionDao;
import Dao.TransactionItemDao;
import Helpers.ReportGenerator;
import Helpers.SessionManager;
import Helpers.UtilityFunctions;
import Models.Transaction;
import Models.TransactionItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CashierController
{


    AdminController adminController = new AdminController();

    SessionManager sessionManager = new SessionManager();
    TransactionDao transactionDao = new TransactionDao();
    TransactionItemDao transactionItemDao = new TransactionItemDao();
    private final ProductDao productDao;
    public final float VAT;

    public CashierController() throws SQLException {
        this.productDao = new ProductDao();
        this.VAT = adminController.getVatRate();
    }

    public CashierController(ProductDao productDao) throws SQLException {
        this.productDao = productDao;
        this.VAT = adminController.getVatRate();
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

    public void saveTransactions(JTable tblCart,String Total) {
        Transaction transaction = new Transaction();
        transaction.setUserID(sessionManager.getUserID());
        transaction.setTotalCost(Double.parseDouble(Total));
        transaction.setTransactionDate(new Timestamp(System.currentTimeMillis()));

        if(transactionDao.addTransaction(transaction))
        {
            System.out.println("transaction added "+transaction.getTransactionID() );
        }
        int transactionID = transaction.getTransactionID();
        DefaultTableModel model = (DefaultTableModel) tblCart.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            TransactionItem transactionItem = new TransactionItem();
            transactionItem.setTransactionID(transactionID);
            transactionItem.setProductID(Integer.parseInt(model.getValueAt(i, 0).toString()));
            transactionItem.setQuantity((int) model.getValueAt(i, 4));

            if(transactionItemDao.addTransactionItem(transactionItem))
            {
                System.out.println("transaction item "+transactionItem.getProductID() );
            }

        }
        System.out.println("Transactions saved successfully");
    }
    public int askForQuantity() {
        String input = JOptionPane.showInputDialog("Enter quantity:");
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return 0;
        }
    }


}
