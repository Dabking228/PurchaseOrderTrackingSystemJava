package user_interface.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import backend.Backend;
import data.*;

import user_interface.MainMenu;

public class ItemPanel extends Panel<Item> {
    protected FieldText fieldItemID, fieldItemName, fieldNumStock, fieldRestockLevel, fieldPrice;
    protected FieldDropdown<data.Supplier> SupplierDrop;
    protected JButton deleteButton, editButton, editConfirm, editCancel, confirmButton;
    private JLabel greenLabel;
    private ItemListPanel itemList;
    private boolean viewOnly;
    protected Role role;
    protected String rowData;
    private JPanel editcfm;
    protected Item item;
    private String panelName;

    public ItemPanel(Backend backend, MainMenu parent) {
        this(backend, parent, false);

        greenLabel = new JLabel("Item added");
        greenLabel.setVisible(false);
        greenLabel.setForeground(Color.GREEN);
        greenLabel.setFont(new Font(greenLabel.getText(), Font.BOLD, 20));
        contentPanel.add(greenLabel);

        // add confirmation button
        confirmButton = new JButton("Confirm");
        titleButtonPanel.add(confirmButton, 1);

        // confirmation button logic
        confirmButton.addActionListener(e -> {
            try {
                String itemID = fieldItemID.getData();
                String itemName = fieldItemName.getData();
                String supplierID = SupplierDrop.getSelected().getValue().getId();
                int numStock = fieldNumStock.getIntData();
                int minStock = fieldRestockLevel.getIntData();
                BigDecimal price = new BigDecimal(fieldPrice.getData());
                System.out.println(numStock);
                if (!itemID.isEmpty() || !itemName.isEmpty() || numStock != 0 || minStock != 0) {
                    backend.db.addItem(new Item(itemID, itemName, supplierID, numStock, minStock, price));
                    greenLabel.setVisible(true);

                    // reset field
                    fieldItemID.resetField();
                    fieldItemName.resetField();
                    fieldNumStock.resetField();
                    fieldRestockLevel.resetField();
                    fieldPrice.resetField();

                    // adding a timer to hide the greenlabel
                    Timer timer = new Timer(2000, g -> greenLabel.setVisible(false));
                    timer.setRepeats(false);
                    timer.start();
                }

            } catch (Exception err) {
                System.out.println(err);
            }
        });
    }

    public ItemPanel(Backend backend, MainMenu parent, boolean viewOnly) {
        super("Add New Item", parent, backend.db.itemsMap, backend);
        this.viewOnly = viewOnly;
        this.itemList = new ItemListPanel();
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

        fieldPrice = new FieldText("Price", false);
        contentPanel.add(fieldPrice);

        // change the return panel based on the menu name

        backButton.addActionListener(e -> {
            parent.showPanel(panelName);
        });

        // View only setups
        if (viewOnly) {
            fieldItemID.setEditable(false);
            fieldItemName.setEditable(false);
            SupplierDrop.setEditable(false);
            fieldNumStock.setEditable(false);
            fieldRestockLevel.setEditable(false);
            fieldPrice.setEditable(false);

            // init item lists
            itemList.setItem(items);
            itemList.setValue();

            if (role.hasPermission("Items", Permission.CREATE)) {
                // Button creation for Edit, Delete
                editcfm = new JPanel(new GridLayout(1, 2));
                editConfirm = new JButton("Confirm");
                editCancel = new JButton("Cancel");
                editcfm.add(editConfirm);
                editcfm.add(editCancel);
                editcfm.setMaximumSize(new Dimension(300,30));
                editcfm.setVisible(false);
                contentPanel.add(editcfm);

                deleteButton = new JButton("Delete");
                editButton = new JButton("Edit");
                titleButtonPanel.add(deleteButton);
                titleButtonPanel.add(editButton);

                deleteButton.addActionListener(e -> {
                    backend.db.itemsMap.remove(itemList.getObjectUUID(item.getItemCode()));
                    parent.showPanel("itemsTable");
                });

                editButton.addActionListener(e -> {
                    editcfm.setVisible(true);
                    fieldItemName.setEditable(true);
                    fieldNumStock.setEditable(true);
                    fieldRestockLevel.setEditable(true);
                    fieldPrice.setEditable(true);
                });

                editConfirm.addActionListener(e -> {
                    editcfm.setVisible(false);
                    try {
                        String itemName = fieldItemName.getData();
                        int numStock = fieldNumStock.getIntData();
                        int minStock = fieldRestockLevel.getIntData();
                        BigDecimal price = new BigDecimal(fieldPrice.getData());

                        if (item.getItemName() != itemName) {
                            System.out.println("item changed"); // TODO: remove
                            backend.db.itemsMap.get(itemList.getObjectUUID(item.getItemCode())).setItemName(itemName);
                        }
                        if (item.getStockLevel() != numStock) {
                            System.out.println("numstock changed"); // TODO: remove
                            backend.db.itemsMap.get(itemList.getObjectUUID(item.getItemCode())).setStockLevel(numStock);
                        }
                        if (item.getReorderLevel() != minStock) {
                            System.out.println("restocknum changed"); // TODO: remove
                            backend.db.itemsMap.get(itemList.getObjectUUID(item.getItemCode()))
                                    .setReorderLevel(minStock);
                        }
                        if (item.getPrice() != price) {
                            System.out.println("price changed"); // TODO: remove
                            backend.db.itemsMap.get(itemList.getObjectUUID(item.getItemCode())).setPrice(price);
                        }

                        System.out.println("item updated"); // TODO: remove
                        fieldItemName.setEditable(false);
                        fieldNumStock.setEditable(false);
                        fieldRestockLevel.setEditable(false);
                        fieldPrice.setEditable(false);

                        editcfm.setVisible(false);
                    } catch (Exception err) {
                        System.out.println(err);
                    }

                });

                editCancel.addActionListener(e -> {
                    fieldItemName.setEditable(false);
                    fieldNumStock.setEditable(false);
                    fieldRestockLevel.setEditable(false);
                    fieldPrice.setEditable(false);

                    fieldItemID.setData(item.getItemCode());
                    fieldItemName.setData(item.getItemName());
                    SupplierDrop.setData(item.getSupplierId());
                    fieldNumStock.setData(String.valueOf(item.getStockLevel()));
                    fieldRestockLevel.setData(String.valueOf(item.getReorderLevel()));
                    fieldPrice.setData(String.valueOf(item.getPrice()));
                    editcfm.setVisible(false);
                });

            }
        }
    }

    // Function for the panel when is in view only
    public void setData() {
        if (viewOnly) {
            item = itemList.getObject(rowData);
            System.out.println("yay"); // TODO: Remove
            System.out.println(rowData); // TODO: Remove
            System.out.println(itemList.getObject(rowData).getSupplierId()); // TODO: Remove

            fieldItemID.setData(item.getItemCode());
            fieldItemName.setData(item.getItemName());
            SupplierDrop.setData(item.getSupplierId());
            fieldNumStock.setData(String.valueOf(item.getStockLevel()));
            fieldRestockLevel.setData(String.valueOf(item.getReorderLevel()));
            fieldPrice.setData(String.valueOf(item.getPrice()));
        }
    }

    public void setRowNum(String data) {
        this.rowData = data;
    }
    
    public void setBack(String PanelName){
        this.panelName = PanelName;
    }
}

class SupplierList extends ComboList<data.Supplier> {
    @Override
    public void setItem(Map<String, data.Supplier> items) {
        this.items = items;
    }

    @Override
    public String getName(String UUID) {
        return this.items.get(UUID).getSupplierName();
    }

    @SuppressWarnings("unlikely-arg-type")
    @Override
    public String toString() {
        return this.items.get(UUID).getSupplierName();
    }

    @Override
    public void setValue() {
        this.values = new data.Supplier[this.items.size()];
        initGetValue();
    }

    @Override
    public data.Supplier getObject(Object ItemName) {
        for (data.Supplier item : items.values()) {
            if (item.getId() == String.valueOf(ItemName)) {
                return item;
            }
        }
        return null;
    }
}

class ItemListPanel extends ComboList<Item> {
    @Override
    public void setItem(Map<String, Item> items) {
        this.items = items;
    }

    @Override
    public void setValue() {
        this.values = new Item[this.items.size()];
        initGetValue();
    }

    @Override
    public Item getObject(Object ItemName) {
        for (Item item : items.values()) {
            if (item.getItemCode() == String.valueOf(ItemName)) {
                return item;
            }
        }
        return null;
    }

    public String getObjectUUID(Object ItemCode) {
        for (Map.Entry<String, Item> item : items.entrySet()) {
            if (item.getValue().getItemCode() == String.valueOf(ItemCode)) {
                return item.getKey();
            }
        }
        return null;
    }
}