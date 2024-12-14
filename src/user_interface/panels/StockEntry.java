import javax.swing.JOptionPane;

import data.Item;
import user_interface.components.*;

public class StockEntry extends data<Item> {

    private fieldText itemCodeField;
    private fieldText itemNameField;
    private fieldText supplierIdField;
    private fieldText stockLevelField;
    private fieldText reorderLevelField;
    private fieldDrop comboBox;
    private List<Item> itemList;

    public Item promptForItemDetails() {

        String itemCode = JOptionPane.showInputDialog("Enter Item Code:");
        String itemName = JOptionPane.showInputDialog("Enter Item Name:");
        String supplierId = JOptionPane.showInputDialog("Enter Supplier ID:");
        String stockLevelStr = JOptionPane.showInputDialog("Enter Stock Level:");
        String reorderLevelStr = JOptionPane.showInputDialog("Enter Reorder Level:");

        int stockLevel = Integer.parseInt(stockLevelStr);
        int reorderLevel = Integer.parseInt(reorderLevelStr);

        return new Item(itemCode, itemName, supplierId, stockLevel, reorderLevel);
    }

}
