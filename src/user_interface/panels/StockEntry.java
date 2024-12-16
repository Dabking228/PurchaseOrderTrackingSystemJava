package user_interface.panels;
import backend.Backend;
import data.Item;
import javax.swing.JButton;
import user_interface.*;

public class StockEntry extends Panel<Item> {
    protected FieldText itemID, itemName, StockAmt, ReStckAmt, itemIDString;


    public StockEntry(Backend backend, MainMenu parent) {
        super("Stock Entry Form", parent, backend.db.itemsMap, backend);

        itemID = new FieldText("Item ID: ");
        contentPanel.add(itemID);

        itemName = new FieldText("Item Name: ");
        contentPanel.add(itemName);

        StockAmt = new FieldText("Stock: ", true);
        contentPanel.add(StockAmt);

        ReStckAmt = new FieldText("Minimum Amount of Stock: ", true);
        contentPanel.add(ReStckAmt);
        
        backButton.addActionListener(e -> {
            parent.showMainMenu();
        });

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> {
            try {
                
                String itemIDString = itemID.getData();
                String itemNameString = itemName.getData();
                String StockAmtString = StockAmt.getData();
                String ReStckAmtString = ReStckAmt.getData();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
    
    

    // public Item promptForItemDetails() {
    // }

    // public Item promptForItemDetails() {

    //     FieldText fieldItemCode = new FieldText("Item Code");
    //     contentPanel.add(fieldItemCode);

    //     FieldText fieldItemName = new FieldText("Item Name");
    //     contentPanel.add(fieldItemName);

    //     FieldText fieldSupplierId = new FieldText("Supplier ID");
    //     contentPanel.add(fieldSupplierId);

    //     FieldText fieldStockLevel = new FieldText("Stock Level", true);
    //     contentPanel.add(fieldStockLevel);

    //     FieldText fieldReorderLevel = new FieldText("Reorder Level", true);
    //     contentPanel.add(fieldReorderLevel);

    //     JButton confirmButton = new JButton("Confirm");
    //     confirmButton.addActionListener(e -> {
    //         String itemCode = fieldItemCode.getText();
    //         String itemName = fieldItemName.getText();
    //         String supplierId = fieldSupplierId.getText();
    //         int stockLevel = Integer.parseInt(fieldStockLevel.getText());
    //         int reorderLevel = Integer.parseInt(fieldReorderLevel.getText());

    //         backend.db.addItem(new Item(itemCode, itemName, supplierId, stockLevel, reorderLevel));
    //         JLabel successLabel = new JLabel("Item added successfully!");
    //         successLabel.setForeground(Color.GREEN);
    //         contentPanel.add(successLabel);
    //         Timer timer = new Timer(2000, g -> successLabel.setVisible(false));
    //         timer.setRepeats(false);
    //         timer.start();
    //     });
    //     contentPanel.add(confirmButton);

    //     int result = JOptionPane.showConfirmDialog(null, contentPanel, "Enter Item Details", JOptionPane.OK_CANCEL_OPTION);
    //     if (result == JOptionPane.OK_OPTION) {
    //         String itemCode = fieldItemCode.getText();
    //         String itemName = fieldItemName.getText();
    //         String supplierId = fieldSupplierId.getText();
    //         int stockLevel = Integer.parseInt(fieldStockLevel.getText());
    //         int reorderLevel = Integer.parseInt(fieldReorderLevel.getText());

    //         return new Item(itemCode, itemName, supplierId, stockLevel, reorderLevel);
    //     }
    //     return null; 
    // }

}
