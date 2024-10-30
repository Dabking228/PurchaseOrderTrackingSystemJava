package data;

/**
 *
 * @author Keith Lo Ze Hui
 */
public class FinanceManager extends Account{
    public FinanceManager(String username, String password) {
        super(username, password, Role.FINANCE_MANAGER);
    }
}
