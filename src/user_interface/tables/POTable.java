package user_interface.tables;

import java.util.ArrayList;
import java.util.HashMap;

import data.Item;
import data.Permission;
import data.PurchaseOrder;
import data.PurchaseRequisition;
import data.Role;
import data.Supplier;
import user_interface.MainMenu;

import user_interface.panels.POForm;
import backend.Backend;

public class POTable extends TablePanel<PurchaseOrder> {
    protected MainMenu parent;
    private POForm POPanel;
    private Role role;

    public POTable(Backend backend, MainMenu parent) {
        super("PurchaseOrder", 10, parent, backend.db.purchaseOrdersMap, new PurchaseOrdersTableModel(), backend);
        this.backend = backend;
        this.parent = parent;
    }

    @Override
    public void createAddPanel() {
        role = backend.getCurrentAccount().getRole();
        POPanel = parent.getPanel("AddPO", POForm.class);
        POPanel.setBack("POTable");
        if (role.hasPermission("PurchaseOrder", Permission.CREATE)) {
            POPanel.createPO();
        }

        parent.showPanel("AddPO");
    }

    @Override
    public void createEditPanel(int modelRow) {
        role = backend.getCurrentAccount().getRole();
        POPanel = parent.getPanel("AddPO", POForm.class);

        POPanel.setBack("POTable");
        POPanel.setRowNum(tableModel.getValueAt(modelRow, 11).toString());

        POPanel.viewOnly();

        if (role.hasPermission("PurchaseOrder", Permission.CREATE)) {
            POPanel.viewOnlyUpdate();
        } else if (role == Role.FINANCE_MANAGER) {
            POPanel.FMView(true);
        }
        POPanel.setData();
        parent.showPanel("AddPO");
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
        return 11;
    }

    public Object getValueAt(int row, int column) {
        PurchaseOrder purchaseOrder = items.get(row);
        PurchaseRequisition purchaseRequisition = purchaseRequisitionsMap.get(purchaseOrder.getPurchaseRequisitionId());
        String purchaseItemName = itemsMap.get(purchaseRequisition.getItemId()).getItemName();
        String supplierName = suppliersMap.get(purchaseOrder.getSupplierId()).getSupplierName();
        switch (column) {
            case 0:
                return purchaseItemName;
            case 1:
                return purchaseRequisition.getQuantity();
            case 2:
                return purchaseRequisition.getStatus();
            case 3:
                return purchaseRequisition.getSalesManagerId();
            case 4:
                return purchaseRequisition.getRequiredByDate();
            case 5:
                return supplierName;
            case 6:
                return purchaseOrder.getPurchaseManagerId();
            case 7:
                return purchaseOrder.getPoStatus();
            case 8:
                return purchaseOrder.getCreatedDate();
            case 9:
                return purchaseOrder.getTotalAmount();
            case 10:
                return "View";
            case 11:
                return purchaseOrder.getId();
            default:
                return null;
        }
    }

    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Item";
            case 1:
                return "Quantity";
            case 2:
                return "PR Status";
            case 3:
                return "Sales Manager";
            case 4:
                return "Required By";
            case 5:
                return "Supplier";
            case 6:
                return "PO Manager";
            case 7:
                return "PO Status";
            case 8:
                return "PO Created";
            case 9:
                return "Total Amount";
            case 10:
                return "View";
            default:
                return null;
        }
    }

    public boolean isCellEditable(int row, int column) {
        switch (column) {
            case 10:
                return true;
            default:
                return false;
        }
    }
}
