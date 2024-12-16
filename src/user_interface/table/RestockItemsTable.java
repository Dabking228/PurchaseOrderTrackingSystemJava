package user_interface.table;

import java.util.ArrayList;

import backend.Backend;
import data.Item;
import user_interface.MainMenu;

public class RestockItemsTable extends ItemsTable {
    public RestockItemsTable(Backend backend, MainMenu parent) {
        super(backend, parent);
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
