package data;

import java.math.BigDecimal;

public class Item extends BaseItem {
    private String itemCode;
    private String itemName;
    private String supplierId;
    private int stockLevel;
    private int reorderLevel;
    private BigDecimal price;

    // Constructor
    public Item(String itemCode, String itemName, String supplierId, int stockLevel, int reorderLevel,
            BigDecimal price) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.supplierId = supplierId;
        this.stockLevel = stockLevel;
        this.reorderLevel = reorderLevel;
        this.price = price;
    }

    public Item(String itemCode, String itemName, String supplierId, int stockLevel, int reorderLevel, BigDecimal price,
            String Id) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.supplierId = supplierId;
        this.stockLevel = stockLevel;
        this.reorderLevel = reorderLevel;
        this.price = price;
        this.Id = Id;
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

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public void printLog() {
        System.out.println("Item loaded.");
    }
}
