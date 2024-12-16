package user_interface.panels;


import backend.Backend;
import javax.swing.JButton;
import user_interface.*;

public class PurchaseOrderPanel extends Panel<data.PurchaseOrder> {
    protected FieldText fieldItemID, fieldSupplierID, fieldPRStatus, fieldPOStatus, fieldtotalAmount;
    
    public PurchaseOrderPanel(Backend backend, MainMenu parent) {

        super("Purchase Order Panel", parent, backend.db.purchaseOrdersMap, backend);

        fieldItemID = new FieldText("Item Name");
        contentPanel.add(fieldItemID);

        fieldSupplierID = new FieldText("Supplier");
        contentPanel.add(fieldSupplierID);

        fieldPRStatus = new FieldText("PR Status");
        contentPanel.add(fieldPRStatus);

        fieldPOStatus = new FieldText("Purchase orders Status");
        contentPanel.add(fieldPOStatus);

        fieldtotalAmount = new FieldText("Purchase orders Stats");
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
}
