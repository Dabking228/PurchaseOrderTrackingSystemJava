package user_interface.table;

import javax.swing.*;

import data.PurchaseRequisition;
import user_interface.MainMenu;
import user_interface.add_item_dialog.AddNewItem;
import backend.Backend;

public class PurchaseRequisitionTable extends TablePanel<PurchaseRequisition> {
    public PurchaseRequisitionTable(Backend backend, MainMenu parent) {
        super("PurchaseRequisition", 5, parent, backend.db.purchaseRequisitionsMap, new PurchaseRequisitionTableModel(),
                backend);
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

class PurchaseRequisitionTableModel extends TablePanelModel<PurchaseRequisition> {
    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    public Object getValueAt(int row, int column) {
        PurchaseRequisition purchaseRequisition = items.get(row);
        switch (column) {
            case 0:
                return purchaseRequisition.getItemId();
            case 1:
                return purchaseRequisition.getQuantity();
            case 2:
                return purchaseRequisition.getRequiredByDate();
            case 3:
                return purchaseRequisition.getSalesManagerId();
            case 4:
                return purchaseRequisition.getStatus();
            case 5:
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
                return "Quantity";
            case 2:
                return "Required By Date";
            case 3:
                return "Sales Manager ID";
            case 4:
                return "Status";
            case 5:
                return "View";
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
