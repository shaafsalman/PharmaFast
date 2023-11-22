package Models;



import java.util.Date;

public class Transaction {
    private int transactionID;
    private double totalCost;
    private Date transactionDate;
    private int productID;
    private int quantity;

    public Transaction(int transactionID, double totalCost, Date transactionDate, int productID, int quantity) {
        this.transactionID = transactionID;

        this.totalCost = totalCost;
        this.transactionDate = transactionDate;
        this.productID = productID;
        this.quantity = quantity;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public int getProductID() {
        return productID;
    }

    public int getQuantity() {
        return quantity;
    }

    // Calculate the cost based on quantity and product cost
    public double calculateCost(double productCost) {
        return quantity * productCost;
    }

    // Calculate the profit based on quantity, product cost, and selling price
    public double calculateProfit(double productCost, double sellingPrice) {
        double cost = calculateCost(productCost);
        return quantity * (sellingPrice - cost);
    }
}
