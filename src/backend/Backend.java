package backend;

import data.*;
import database.*;

public class Backend {
    public Backend() {
        Database db = new Database();

        // db.load();

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
}
