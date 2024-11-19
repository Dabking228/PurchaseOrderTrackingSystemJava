package data;

import java.util.Date;

public class Sale extends BaseItem {
    private String itemId;
    private int quantitySold;
    private Date saleDate;
    private String salesManagerId;

    // Constructor
    public Sale(String itemId, int quantitySold, Date saleDate, String salesManagerId) {
        this.itemId = itemId;
        this.quantitySold = quantitySold;
        this.saleDate = saleDate;
        this.salesManagerId = salesManagerId;
    }

    public Sale(String itemId, int quantitySold, Date saleDate, String salesManagerId, String Id) {
        this.itemId = itemId;
        this.quantitySold = quantitySold;
        this.saleDate = saleDate;
        this.salesManagerId = salesManagerId;
        this.Id = Id;
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

    public String getSalesManagerId() {
        return salesManagerId;
    }

    public void setSalesManagerId(String salesManagerId) {
        this.salesManagerId = salesManagerId;
    }
}
