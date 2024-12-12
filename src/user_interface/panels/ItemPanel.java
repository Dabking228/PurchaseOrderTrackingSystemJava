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

public class ItemPanel extends Panel<Item> {
    protected FieldText fieldItemID, fieldItemName, fieldNumStock, fieldRestockLevel;
    protected FieldDropdown<data.Supplier> SupplierDrop;
    protected JButton deleteButton,editButton;
    private ItemList itemList;
    private boolean viewOnly;
    protected Role role;
    protected String rowData;

    public ItemPanel(Backend backend, MainMenu parent){
        this(backend,parent,false);

        JLabel greenLabel = new JLabel("Item added");
        greenLabel.setVisible(false);
        greenLabel.setForeground(Color.GREEN);
        greenLabel.setFont(new Font(greenLabel.getText(), Font.BOLD, 20));
        contentPanel.add(greenLabel);

        JButton confirmButton = new JButton("Confirm?");
        confirmButton.addActionListener(e -> {
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

    public ItemPanel(Backend backend, MainMenu parent, boolean viewOnly) {
        super("Add New Item", parent, backend.db.itemsMap, backend);
        this.viewOnly = viewOnly;
        this.itemList = new ItemList();
        role = backend.getCurrentAccount().getRole();

        fieldItemID = new FieldText("Item ID");
        contentPanel.add(fieldItemID);

        fieldItemName = new FieldText("Item Name");
        contentPanel.add(fieldItemName);

        SupplierDrop = new FieldDropdown<data.Supplier>("Supplier", backend.db.suppliersMap, new SupplierList());
        contentPanel.add(SupplierDrop);

        fieldNumStock = new FieldText("Stock", true);
        contentPanel.add(fieldNumStock);

        fieldRestockLevel = new FieldText("Minimum Stock", true);
        contentPanel.add(fieldRestockLevel);


        // change the return panel based on the menu name
        backMainMenu("itemsTable");

        // View only setups
        if(viewOnly){
            fieldItemID.setEditable(false);
            fieldItemName.setEditable(false);
            SupplierDrop.setEditable(false);
            fieldNumStock.setEditable(false);
            fieldRestockLevel.setEditable(false);

            //  init item lists
            itemList.setItem(items);
            itemList.setValue();

            if (role.hasPermission("Items", Permission.CREATE)) {
                // Button creation for Edit, Delete
                deleteButton = new JButton("Delete");
                editButton = new JButton("Edit");
                titleButtonPanel.add(deleteButton); 
                titleButtonPanel.add(editButton);
            }
        }
    }
    
    // Function for the panel when is in view only
    public void setData(){
        Item item;
        if(viewOnly){
            if (role.hasPermission("Items", Permission.CREATE)) {
                item = itemList.getObject(rowData);
                System.out.println("yay");
                System.out.println(rowData);
                System.out.println(itemList.getObject(rowData).getSupplierId());

                fieldItemID.setData(item.getItemCode());
                fieldItemName.setData(item.getItemName());
                SupplierDrop.setData(item.getSupplierId());
                fieldNumStock.setData(String.valueOf(item.getStockLevel()));
                fieldRestockLevel.setData(String.valueOf(item.getReorderLevel()));
                
                // function for the edit and delete button
                deleteButton.addActionListener(e -> {
                    ConfirmPane cp = new ConfirmPane("Add thing item?",  "Add Item", this, JOptionPane.YES_NO_OPTION);
                    if(cp.getResult() == JOptionPane.YES_OPTION){
                        backend.db.itemsMap.remove(itemList.getObjectUUID(item.getItemCode()));
                        parent.showPanel("itemsTable");
                    } else {
                        
                    }
                    
                });
            }
        }
    }

    public void setRowNum(String data){
        this.rowData = data;
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

    @Override
    public data.Supplier getObject(Object ItemName){
        for(data.Supplier item: items.values()){
            if(item.getId() == String.valueOf(ItemName)){
                return item;
            }
        }
        return null;
    }
}

class ItemList extends ComboList<Item> {
    @Override
    public void setItem(Map<String, Item> items){
        this.items = items;
    }

    @Override
    public void setValue(){
        this.values = new Item[this.items.size()];
        initGetValue();
    }   

    @Override
    public Item getObject(Object ItemName){
        for(Item item: items.values()){
            if(item.getItemCode() == String.valueOf(ItemName)){
                return item;
            }
        }
        return null;
    }

    public String getObjectUUID(Object ItemName){
        for(Map.Entry<String, Item> item : items.entrySet()){
            if(item.getValue().getItemCode() == String.valueOf(ItemName)){
                return item.getKey();
            }
        }
        return null;
    }
}