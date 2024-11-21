package user_interface.table;

import javax.swing.*;

import data.PurchaseOrder;
import user_interface.MainMenu;
import user_interface.add_item_dialog.AddNewItem;
import backend.Backend;

public class PurchaseOrdersTable extends TablePanel<PurchaseOrder> {
    public PurchaseOrdersTable(Backend backend, MainMenu parent) {
        super("PurchaseOrders", 6, parent, backend.db.purchaseOrdersMap, new PurchaseOrdersTableModel(), backend);
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

class PurchaseOrdersTableModel extends TablePanelModel<PurchaseOrder> {
    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    public Object getValueAt(int row, int column) {
        PurchaseOrder purchaseOrder = items.get(row);
        switch (column) {
            case 0:
                return purchaseOrder.getPurchaseRequisitionId();
            case 1:
                return purchaseOrder.getSupplierId();
            case 2:
                return purchaseOrder.getPurchaseManagerId();
            case 3:
                return purchaseOrder.getPoStatus();
            case 4:
                return purchaseOrder.getCreatedDate();
            case 5:
                return purchaseOrder.getTotalAmount();
            case 6:
                return "View";
            default:
                return null;
        }
    }

    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Purchase Requisition ID";
            case 1:
                return "Supplier ID";
            case 2:
                return "Purchase Manager ID";
            case 3:
                return "PO Status";
            case 4:
                return "Created Date";
            case 5:
                return "Total Amount";
            case 6:
                return "View";
            default:
                return null;
        }
    }

    public boolean isCellEditable(int row, int column) {
        switch (column) {
            case 6:
                return true;
            default:
                return false;
        }
    }
}
