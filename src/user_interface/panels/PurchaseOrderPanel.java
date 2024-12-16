package user_interface.panels;


import backend.Backend;
import data.Item;
import data.PurchaseOrder;
import data.PurchaseRequisition;

import java.util.Map;

import javax.swing.JButton;
import user_interface.*;

public class PurchaseOrderPanel extends Panel<PurchaseOrder> {
    private String rowData;
    private PurchaseOrder PO;

    protected FieldText fieldItemID, fieldSupplierID, fieldPRStatus, fieldPOStatus, fieldtotalAmount, fieldCurrStock;

    public PurchaseOrderPanel(Backend backend, MainMenu parent) {
        super("Purchase Order Panel", parent, backend.db.purchaseOrdersMap, backend);

        fieldItemID = new FieldText("Item Name");
        fieldItemID.setEditable(false);
        contentPanel.add(fieldItemID);

        fieldSupplierID = new FieldText("Supplier");
        fieldSupplierID.setEditable(false);
        contentPanel.add(fieldSupplierID);

        fieldPRStatus = new FieldText("PR Status");
        fieldPRStatus.setEditable(false);
        contentPanel.add(fieldPRStatus);

        fieldPOStatus = new FieldText("PO Status");
        fieldPOStatus.setEditable(false);
        contentPanel.add(fieldPOStatus);

        fieldCurrStock = new FieldText("Current Stock");
        fieldCurrStock.setEditable(false);
        contentPanel.add(fieldCurrStock);

        fieldtotalAmount = new FieldText("Total");
        contentPanel.add(fieldtotalAmount);


        backButton.addActionListener(e -> {
            parent.showMainMenu();
        });

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> {
            // try {
            //     String PurReqIDString = PurReqID.getData();
            //     String SupplierIDString = SupplierID.getData();
            //     String POStatString = POStat.getData();
                
            // } catch (Exception ex) {
            //     ex.printStackTrace();
            // }
        });
    }

    public void viewOnly(){

    }

    public void setData(){
        PO = backend.db.purchaseOrdersMap.get(rowData);
        PurchaseRequisition PR = backend.db.purchaseRequisitionsMap.get(PO.getPurchaseRequisitionId());
        Item item = backend.db.itemsMap.get(PR.getItemId());

        String itemName = item.getItemName();

        String supplierName = backend.db.suppliersMap.get(item.getSupplierId()).getSupplierName();
       
        fieldItemID.setData(itemName);
        fieldSupplierID.setData(supplierName);
        fieldPRStatus.setData(PR.getStatus().toString());
        fieldPOStatus.setData(PO.getPoStatus().toString());
        fieldCurrStock.setData(String.valueOf(item.getStockLevel()));
        fieldtotalAmount.setData(String.valueOf(PO.getTotalAmount()));
    }

    public void setRowNum(String data){
        this.rowData = data;
    }

}

class PRListPO extends ComboList<PurchaseRequisition> {
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

class ItemListPO extends ComboList<Item> {
    @Override
    public void setItem(Map<String, Item> items) {
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
        this.values = new Item[this.items.size()];
        initGetValue();
    }

    @Override
    public Item getObject(Object ItemName) {
        for (Item item : items.values()) {
            if (item.getItemName() == String.valueOf(ItemName)) {
                return item;
            }
        }
        return null;
    }
}