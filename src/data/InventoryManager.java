package data;

/**
 *
 * @author Keith Lo Ze Hui
 */
public class InventoryManager extends Account {
    public InventoryManager(String username, String password) {
        super(username, password, Role.INVENTORY_MANAGER);
    }
}
