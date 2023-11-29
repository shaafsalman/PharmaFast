package Controllers;
import Dao.ProductDao;
import Dao.TransactionDao;
import Dao.TransactionItemDao;
import Helpers.ReportGenerator;
import Helpers.SessionManager;
import Helpers.UtilityFunctions;
import Models.Product;
import Models.Transaction;
import Models.TransactionItem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

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

    public ArrayList<Product>getAllProducts()
    {
        return productDao.getAllProducts();
    }
    public Product findProductByName(String productName) {
        for (Product product : getAllProducts()) {
            if (product.getProductName().equalsIgnoreCase(productName)) {
                return product;
            }
        }
        return null;
    }
    public void initializeProducts(JComboBox productComboBox) {
        // Clear existing items
        productComboBox.removeAllItems();

        for (Product product : getAllProducts()) {
            productComboBox.addItem(product.getProductName());
        }
    }

    public void optimizTable(JTable table) {
        // Set row height and other table properties
        table.setRowHeight(40);
        table.setOpaque(false);
        table.setShowGrid(false);
        table.setBackground(new Color(255, 255, 255));

        table.setSelectionBackground(new Color(220, 220, 220));

        class HeaderRenderer extends DefaultTableCellRenderer {
            public HeaderRenderer() {
                setHorizontalAlignment(SwingConstants.CENTER);
            }
        }
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Century Gothic", Font.PLAIN, 18));
        header.setBackground(new Color(47, 47, 47));
        header.setForeground(Color.darkGray);
        header.setBorder(new EmptyBorder(20, 10, 20, 10));
        TableCellRenderer headerRenderer = new HeaderRenderer();
        table.getTableHeader().setDefaultRenderer(headerRenderer);

        // Set cell properties
        table.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

//        DefaultTableModel model = new DefaultTableModel() {
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return false;
//            }
//        };
//
//        table.setModel(model);
    }

}
