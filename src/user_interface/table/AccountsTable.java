package user_interface.table;

import javax.swing.*;

import data.Account;
import user_interface.MainMenu;
import user_interface.add_item_dialog.AddNewItem;
import user_interface.panels.AddNewUser;
import backend.Backend;

public class AccountsTable extends TablePanel<Account> {
    public AccountsTable(Backend backend, MainMenu parent) {
        super("Accounts", 2, parent, backend.db.accountsMap, new AccountsTableModel(), backend);
        this.backend = backend;
    }

    @Override
    public void createAddPanel() {
        // TODO add item panel
    }

    @Override
    public void createEditPanel(int modelRow) {
        // TODO add item panel but with fields filled in
    }
}

class AccountsTableModel extends TablePanelModel<Account> {
    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    // passwords are not shown in the table
    public Object getValueAt(int row, int column) {
        Account account = items.get(row);
        switch (column) {
            case 0:
                return account.getUsername();
            case 1:
                return account.getRole();
            case 2:
                return "View";
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
                return "View";
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
}
