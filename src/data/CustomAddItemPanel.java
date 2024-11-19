package user_interface;

import backend.Backend;

public class CustomAddItemPanel extends AbstractAddItemPanel {

    public CustomAddItemPanel(Backend backend, String categoryName) {
        super(backend, categoryName);
    }

    @Override
    protected void addItem() {
        String code = itemCodeField.getText();
        String name = itemNameField.getText();
        String supplierId = supplierIdField.getText();
        int stockLevel = Integer.parseInt(stockLevelField.getText());
        int reorderLevel = Integer.parseInt(reorderLevelField.getText());


        backend.addCustomItem(code, name, supplierId, stockLevel, reorderLevel, categoryName);
        clearFields();
    }
}