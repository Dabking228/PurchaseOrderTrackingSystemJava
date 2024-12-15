package user_interface.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Date;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import backend.Backend;
import data.Item;
import data.Permission;
import data.PurchaseRequisition;
import data.Role;
import data.Status;
import user_interface.*;

public class PurchaseReqPanel extends Panel<data.PurchaseRequisition> {
    // private boolean viewOnly;
    protected String rowData;
    private PRListReq PRList;
    private PurchaseRequisition PR;
    private Item item;
    protected ItemListReq ItemList;

    protected FieldDropdown<data.Item> dropItemID;
    protected FieldText fieldItemName, fieldItemStock, fieldItemMinStock, fieldRestockVal;
    protected JLabel greenLabel;
    protected JPanel editcfm;
    protected JButton editConfirm, editCancel, confirmButton, deleteButton, editButton;

    public PurchaseReqPanel(Backend backend, MainMenu parent) {
        super("Purchase Requisition", parent, backend.db.purchaseRequisitionsMap, backend);
        this.backend = backend;
        this.PRList = new PRListReq();
        this.ItemList = new ItemListReq();

        dropItemID = new FieldDropdown<data.Item>("Item Name", backend.db.itemsMap, ItemList);
        dropItemID.fieldCombo.setSelectedIndex(-1);
        contentPanel.add(dropItemID);

        fieldItemStock = new FieldText("Stock", true);
        fieldItemStock.setEditable(false);
        contentPanel.add(fieldItemStock);

        fieldItemMinStock = new FieldText("Min Stock", true);
        fieldItemMinStock.setEditable(false);
        contentPanel.add(fieldItemMinStock);

        fieldRestockVal = new FieldText("Item Ordered", true);
        contentPanel.add(fieldRestockVal);

        greenLabel = new JLabel("Item added");
        greenLabel.setVisible(false);
        greenLabel.setForeground(Color.GREEN);
        greenLabel.setFont(new Font(greenLabel.getText(), Font.BOLD, 20));
        contentPanel.add(greenLabel);

        backButton.addActionListener(e -> {
            parent.showPanel("purchaseRequisitionTable");
            dropItemID.fieldCombo.setSelectedIndex(-1);
            fieldItemStock.setData("");
            fieldItemMinStock.setData("");
            fieldRestockVal.setData("");
        });

        confirmButton = new JButton("Confirm");
        titleButtonPanel.add(confirmButton, 1);

        deleteButton = new JButton("Delete");
        editButton = new JButton("Edit");
        titleButtonPanel.add(deleteButton);
        titleButtonPanel.add(editButton);

        editcfm = new JPanel(new GridLayout(1, 2));
        editConfirm = new JButton("Confirm");
        editCancel = new JButton("Cancel");
        editcfm.add(editConfirm);
        editcfm.add(editCancel);
        editcfm.setMaximumSize(new Dimension(300, 30));
        contentPanel.add(editcfm);


        PRList.setItem(items);
        PRList.setValue();

        // auto pull data from itemsmap to fill up the details
        dropItemID.fieldCombo.addActionListener(e -> {
            // System.out.println(dropItemID.getSelected().getValue());

            data.Item item = (dropItemID.getSelected() != null)
                    ? (data.Item) dropItemID.getSelected().getValue()
                    : null;
            if (item != null) {
                fieldItemStock.setData(String.valueOf(item.getStockLevel()));
                fieldItemMinStock.setData(String.valueOf(item.getReorderLevel()));
            }
        });

        // cfm button logic
        confirmButton.addActionListener(e -> {
            try {
                String itemID = dropItemID.getSelected().getValue().getId();
                int RestockVal = fieldRestockVal.getIntData();
                System.out.println(dropItemID.getSelected().getValue().getId());
                System.out.println(fieldRestockVal.getIntData());

                if(!itemID.isEmpty()|| RestockVal != 0){
                    backend.db.addPurchaseRequisition(new PurchaseRequisition(itemID, RestockVal, new java.util.Date(), 1, Status.PENDING));
                    greenLabel.setVisible(true);

                    dropItemID.fieldCombo.setSelectedIndex(-1);
                    fieldItemStock.setData("");
                    fieldItemMinStock.setData("");
                    fieldRestockVal.setData("");
                    

                    Timer timer = new Timer(2000, g -> greenLabel.setVisible(false));
                    timer.setRepeats(false);
                    timer.start();
                }

            } catch (Exception err) {
                System.out.println(err);
            }
        });

        //delete button logic
        deleteButton.addActionListener(e -> {
            backend.db.purchaseRequisitionsMap.remove(PR.getId());
            parent.showMainMenu(); // somehow deleting the item make the table funky
            System.out.println(backend.db.purchaseRequisitionsMap);
        });

        editButton.addActionListener(e -> {
            editcfm.setVisible(true);
            fieldRestockVal.setEditable(true);
        });

        editConfirm.addActionListener(e -> {
            editcfm.setVisible(false);
            
            try{
                int RestockVal = fieldRestockVal.getIntData();


                if (PR.getQuantity() != RestockVal) {
                    System.out.println("restocknum changed"); //TODO: remove
                    backend.db.purchaseRequisitionsMap.get(PR.getId()).setQuantity(RestockVal);
                }

                System.out.println("item updated"); //TODO: remove
                fieldRestockVal.setEditable(false);

                editcfm.setVisible(false);
            } catch (Exception err){
                System.out.println(err);
                fieldRestockVal.setEditable(false);
                fieldRestockVal.setData(String.valueOf(PR.getQuantity()));
            }
           
            
        });

        editCancel.addActionListener(e -> {
            fieldRestockVal.setEditable(false);
            fieldRestockVal.setData(String.valueOf(PR.getQuantity()));
            
            editcfm.setVisible(false);
        });
    }


    public void createPR() {
        createHideOrShow(true);
        editorHideOrShow(false);
        editHideOrShow(false);

        dropItemID.setEditable(true);
        fieldRestockVal.setEditable(true);
    }

    void createHideOrShow(boolean bool) {
        confirmButton.setVisible(bool);
    }

    void editorHideOrShow(boolean bool) {
        editButton.setVisible(bool);
        deleteButton.setVisible(bool);
    }

    void editHideOrShow(boolean bool){
        editcfm.setVisible(bool);
    }

    public void viewOnly() {
        createHideOrShow(false);
        editHideOrShow(false);

        dropItemID.setEditable(false);
        fieldRestockVal.setEditable(false);
    }

    public void viewOnlyUpdate() {
        editorHideOrShow(true);
    }

    public void setData() {
        item = ItemList.getObject(rowData);
        PR = PRList.getObject(item.getId());
        System.out.println(backend.db.purchaseRequisitionsMap);
        System.out.println("yay"); // TODO: Remove
        System.out.println(rowData); // TODO: Remove
        System.out.println(PRList.getObject(item.getId()).getId()); // TODO: Remove

        dropItemID.setData(PR.getItemId());
        fieldItemStock.setData(String.valueOf(item.getStockLevel()));
        fieldItemMinStock.setData(String.valueOf(item.getReorderLevel()));
        fieldRestockVal.setData(String.valueOf(PR.getQuantity()));
    }

    public void setRowNum(String data) {
        this.rowData = data;
    }
}

class ItemListReq extends ComboList<data.Item> {
    @Override
    public void setItem(Map<String, data.Item> items) {
        this.items = items;
    }

    @Override
    public String getName(String UUID) {
        return this.items.get(UUID).getItemName();
    }

    @SuppressWarnings("unlikely-arg-type")
    @Override
    public String toString() {
        return this.items.get(UUID).getId();
    }

    @Override
    public void setValue() {
        this.values = new data.Item[this.items.size()];
        initGetValue();
    }

    @Override
    public data.Item getObject(Object ItemName) {
        for (data.Item item : items.values()) {
            if (item.getItemName() == String.valueOf(ItemName)) {
                return item;
            }
        }
        return null;
    }
}

class PRListReq extends ComboList<PurchaseRequisition> {
    @Override
    public void setItem(Map<String, PurchaseRequisition> items) {
        this.items = items;
    }

    @Override
    public void setValue() {
        this.values = new PurchaseRequisition[this.items.size()];
        initGetValue();
    }

    @Override
    public PurchaseRequisition getObject(Object ItemName) {
        for (PurchaseRequisition item : items.values()) {
            if (item.getItemId() == String.valueOf(ItemName)) {
                return item;
            }
        }
        return null;
    }

    public String getObjectUUID(Object ItemCode) {
        for (Map.Entry<String, PurchaseRequisition> item : items.entrySet()) {
            if (item.getValue().getId() == String.valueOf(ItemCode)) {
                return item.getKey();
            }
        }
        return null;
    }
}