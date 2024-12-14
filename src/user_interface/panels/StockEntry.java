import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

import backend.Backend;
import data.*;
import user_interface.MainMenu;
public class StockEntry extends data<Item> {


    public Item promptForItemDetails() {
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));

        FieldText fieldItemCode = new FieldText("Item Code");
        contentPanel.add(fieldItemCode);

        FieldText fieldItemName = new FieldText("Item Name");
        contentPanel.add(fieldItemName);

        FieldText fieldSupplierId = new FieldText("Supplier ID");
        contentPanel.add(fieldSupplierId);

        FieldText fieldStockLevel = new FieldText("Stock Level", true);
        contentPanel.add(fieldStockLevel);

        FieldText fieldReorderLevel = new FieldText("Reorder Level", true);
        contentPanel.add(fieldReorderLevel);

        int result = JOptionPane.showConfirmDialog(null, contentPanel, "Enter Item Details", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String itemCode = fieldItemCode.getText();
            String itemName = fieldItemName.getText();
            String supplierId = fieldSupplierId.getText();
            int stockLevel = Integer.parseInt(fieldStockLevel.getText());
            int reorderLevel = Integer.parseInt(fieldReorderLevel.getText());

            return new Item(itemCode, itemName, supplierId, stockLevel, reorderLevel);
        }
        return null; 
    }

}
