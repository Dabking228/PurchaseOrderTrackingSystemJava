package user_interface.tables;

import java.util.ArrayList;

import backend.Backend;
import data.Item;
import user_interface.MainMenu;
import user_interface.panels.ItemForm;

public class ItemRestockTable extends ItemTable {
    public ItemRestockTable(Backend backend, MainMenu parent) {
        super(backend, parent);
    }

    @Override
    public void createEditPanel(int modelRow) {
        role = backend.getCurrentAccount().getRole();
        itemPanel = parent.getPanel("AddItem", ItemForm.class);
        itemPanel.setBack("restockItem");

        itemPanel.setRowNum(tableModel.getValueAt(modelRow, 6).toString());
        itemPanel.setData();

        itemPanel.viewOnly();

        parent.showPanel("AddItem");
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
