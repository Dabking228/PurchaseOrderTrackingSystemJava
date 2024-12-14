package backend;

import java.math.BigDecimal;

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

        Supplier supplier = new Supplier("SUP123", "Supplier 1", "contact@supplier1.com");
        db.addSupplier(supplier);

        Item item = new Item("CODE123", "Item 1", supplier.getId(), 100, 20, new BigDecimal("20.00"));
        db.addItem(item);

        Sale sale = new Sale(item.getId(), 10, new java.util.Date(), account.getId());
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
            String category, BigDecimal price) {
        Item newItem = new Item(code, name, supplierId, stockLevel, reorderLevel, price);
        db.addItem(newItem);
        System.out.println("Added custom item: " + name + " under category: " + category);
    }

    public boolean addItem(Item newItem) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addItem'");
    }
}
