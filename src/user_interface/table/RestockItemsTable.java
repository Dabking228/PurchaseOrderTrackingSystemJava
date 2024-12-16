package user_interface.table;

import java.util.ArrayList;

import javax.swing.JButton;

import backend.Backend;
import data.Item;
import data.Permission;
import user_interface.MainMenu;
import user_interface.panels.ItemPanel;

public class RestockItemsTable extends ItemsTable {
    public RestockItemsTable(Backend backend, MainMenu parent) {
        super(backend, parent);
        
    }

    @Override
    public void createAddPanel(){

    }

    @Override
    public void createEditPanel(int modelRow) {
        role = backend.getCurrentAccount().getRole();
        itemPanel = parent.getPanel("itemPanel", ItemPanel.class);
        itemPanel.setBack("restockItem");

        itemPanel.setRowNum(tableModel.getValueAt(modelRow, 6).toString());
        itemPanel.setData();

        itemPanel.viewOnly();
       

        parent.showPanel("itemPanel");
    }

    @Override
    public void refresh() {
        ArrayList<Item> array = new ArrayList<>();
        for (Item item : items.values()) {
            if (item.getStockLevel() < item.getReorderLevel()) {
                array.add(item);
            }
        }
        tableModel.setItems(array);
        ((ItemsTableModel) tableModel).setSuppliers(backend.db.suppliersMap);
    }
}
