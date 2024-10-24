package data;

import database.BaseItem;

public class Item extends BaseItem {
    private String itemCode;
    private String itemName;
    private int supplierId;
    private int stockLevel;
    private int reorderLevel;

    // Constructor
    public Item(String itemCode, String itemName, int supplierId, int stockLevel, int reorderLevel) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.supplierId = supplierId;
        this.stockLevel = stockLevel;
        this.reorderLevel = reorderLevel;
    }

    // Getters and Setters
    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }
}
