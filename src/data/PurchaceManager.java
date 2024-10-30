package data;

/**
 *
 * @author Keith Lo Ze Hui
 */
public class PurchaceManager extends Account {
    public PurchaceManager(String username, String password) {
        super(username, password, Role.PURCHASE_MANAGER);
    }
}
