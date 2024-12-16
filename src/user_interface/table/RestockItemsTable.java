package user_interface.table;

import java.util.ArrayList;

import javax.swing.JButton;

import backend.Backend;
import data.Item;
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
        parent.showPanel("itemPanelView");
        viewItem = parent.getPanel("itemPanelView", ItemPanel.class);
        viewItem.setBack("restockItem");
        viewItem.setRowNum(tableModel.getValueAt(modelRow, 0).toString());
        viewItem.setData();
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
