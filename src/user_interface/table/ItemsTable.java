package user_interface.table;

import java.util.ArrayList;

import javax.swing.*;

import data.Item;
import user_interface.MainMenu;
import backend.Backend;

public class ItemsTable extends TablePanel<Item> {
    public ItemsTable(Backend backend, MainMenu parent) {
        super("Items", 5, parent, backend.db.itemsMap, new ItemsTableModel(), backend);

        // add item button
        JButton addItemButton = new JButton("Add New");
        addItemButton.addActionListener(e -> {
            parent.showPanel("addNewItem");
        });
        titleButtonPanel.add(addItemButton, 2);
    }

    @Override
    public void itemButtonAction(int modelRow) {
        // TODO add item button but with fields filled in
    }
}

class ItemsTableModel extends TablePanelModel<Item> {
    @Override
    public void setItems(ArrayList<Item> items) {
        this.items = items;
        fireTableDataChanged();
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
        switch (column) {
            case 0:
                return account.getItemCode();
            case 1:
                return account.getItemName();
            case 2:
                return account.getSupplierId();
            case 3:
                return account.getStockLevel();
            case 4:
                return account.getReorderLevel();
            case 5:
                return "View";
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
                return "Supplier Id";
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
