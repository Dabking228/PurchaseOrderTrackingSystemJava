package data;

/**
 *
 * @author Keith Lo Ze Hui
 */
public class FinanceManager extends Account {
    public FinanceManager(String username, String password) {
        super(username, password, Role.FINANCE_MANAGER);
    }

    public FinanceManager(String password, Role role, String Id) {
        super(password, role, Id);
    }

    public FinanceManager(String username, String password, Role role) {
        super(username, password, role);
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

}
