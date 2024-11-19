package user_interface.add_item_dialog;

import backend.Backend;

public class CustomAddItemPanel extends AbstractAddItemPanel {

    public CustomAddItemPanel(Backend backend) {
        super(backend);
    }

    // @Override
    protected void addItem() {
        String code = itemCodeField.getText();
        String name = itemNameField.getText();
        String supplierId = supplierIdField.getText();
        int stockLevel = Integer.parseInt(stockLevelField.getText());
        int reorderLevel = Integer.parseInt(reorderLevelField.getText());

        // backend.addCustomItem(code, name, supplierId, stockLevel, reorderLevel);
        // clearFields();
    }
}
