package user_interface.panels;

import backend.Backend;
import user_interface.*;

public class PurchaseReqPanel extends Panel<data.PurchaseRequisition> {
    protected FieldText itemId, quantity, date, SaleMangID, Status, ID;

    public PurchaseReqPanel(Backend backend, MainMenu parent){
       super("Purchase Requisition", parent, backend.db.purchaseRequisitionsMap, backend);
       
       FieldText itemId = new FieldText("Item ID: ");
       contentPanel.add(itemId);

       quantity = new FieldText("Item Name: ");
       contentPanel.add(quantity);

       date = new FieldText("Date");
       contentPanel.add(date);

       SaleMangID =  new FieldText("Sales Manger ID: ");
       contentPanel.add(SaleMangID);

       Status = new FieldText("Status: ");
       contentPanel.add(Status);

       ID = new FieldText("User ID: ");
       contentPanel.add(ID);

       backButton.addActionListener(e -> {
        parent.showMainMenu();
    });

    }
    
}
