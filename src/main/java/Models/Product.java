package Models;

import java.sql.Date;
import java.util.Calendar;

public class Product {
    private final int productID;
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

    public void setName(String name) {this.productName = name;}

    public void setCostPrice(double costPrice) {this.costPrice = costPrice;}

    public void setSellingPrice(double modifiedSellingPrice) {this.sellingPrice =modifiedSellingPrice;}

    public void setQuantity(int modifiedQuantity) {this.quantity = modifiedQuantity;}

    public String getName() {return productName;}

    public int getExpiryDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(expiryDate);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getExpiryMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(expiryDate);
        return calendar.get(Calendar.MONTH) + 1;
    }

    public int getExpiryYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(expiryDate);
        return calendar.get(Calendar.YEAR);
    }

    public void setExpiryDay(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(expiryDate);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        expiryDate = (Date) calendar.getTime();
    }

    public void setExpiryMonth(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(expiryDate);
        calendar.set(Calendar.MONTH, month - 1);
        expiryDate = (Date) calendar.getTime();
    }

    public void setExpiryYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(expiryDate);
        calendar.set(Calendar.YEAR, year);
        expiryDate = (Date) calendar.getTime();
    }

}
