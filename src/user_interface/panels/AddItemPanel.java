package user_interface.panels;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.xml.crypto.Data;

import backend.Backend;
import data.*;
import user_interface.MainMenu;
import user_interface.add_item_dialog.AddNewItem;

public class AddItemPanel extends Panel<Item> {
    public AddItemPanel(Backend backend, MainMenu parent) {
        super("Add New Item", parent, backend.db.itemsMap, backend);

        FieldText fieldItemID = new FieldText("Item ID");
        contentPanel.add(fieldItemID);

        FieldText fieldItemName = new FieldText("Item Name");
        contentPanel.add(fieldItemName);

        FieldCombo<data.Supplier> SupplierDrop = 
            new FieldCombo<data.Supplier>("Supplier", backend.db.suppliersMap, new SupplierList());
        contentPanel.add(SupplierDrop);

        FieldText fieldNumStock = new FieldText("Stock", true);
        contentPanel.add(fieldNumStock);

        FieldText fieldRestockLevel = new FieldText("Minimum Stock", true);
        contentPanel.add(fieldRestockLevel);

        JButton confirmButton = new JButton("Confirm?");
        confirmButton.addActionListener(e -> {
            // Test code
            String itemID = fieldItemID.getData();
            String itemName = fieldItemName.getData();
            String supplierID = SupplierDrop.getSelected().getValue().getId();
            int numStock = Integer.parseInt(fieldNumStock.getData());
            int minStock = Integer.parseInt(fieldRestockLevel.getData());
            
            backend.db.addItem(new Item(itemID,itemName,supplierID,numStock,minStock));


            // TODO
            // add pop up after pressing comfirm
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