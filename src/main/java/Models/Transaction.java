package Models;

import java.sql.Timestamp;
import java.util.Date;

public class Transaction {
    private int transactionID;
    private int userID;
    private double totalCost;
    private Timestamp transactionDate;


    public Transaction(int transactionID, int userID, double totalCost, Timestamp transactionDate, int productID, int quantity) {
        this.transactionID = transactionID;
        this.userID = userID;
        this.totalCost = totalCost;
        this.transactionDate = transactionDate;

    }

    public Transaction() {

    }

    public int getTransactionID() {
        return transactionID;
    }

    public int getUserID() {
        return userID;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }


    public void setUserID(int userID) {
        this.userID=userID;
    }

    public void setTotalCost(double v) {
        this.totalCost = v;
    }

    public void setTransactionDate(Timestamp timestamp) {
       this.transactionDate = timestamp;
    }

    public void setTransactionID(int generatedID) {
        this.transactionID = generatedID;
    }
}
