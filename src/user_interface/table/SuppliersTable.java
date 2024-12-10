package user_interface.table;

import javax.swing.*;

import data.Supplier;
import user_interface.MainMenu;
import user_interface.add_item_dialog.AddNewItem;
import backend.Backend;

public class SuppliersTable extends TablePanel<Supplier> {
    public SuppliersTable(Backend backend, MainMenu parent) {
        super("Suppliers", 3, parent, backend.db.suppliersMap, new SuppliersTableModel(), backend);
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

class SuppliersTableModel extends TablePanelModel<Supplier> {
    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    public Object getValueAt(int row, int column) {
        Supplier supplier = items.get(row);
        switch (column) {
            case 0:
                return supplier.getSupplierCode();
            case 1:
                return supplier.getSupplierName();
            case 2:
                return supplier.getContactInfo();
            case 3:
                return "View";
            default:
                return null;
        }
    }

    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Code";
            case 1:
                return "Name";
            case 2:
                return "Contact";
            case 3:
                return "View";
            default:
                return null;
        }
    }
}
