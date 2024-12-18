package user_interface.tables;

import java.util.ArrayList;
import java.util.HashMap;

import data.Item;
import data.Permission;
import data.Role;
import data.Sale;
import user_interface.MainMenu;

import user_interface.panels.SaleForm;
import backend.Backend;

public class SaleTable extends TablePanel<Sale> {
    private MainMenu parent;
    private SaleForm createSale;
    private Role role;

    public SaleTable(Backend backend, MainMenu parent) {
        super("Sales", 4, parent, backend.db.salesMap, new SalesTableModel(), backend);
        this.backend = backend;
        this.parent = parent;
    }

    @Override
    public void createAddPanel() {
        createSale = parent.getPanel("AddSale", SaleForm.class);
        createSale.setBack("saleTable");
        parent.showPanel("AddSale");
    }

    @Override
    public void createEditPanel(int modelRow) {
        role = backend.getCurrentAccount().getRole();
        createSale = parent.getPanel("AddSale", SaleForm.class);
        createSale.setBack("saleTable");

        createSale.setRowNum(tableModel.getValueAt(modelRow, 5).toString());
        createSale.setData();

        createSale.viewOnly();
        if (role.hasPermission("Sales", Permission.UPDATE)) {
            createSale.viewUpdate();
        }

        parent.showPanel("AddSale");
    }

    @Override
    public void refresh() {
        ArrayList<Sale> array = new ArrayList<>(items.values());
        tableModel.setItems(array);
        ((SalesTableModel) tableModel).setStoreItems(backend.db.itemsMap);
    }
}

class SalesTableModel extends TablePanelModel<Sale> {
    public HashMap<String, Item> itemsMap;

    void setStoreItems(HashMap<String, Item> items) {
        this.itemsMap = items;
    }

    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    public Object getValueAt(int row, int column) {
        Sale sale = items.get(row);
        String itemName = itemsMap.get(sale.getItemId()).getItemName();
        switch (column) {
            case 0:
                return itemName;
            case 1:
                return sale.getQuantitySold();
            case 2:
                return simpleDateFormat.format(sale.getSaleDate());
            case 3:
                return sale.getSalesManagerId();
            case 4:
                return "View";
            case 5:
                return sale.getId();
            default:
                return null;
        }
    }

    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Item";
            case 1:
                return "Quantity Sold";
            case 2:
                return "Sale Date";
            case 3:
                return "Sales Manager";
            case 4:
                return "View";
            default:
                return null;
        }
    }

    public boolean isCellEditable(int row, int column) {
        switch (column) {
            case 4:
                return true;
            default:
                return false;
        }
    }
}
