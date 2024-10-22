package data;

import java.util.Date;

import database.BaseItem;

public class Sale extends BaseItem {
    private String itemId;
    private int quantitySold;
    private Date saleDate;
    private int salesManagerId;

    // Constructor
    public Sale(String itemId, int quantitySold, Date saleDate, int salesManagerId) {
        this.itemId = itemId;
        this.quantitySold = quantitySold;
        this.saleDate = saleDate;
        this.salesManagerId = salesManagerId;
    }

    // Getters and Setters
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public int getSalesManagerId() {
        return salesManagerId;
    }

    public void setSalesManagerId(int salesManagerId) {
        this.salesManagerId = salesManagerId;
    }

    @Override
    public String toString() {
        return itemId + "," + quantitySold + "," + saleDate + "," + salesManagerId;
    }
}
