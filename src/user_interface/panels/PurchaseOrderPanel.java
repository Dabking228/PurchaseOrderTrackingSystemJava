package user_interface.panels;


import backend.Database;

import user_interface.*;

public class PurchaseOrderPanel extends Panel<data.PurchaseOrder> {
    public PurchaseOrderPanel(Backend backend, MainMenu parent) {

        super("Purchase Order Panel", parent, backend.db.purchaseOrdersMap, backend);

        PurReqID = new FieldText("Purchase ID");
        contentPanel.add(PurReqID);

        SupplierID = new FieldText("Supplier ID");
}
