package Models;

import java.sql.Date;

public class Product {
    private int productID;
    private String productName;
    private double costPrice;
    private double sellingPrice;
    private int quantity;
    private int categoryID;
    private Date expiryDate;

    public Product(int productID, String productName, double costPrice, double sellingPrice, int quantity, int categoryID, Date expiryDate) {
        this.productID = productID;
        this.productName = productName;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.categoryID = categoryID;
        this.expiryDate = expiryDate;
    }

    public int getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
