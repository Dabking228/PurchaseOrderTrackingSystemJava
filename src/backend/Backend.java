package backend;

import data.*;
import java.math.BigDecimal;

public class Backend {
    public Database db;
    private Account currentAccount;

    public Backend() {
        db = new Database();
        db.load();
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
}
