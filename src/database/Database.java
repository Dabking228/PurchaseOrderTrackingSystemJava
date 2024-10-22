package database;

import java.util.HashMap;

import data.Account;
import data.Item;
import data.PurchaseOrder;
import data.PurchaseRequisition;
import data.Sale;
import data.Supplier;

public class Database {
    private HashMap<String, Account> accountsMap = new HashMap<>();
    private HashMap<String, Item> itemsMap = new HashMap<>();
    private HashMap<String, Supplier> suppliersMap = new HashMap<>();
    private HashMap<String, Sale> salesMap = new HashMap<>();
    private HashMap<String, PurchaseRequisition> purchaseRequisitionsMap = new HashMap<>();
    private HashMap<String, PurchaseOrder> purchaseOrdersMap = new HashMap<>();

    // Account Methods
    public void addAccount(Account account) {
        accountsMap.put(account.getId(), account);
    }

    public Account getAccount(String userId) {
        return accountsMap.get(userId);
    }

    // Item Methods
    public void addItem(Item item) {
        itemsMap.put(item.getId(), item);
    }

    public Item getItem(String itemId) {
        return itemsMap.get(itemId);
    }

    // Supplier Methods
    public void addSupplier(Supplier supplier) {
        suppliersMap.put(supplier.getId(), supplier);
    }

    public Supplier getSupplier(String supplierId) {
        return suppliersMap.get(supplierId);
    }

    // Sale Methods
    public void addSale(Sale sale) {
        salesMap.put(sale.getId(), sale);
    }

    public Sale getSale(String saleId) {
        return salesMap.get(saleId);
    }

    // Purchase Requisition Methods
    public void addPurchaseRequisition(PurchaseRequisition pr) {
        purchaseRequisitionsMap.put(pr.getId(), pr);
    }

    public PurchaseRequisition getPurchaseRequisition(String prId) {
        return purchaseRequisitionsMap.get(prId);
    }

    // Purchase Order Methods
    public void addPurchaseOrder(PurchaseOrder po) {
        purchaseOrdersMap.put(po.getId(), po);
    }

    public PurchaseOrder getPurchaseOrder(String poId) {
        return purchaseOrdersMap.get(poId);
    }
}
