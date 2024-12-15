package backend;

import data.*;

public class Backend {
    public Database db;
    private Account currentAccount;

    public Backend() {
        db = new Database();
        db.load();

        // test code for now
        Account account = new Account("admin", "admin", Role.ADMIN);
        db.addAccount(account);
        Account account2 = new Account("sale", "sale", Role.SALES_MANAGER);
        db.addAccount(account2);
        Account account3 = new Account("pm", "pm", Role.PURCHASE_MANAGER);
        db.addAccount(account3);
        Account account4 = new Account("im", "im", Role.INVENTORY_MANAGER);
        db.addAccount(account4);
        Account account5 = new Account("fm", "fm", Role.FINANCE_MANAGER);
        db.addAccount(account5);

        Supplier supplier = new Supplier("SUP123", "Supplier 1", "contact@supplier1.com");
        db.addSupplier(supplier);
        Supplier supplier2 = new Supplier("suppp2321", "Supplier 3123121", "contact@supplier1.com");
        db.addSupplier(supplier2);

        Item item = new Item("CODE123", "Item 1", supplier.getId(), 100, 20);
        db.addItem(item);
        Item item2 = new Item("CODE1323", "Item132", supplier.getId(), 40, 120);
        db.addItem(item2);


        Sale sale = new Sale(item.getId(), 10, new java.util.Date(), account.getId());
        db.addSale(sale);

        PurchaseRequisition pr = new PurchaseRequisition(item.getId(), 50, new java.util.Date(), 1, Status.PENDING);
        db.addPurchaseRequisition(pr);
        PurchaseRequisition pr2 = new PurchaseRequisition(item2.getId(), 50, new java.util.Date(), 1, Status.PENDING);
        db.addPurchaseRequisition(pr2);
        
        PurchaseOrder po = new PurchaseOrder(
                pr.getId(),
                supplier.getId(),
                account.getId(),
                Status.PENDING,
                new java.util.Date(),
                500.00);
        db.addPurchaseOrder(po);
        PurchaseOrder po2 = new PurchaseOrder(
                pr2.getId(),
                supplier.getId(),
                account.getId(),
                Status.PENDING,
                new java.util.Date(),
                500.00);
        db.addPurchaseOrder(po2);

        db.save();
    }

    public void save() {
        db.save();
    }

    public boolean login(String username, String password) {
        Account account = db.getAccount(username);
        if (account == null) {
            return false;
        }

        String accountPassword = account.getPassword();
        if (accountPassword.equals(password)) {
            currentAccount = account;
            return true;
        }

        return false;
    }

    public void logout() {
        currentAccount = null;
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public Role getRole() {
        return currentAccount.getRole();
    }

    public void addCustomItem(String code, String name, String supplierId, int stockLevel, int reorderLevel,
            String category) {
        Item newItem = new Item(code, name, supplierId, stockLevel, reorderLevel);
        db.addItem(newItem);
        System.out.println("Added custom item: " + name + " under category: " + category);
    }

    public boolean addItem(Item newItem) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addItem'");
    }
}
