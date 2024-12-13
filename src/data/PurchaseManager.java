package data;

/**
 *
 * @author Keith Lo Ze Hui
 */
public class PurchaseManager extends Account {
    public PurchaseManager(String username, String password) {
        super(username, password, Role.PURCHASE_MANAGER);
    }

    public PurchaseManager(String password, Role role, String Id) {
        super(password, role, Id);
    }

    public PurchaseManager(String username, String password, Role role) {
        super(username, password, role);
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

}
