package user_interface.tables;

import java.util.ArrayList;
import java.util.HashMap;

import data.Item;
import data.Permission;
import data.Role;
import data.Supplier;
import user_interface.MainMenu;
import user_interface.panels.ItemForm;
import backend.Backend;

public class ItemTable extends TablePanel<Item> {
    protected MainMenu parent;
    protected ItemForm itemPanel;
    protected Role role;

    public ItemTable(Backend backend, MainMenu parent) {
        super("Items", 5, parent, backend.db.itemsMap, new ItemsTableModel(), backend);
        this.parent = parent;
    }

    @Override
    public void createAddPanel() {
        role = backend.getCurrentAccount().getRole();
        itemPanel = parent.getPanel("AddItem", ItemForm.class);
        itemPanel.setBack("itemTable");
        if (role.hasPermission("Items", Permission.CREATE)) {
            itemPanel.CreateItem();
        }
        parent.showPanel("AddItem");
    }

    @Override
    public void createEditPanel(int modelRow) {
        role = backend.getCurrentAccount().getRole();
        itemPanel = parent.getPanel("AddItem", ItemForm.class);
        itemPanel.setBack("itemTable");

        itemPanel.setRowNum(tableModel.getValueAt(modelRow, 6).toString());
        itemPanel.setData();

        itemPanel.viewOnly();
        if (role.hasPermission("Items", Permission.UPDATE)) {
            itemPanel.viewUpdate();
        }

        parent.showPanel("AddItem");
    }

    @Override
    public void refresh() {
        ArrayList<Item> array = new ArrayList<>(items.values());
        tableModel.setItems(array);
        ((ItemsTableModel) tableModel).setSuppliers(backend.db.suppliersMap);
    }
}

class ItemsTableModel extends TablePanelModel<Item> {
    public HashMap<String, Supplier> suppliersMap;

    void setSuppliers(HashMap<String, Supplier> suppliers) {
        this.suppliersMap = suppliers;
    }

    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    public Object getValueAt(int row, int column) {
        Item account = items.get(row);
        String supplierName = suppliersMap.get(account.getSupplierId()).getSupplierName();
        switch (column) {
            case 0:
                return account.getItemCode();
            case 1:
                return account.getItemName();
            case 2:
                return supplierName;
            case 3:
                return account.getStockLevel();
            case 4:
                return account.getReorderLevel();
            case 5:
                return "View";
            case 6:
                return account.getId();
            default:
                return null;
        }
    }

    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Item Code";
            case 1:
                return "Item Name";
            case 2:
                return "Supplier";
            case 3:
                return "Stock Level";
            case 4:
                return "Reorder Level";
            case 5:
                return "View item";
            default:
                return null;
        }
    }

    public boolean isCellEditable(int row, int column) {
        switch (column) {
            case 5:
                return true;
            default:
                return false;
        }
    }
}
