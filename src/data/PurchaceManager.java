package data;

/**
 *
 * @author Keith Lo Ze Hui
 */
public class PurchaceManager extends Account {
    public PurchaceManager(String username, String password) {
        super(username, password, Role.PURCHASE_MANAGER);
    }

    public PurchaceManager(String password, Role role, String Id) {
        super(password, role, Id);
    }

    public PurchaceManager(String username, String password, Role role) {
        super(username, password, role);
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

}
