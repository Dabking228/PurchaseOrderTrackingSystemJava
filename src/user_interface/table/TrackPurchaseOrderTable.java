package user_interface.table;

import java.util.ArrayList;

import backend.Backend;
import data.PurchaseOrder;
import data.Role;
import data.Status;
import user_interface.MainMenu;
import user_interface.panels.PurchaseOrderPanel;

public class TrackPurchaseOrderTable extends PurchaseOrdersTable{
    private Role role;
    private PurchaseOrderPanel POPanel;
    public TrackPurchaseOrderTable(Backend backend, MainMenu parent){
        super(backend, parent);
    }

    @Override
    public void createEditPanel(int modelRow){
        role = backend.getCurrentAccount().getRole();
        System.out.println("helo from  track purchase order table"); //TODO: remove
        POPanel = parent.getPanel("PurOrdPane", PurchaseOrderPanel.class);
        POPanel.setBack("trackPO");
        POPanel.setRowNum(tableModel.getValueAt(modelRow, 11).toString());
        POPanel.viewOnly();
        POPanel.setData();
        

        parent.showPanel("PurOrdPane");
    }

    @Override
    public void refresh() {
        ArrayList<PurchaseOrder> array = new ArrayList<>();
        for (PurchaseOrder item : items.values()) {
            if (item.getPoStatus() != Status.PENDING) {
                array.add(item);
            }
        }

        tableModel.setItems(array);
        ((PurchaseOrdersTableModel) tableModel).setMaps(backend.db.purchaseRequisitionsMap, backend.db.suppliersMap,
                backend.db.itemsMap);
        
    }
}
