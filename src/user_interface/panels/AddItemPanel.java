package user_interface.panels;


import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JPanel;

import backend.Backend;
import data.Item;
import user_interface.MainMenu;
import user_interface.add_item_dialog.AddNewItem;
import user_interface.components.FieldCombo;


public class AddItemPanel extends Panel<Item> {
    public AddItemPanel(Backend backend, MainMenu parent){
        super("Add New Item",parent, backend.db.itemsMap, backend);

        FieldCombo itemID = new FieldCombo("Item ID");
        contentPanel.add(itemID);

        FieldCombo itemName = new FieldCombo("Item Name");
        contentPanel.add(itemName);

        // supplier dropdown here i guess

        FieldCombo numStock = new FieldCombo("Stock",true);
        contentPanel.add(numStock);

        FieldCombo restockLevel = new FieldCombo("Minimum Stock", true);
        contentPanel.add(restockLevel);

        JButton confirmButton = new JButton("Confirm?");
        confirmButton.addActionListener(e -> {
            //TODO
            System.out.println(itemID.getText());
            System.out.println(numStock.getText());
        });
        titleButtonPanel.add(confirmButton,1);
    }
}


