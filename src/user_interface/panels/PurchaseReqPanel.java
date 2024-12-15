package user_interface.panels;

import backend.Backend;
import user_interface.*;

public class PurchaseReqPanel extends Panel<data.PurchaseRequisition> {


    public PurchaseReqPanel(Backend backend, MainMenu parent){
       super("Purchase Requisition", parent, backend.db.purchaseRequisitionsMap, backend);
       
    }
    
}
