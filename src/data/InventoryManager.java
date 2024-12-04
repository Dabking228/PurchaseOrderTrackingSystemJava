package data;

/**
 *
 * @author Keith Lo Ze Hui
 */
public class InventoryManager extends Account {
    public InventoryManager(String username, String password) {
        super(username, password, Role.INVENTORY_MANAGER);
    }

    public InventoryManager(String password, Role role, String Id) {
        super(password, role, Id);
    }

    public InventoryManager(String username, String password, Role role) {
        super(username, password, role);
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

}
