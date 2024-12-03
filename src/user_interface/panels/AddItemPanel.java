package user_interface.panels;


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

        FieldCombo field = new FieldCombo("Item ID");
        contentPanel.add(field);

        JButton confirmButton = new JButton("Confirm?");
        confirmButton.addActionListener(e -> {
            //TODO
            // System.out.println(field.getText());
        });
        titleButtonPanel.add(confirmButton);
    }
}

// class AddItemSubPanel extends SubPanel{
    
// }

