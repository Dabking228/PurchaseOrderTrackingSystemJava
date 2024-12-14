import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.Timer;
import javax.swing.JLabel;

import backend.Backend;
import data.*;
import user_interface.MainMenu;
public class StockEntry extends Panel<Item> {

    public StockEntry() {
    }

    public Item promptForItemDetails() {
    }

    public Item promptForItemDetails() {
        super();

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

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> {
            String itemCode = fieldItemCode.getText();
            String itemName = fieldItemName.getText();
            String supplierId = fieldSupplierId.getText();
            int stockLevel = Integer.parseInt(fieldStockLevel.getText());
            int reorderLevel = Integer.parseInt(fieldReorderLevel.getText());

            backend.db.addItem(new Item(itemCode, itemName, supplierId, stockLevel, reorderLevel));
            JLabel successLabel = new JLabel("Item added successfully!");
            successLabel.setForeground(Color.GREEN);
            contentPanel.add(successLabel);
            Timer timer = new Timer(2000, g -> successLabel.setVisible(false));
            timer.setRepeats(false);
            timer.start();
        });
        contentPanel.add(confirmButton);

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
