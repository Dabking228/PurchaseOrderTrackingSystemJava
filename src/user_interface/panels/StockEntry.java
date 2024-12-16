package user_interface.panels;
import backend.Backend;
import data.Item;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

import user_interface.*;

public class StockEntry extends Panel<Item> {
    protected FieldDropdown<Item> itemIDDropdown;
    protected FieldText itemName, StockAmt, ReStockAmt, itemIDString;
    private JButton confirmButton, cancelButton;
    private JLabel successLabel;


    public StockEntry(Backend backend, MainMenu parent) {
        super("Stock Entry Form", parent, backend.db.itemsMap, backend);

        // itemID = new FieldText("Item ID: ");
        // contentPanel.add(itemID);
        itemIDDropdown = new FieldDropdown<>("Item ID: ", backend.db.itemsMap, new ItemList());
        contentPanel.add(itemIDDropdown);

        itemName = new FieldText("Item Name: ");
        itemName.setEditable(false);
        contentPanel.add(itemName);

        StockAmt = new FieldText("Stock: ", true);
        contentPanel.add(StockAmt);

        ReStockAmt = new FieldText("Minimum Amount of Stock: ", true);
        ReStockAmt.setEditable(false);
        contentPanel.add(ReStockAmt);
        
        backButton.addActionListener(e -> {
            parent.showMainMenu();
        });

        // Success label
        successLabel = new JLabel("Stock entered successfully!");
        successLabel.setVisible(false);
        successLabel.setForeground(Color.GREEN);
        successLabel.setFont(new Font(successLabel.getText(), Font.BOLD, 20));
        contentPanel.add(successLabel);

        // Confirm Button
        confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> {
            try {
                // Fetch selected item from dropdown
                Item selectedItem = itemIDDropdown.getSelected().getValue();
                if (selectedItem != null) {
                    // Update stock amount
                    int stockAmount = StockAmt.getIntData();
                    selectedItem.setStockLevel(stockAmount);

                    // Show success message
                    successLabel.setVisible(true);

                    // Reset stock input field
                    StockAmt.resetField();

                    // Save changes to the database
                    backend.db.save();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        contentPanel.add(confirmButton);

        // Cancel Button
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            parent.showMainMenu();
        });
        contentPanel.add(cancelButton);

        //Set up dropdown listener to populate fields
        itemIDDropdown.addActionListener(e -> {
            try {
                Item selectedItem = itemIDDropdown.getSelected().getValue();
                if (selectedItem != null) {
                    fieldItemName.setData(selectedItem.getItemName());
                    fieldRestockLevel.setData(String.valueOf(selectedItem.getReorderLevel()));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    
    }
}   

        // JButton confirmButton = new JButton("Confirm");
        // confirmButton.addActionListener(e -> {
        //     try {
                
        //         String itemIDString = itemIDDropdown.getData();
        //         String itemNameString = itemName.getData();
        //         String StockAmtString = StockAmt.getData();
        //         String ReStockAmtString = ReStockAmt.getData();

        //     } catch (Exception ex) {
        //         ex.printStackTrace();
        //     }
        // });
  



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


