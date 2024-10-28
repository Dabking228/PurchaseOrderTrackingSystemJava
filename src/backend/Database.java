package backend;

import java.util.HashMap;

import data.*;

public class Database {
    private HashMap<String, Account> accountsMap = new HashMap<>();
    private HashMap<String, Item> itemsMap = new HashMap<>();
    private HashMap<String, Supplier> suppliersMap = new HashMap<>();
    private HashMap<String, Sale> salesMap = new HashMap<>();
    private HashMap<String, PurchaseRequisition> purchaseRequisitionsMap = new HashMap<>();
    private HashMap<String, PurchaseOrder> purchaseOrdersMap = new HashMap<>();

    public void save() {
        CSV.save(accountsMap, "accounts.csv");
        CSV.save(itemsMap, "items.csv");
        CSV.save(suppliersMap, "suppliers.csv");
        CSV.save(salesMap, "sales.csv");
        CSV.save(purchaseRequisitionsMap, "purchase_requisitions.csv");
        CSV.save(purchaseOrdersMap, "purchase_orders.csv");
    }

    public void load() {
        CSV.load(accountsMap, "accounts.csv", Account.class);
        CSV.load(itemsMap, "items.csv", Item.class);
        CSV.load(suppliersMap, "suppliers.csv", Supplier.class);
        CSV.load(salesMap, "sales.csv", Sale.class);
        CSV.load(purchaseRequisitionsMap, "purchase_requisitions.csv", PurchaseRequisition.class);
        CSV.load(purchaseOrdersMap, "purchase_orders.csv", PurchaseOrder.class);
    }

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
