package user_interface.tables;

import data.Permission;
import data.Role;
import data.Supplier;
import user_interface.MainMenu;
import user_interface.panels.SupplierForm;
import backend.Backend;

public class SupplierTable extends TablePanel<Supplier> {
    private SupplierForm createSupplier;
    private MainMenu parent;

    public SupplierTable(Backend backend, MainMenu parent) {
        super("Suppliers", 3, parent, backend.db.suppliersMap, new SuppliersTableModel(), backend);
        this.backend = backend;
        this.parent = parent;
    }

    @Override
    public void createAddPanel() {
        createSupplier = parent.getPanel("AddSupplier", SupplierForm.class);
        createSupplier.setBack("supplierTable");
        parent.showPanel("AddSupplier");
    }

    @Override
    public void createEditPanel(int modelRow) {
        Role role = backend.getCurrentAccount().getRole();
        createSupplier = parent.getPanel("AddSupplier", SupplierForm.class);
        createSupplier.setBack("supplierTable");

        createSupplier.setRowNum(tableModel.getValueAt(modelRow, 0).toString());
        createSupplier.setData();

        createSupplier.viewOnly();
        if (role.hasPermission("Suppliers", Permission.UPDATE)) {
            createSupplier.viewUpdate();
        }

        parent.showPanel("AddSupplier");
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
