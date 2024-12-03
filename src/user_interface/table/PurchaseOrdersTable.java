package user_interface.table;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

import data.Item;
import data.PurchaseOrder;
import data.PurchaseRequisition;
import data.Supplier;
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

    @Override
    public void refresh() {
        ArrayList<PurchaseOrder> array = new ArrayList<>(items.values());
        tableModel.setItems(array);
        ((PurchaseOrdersTableModel) tableModel).setMaps(backend.db.purchaseRequisitionsMap, backend.db.suppliersMap,
                backend.db.itemsMap);
    }
}

class PurchaseOrdersTableModel extends TablePanelModel<PurchaseOrder> {
    public HashMap<String, Item> itemsMap;
    public HashMap<String, PurchaseRequisition> purchaseRequisitionsMap;
    public HashMap<String, Supplier> suppliersMap;

    void setMaps(
            HashMap<String, PurchaseRequisition> purchaseRequisitions,
            HashMap<String, Supplier> suppliers,
            HashMap<String, Item> items) {
        this.purchaseRequisitionsMap = purchaseRequisitions;
        this.suppliersMap = suppliers;
        this.itemsMap = items;
    }

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
        // TODO more columns to give purchase requsition info
        PurchaseRequisition purchaseRequisition = purchaseRequisitionsMap.get(purchaseOrder.getPurchaseRequisitionId());
        String purchaseItemName = itemsMap.get(purchaseRequisition.getItemId()).getItemName();
        String supplierName = suppliersMap.get(purchaseOrder.getSupplierId()).getSupplierName();
        switch (column) {
            case 0:
                return purchaseItemName;
            case 1:
                return supplierName;
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
                return "Purchase Requisition";
            case 1:
                return "Supplier";
            case 2:
                return "Purchase Manager";
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
