package Models;

public class TransactionItem {
    private int transactionItemID;
    private int transactionID;
    private int productID;
    private int quantity;

    public void setProductID(int ID) {
        this.productID = ID;
    }

    public void setQuantity(int val) {
        this.quantity = val;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getProductID() {
        return productID;
    }

    public int getQuantity() {
        return quantity;
    }
}
