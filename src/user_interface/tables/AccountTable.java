package user_interface.tables;

import data.Account;
import data.Permission;
import data.Role;
import user_interface.MainMenu;

import user_interface.panels.AccountForm;
import backend.Backend;

public class AccountTable extends TablePanel<Account> {
    protected MainMenu parent;
    protected AccountForm UserPanel;
    protected Role role;

    public AccountTable(Backend backend, MainMenu parent) {
        super("Accounts", 2, parent, backend.db.accountsMap, new AccountsTableModel(), backend);
        this.parent = parent;
        this.backend = backend;
    }

    @Override
    public void createAddPanel() {
        role = backend.getCurrentAccount().getRole();
        UserPanel = parent.getPanel("AddAccount", AccountForm.class);
        if (role.hasPermission("Accounts", Permission.CREATE)) {
            UserPanel.CreateUser();
        }
        parent.showPanel("AddAccount");
    }

    @Override
    public void createEditPanel(int modelRow) {
        role = backend.getCurrentAccount().getRole();
        UserPanel = parent.getPanel("AddAccount", AccountForm.class);
        UserPanel.setRowNum(tableModel.getValueAt(modelRow, 0).toString());
        UserPanel.setData();

        UserPanel.viewOnly();
        if (role.hasPermission("Accounts", Permission.UPDATE)) {
            UserPanel.viewUpdate();
        }
        parent.showPanel("AddAccount");

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
            case 3:
                return account.getId();
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
