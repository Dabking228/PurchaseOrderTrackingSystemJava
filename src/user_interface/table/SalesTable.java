package user_interface.table;

import javax.swing.*;

import data.Sale;
import user_interface.MainMenu;
import user_interface.add_item_dialog.AddNewItem;
import backend.Backend;

public class SalesTable extends TablePanel<Sale> {
    public SalesTable(Backend backend, MainMenu parent) {
        super("Sales", 4, parent, backend.db.salesMap, new SalesTableModel(), backend);
        this.backend = backend;

        // add item button
        JButton addItemButton = new JButton("Add New");
        addItemButton.addActionListener(e -> {
            AddNewItem addNewItem = new AddNewItem(backend);
        });
        titleButtonPanel.add(addItemButton, 2);
    }

    @Override
    public void itemButtonAction(int modelRow) {
        // TODO add item button but with fields filled in
    }
}

class SalesTableModel extends TablePanelModel<Sale> {
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
        switch (column) {
            case 0:
                return sale.getItemId();
            case 1:
                return sale.getQuantitySold();
            case 2:
                return sale.getSaleDate();
            case 3:
                return sale.getSalesManagerId();
            case 4:
                return "View";
            default:
                return null;
        }
    }

    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Item ID";
            case 1:
                return "Quantity Sold";
            case 2:
                return "Sale Date";
            case 3:
                return "Sales Manager ID";
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
