package user_interface.tables;

import java.util.ArrayList;

import backend.Backend;
import data.PurchaseOrder;
import data.Role;
import data.Status;
import user_interface.MainMenu;
import user_interface.panels.POForm;

public class TrackPurchaseOrderTable extends POTable {
    private Role role;
    private POForm POPanel;

    public TrackPurchaseOrderTable(Backend backend, MainMenu parent) {
        super(backend, parent);
    }

    @Override
    public void createEditPanel(int modelRow) {
        role = backend.getCurrentAccount().getRole();
        System.out.println("helo from  track purchase order table"); // TODO: remove
        POPanel = parent.getPanel("AddPO", POForm.class);
        POPanel.setBack("trackPO");
        POPanel.setRowNum(tableModel.getValueAt(modelRow, 11).toString());
        POPanel.viewOnly();
        POPanel.setData();

        parent.showPanel("AddPO");
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
