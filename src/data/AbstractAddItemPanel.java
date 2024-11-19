package backend;

import data.*;

public class Backend {
    public Database db;
    private Account currentAccount;

    public Backend() {
        db = new Database();
        db.load();

        Account account = new Account("admin", "admin", Role.ADMIN);
        db.addAccount(account);

        Item item = new Item("CODE123", "Item 1", 1, 100, 20);
        db.addItem(item);

        Supplier supplier = new Supplier("SUP123", "Supplier 1", "contact@supplier1.com");
        db.addSupplier(supplier);

        Sale sale = new Sale(item.getId(), 10, new java.util.Date(), 1);
        db.addSale(sale);

        PurchaseRequisition pr = new PurchaseRequisition(item.getId(), 50, new java.util.Date(), 1, Status.PENDING);
        db.addPurchaseRequisition(pr);

        PurchaseOrder po = new PurchaseOrder(
                pr.getId(),
                supplier.getId(),
                account.getId(),
                "pending",
                new java.util.Date(),
                500.00);
        db.addPurchaseOrder(po);

        db.save();
    }

    public void save() {
        db.save();
    }

    public boolean login(String username, String password) {
        Account account = db.getAccount(username);
        String accountPassword = account.getPassword();

        if (accountPassword.equals(password)) {
            System.out.println("Login successful");
            currentAccount = account;
            return true;
        }

        System.out.println("Login failed");
        return false;
    }

    public void logout() {
        currentAccount = null;
    }

    public void addCustomItem(String code, String name, String supplierId, int stockLevel, int reorderLevel, String category) {
        Item newItem = new Item(code, name, stockLevel, reorderLevel, 0); 
        db.addItem(newItem);
        System.out.println("Added custom item: " + name + " under category: " + category);
    }
}