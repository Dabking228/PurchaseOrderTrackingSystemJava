package user_interface.panels;

import java.awt.Color;
import java.awt.Font;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.xml.crypto.Data;

import backend.Backend;
import data.*;
import user_interface.ConfirmPane;
import user_interface.MainMenu;
import user_interface.add_item_dialog.AddNewItem;

public class AddItemPanel extends Panel<Item> {
    public AddItemPanel(Backend backend, MainMenu parent) {
        super("Add New Item", parent, backend.db.itemsMap, backend);

        FieldText fieldItemID = new FieldText("Item ID");
        contentPanel.add(fieldItemID);

        FieldText fieldItemName = new FieldText("Item Name");
        contentPanel.add(fieldItemName);

        FieldDropdown<data.Supplier> SupplierDrop = 
            new FieldDropdown<data.Supplier>("Supplier", backend.db.suppliersMap, new SupplierList());
        contentPanel.add(SupplierDrop);

        FieldText fieldNumStock = new FieldText("Stock", true);
        contentPanel.add(fieldNumStock);

        FieldText fieldRestockLevel = new FieldText("Minimum Stock", true);
        contentPanel.add(fieldRestockLevel);

        JLabel greenLabel = new JLabel("Item added");
        greenLabel.setVisible(false);
        greenLabel.setForeground(Color.GREEN);
        greenLabel.setFont(new Font(greenLabel.getText(), Font.BOLD, 20));
        contentPanel.add(greenLabel);

        JButton confirmButton = new JButton("Confirm?");
        confirmButton.addActionListener(e -> {
            // Test code
            String itemID = fieldItemID.getData();
            String itemName = fieldItemName.getData();
            String supplierID = SupplierDrop.getSelected().getValue().getId();
            int numStock = Integer.parseInt(fieldNumStock.getData());
            int minStock = Integer.parseInt(fieldRestockLevel.getData());
            
            
            ConfirmPane cp = new ConfirmPane("Add thing item?",  "Add Item", this, JOptionPane.YES_NO_OPTION);
            if(cp.getResult() == JOptionPane.YES_OPTION){
                backend.db.addItem(new Item(itemID,itemName,supplierID,numStock,minStock));
                greenLabel.setVisible(true);
            } else {
                parent.showMainMenu();
            }
            
            // reset field
            fieldItemID.resetField();
            fieldItemName.resetField();
            fieldNumStock.resetField();
            fieldRestockLevel.resetField();

            // adding a timer to hide the greenlabel
            Timer timer = new Timer(2000, g -> greenLabel.setVisible(false));
            timer.setRepeats(false);
            timer.start();

        });
        titleButtonPanel.add(confirmButton, 1);
    }
}

class SupplierList extends ComboList<data.Supplier>{
    @Override
    public void setItem(Map<String, data.Supplier> items){
        this.items = items;
    }

    @Override
    public String getName(String UUID){
        return this.items.get(UUID).getSupplierName();
    }

    @Override
    public String toString(){
        return this.items.get(UUID).getSupplierName();
    }

    @Override
    public void setValue(){
        this.values = new data.Supplier[this.items.size()];
        initGetValue();
    }


    
}