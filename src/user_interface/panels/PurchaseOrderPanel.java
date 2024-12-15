package user_interface.panels;


import backend.Backend;
import javax.swing.JButton;
import user_interface.*;

public class PurchaseOrderPanel extends Panel<data.PurchaseOrder> {
    protected FieldText PurReqID, SupplierID, POStat, CreateDate, TotalAmt, ID, PurReqIDString, SupplierIDString
    , POStatString;
    public PurchaseOrderPanel(Backend backend, MainMenu parent) {

        super("Purchase Order Panel", parent, backend.db.purchaseOrdersMap, backend);

        PurReqID = new FieldText("Purchase ID");
        contentPanel.add(PurReqID);

        SupplierID = new FieldText("Supplier ID");
        contentPanel.add(SupplierID);

        POStat = new FieldText("Purchase orders Stats");
        contentPanel.add(POStat);

        CreateDate = new FieldText("Date Created");
        contentPanel.add(CreateDate);

        TotalAmt = new FieldText("Purchase orders Stats");
        contentPanel.add(TotalAmt);

        ID = new FieldText("Purchase orders Stats");
        contentPanel.add(ID);

        backButton.addActionListener(e -> {
            parent.showMainMenu();
        });

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> {
            try {
                String PurReqIDString = PurReqID.getData();
                String SupplierIDString = SupplierID.getData();
                String POStatString = POStat.getData();
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
