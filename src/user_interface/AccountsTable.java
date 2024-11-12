package user_interface;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import data.Account;
import backend.Backend;

class AccountsTable extends TablePanel {
    private Map<String, Account> accounts;
    private AccountsTableModel accountsTableModel;
    private Backend backend;

    public AccountsTable() {
        super("Accounts", 2);

        // Back button?
        // Add user button
    }

    @Override
    public void setModel(JTable itemTable) {
        accountsTableModel = new AccountsTableModel();
        itemTable.setModel(accountsTableModel);
    }

    @Override
    public void refreshItemsTable() {
        accounts = backend.db.accountsMap;
        ArrayList<Account> accountsArray = new ArrayList<>(accounts.values());
        accountsTableModel.setAccounts(accountsArray);
    }

    @Override
    public void goToItem(int modelRow) {
        Account accountToDelete = accountsTableModel.accounts.get(modelRow);
        backend.db.accountsMap.remove(accountToDelete.getId());
        accounts = backend.db.accountsMap;
        refreshItemsTable();
    }
}

class AccountsTableModel extends AbstractTableModel {
    protected List<Account> accounts = new ArrayList<>();

    @Override
    public int getRowCount() {
        return accounts.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    // passwords are not shown in the table
    public Object getValueAt(int row, int column) {
        Account account = accounts.get(row);
        switch (column) {
            case 0:
                return account.getUsername();
            case 1:
                return account.getRole();
            case 2:
                return "Delete";
            default:
                return null;
        }
    }

    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "User Name";
            case 1:
                return "Role";
            case 2:
                return "Delete user";
            default:
                return null;
        }
    }

    public boolean isCellEditable(int row, int column) {
        switch (column) {
            case 2:
                return true;
            default:
                return false;
        }
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
        this.fireTableDataChanged();
    }
}
