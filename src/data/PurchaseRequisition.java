package data;

import java.util.Date;

import database.BaseItem;

public class PurchaseRequisition extends BaseItem {
    private String itemId;
    private int quantity;
    private Date requiredByDate;
    private int salesManagerId;
    private Status status;

    // Constructor
    public PurchaseRequisition(String itemId, int quantity, Date requiredByDate, int salesManagerId, Status status) {
        this.itemId = itemId;
        this.quantity = quantity;
        this.requiredByDate = requiredByDate;
        this.salesManagerId = salesManagerId;
        this.status = status;
    }

    // Getters and Setters
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getRequiredByDate() {
        return requiredByDate;
    }

    public void setRequiredByDate(Date requiredByDate) {
        this.requiredByDate = requiredByDate;
    }

    public int getSalesManagerId() {
        return salesManagerId;
    }

    public void setSalesManagerId(int salesManagerId) {
        this.salesManagerId = salesManagerId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return itemId + "," + quantity + "," + requiredByDate + "," + salesManagerId + "," + status;
    }
}
