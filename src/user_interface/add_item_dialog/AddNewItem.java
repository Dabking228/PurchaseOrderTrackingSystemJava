package user_interface.add_item_dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import backend.Backend;
import data.Item;

public class AddNewItem extends JPanel {

    private JTextField itemCodeField;
    private JTextField itemNameField;
    private JTextField supplierIdField;
    private JTextField stockLevelField;
    private JTextField reorderLevelField;
    private Backend backend;

    public AddNewItem(Backend backend) {
        this.backend = backend;
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridLayout(6, 2, 10, 10));

        JLabel itemCodeLabel = new JLabel("Item Code:");
        itemCodeField = new JTextField();

        JLabel itemNameLabel = new JLabel("Item Name:");
        itemNameField = new JTextField();

        JLabel supplierIdLabel = new JLabel("Supplier ID:");
        supplierIdField = new JTextField();

        JLabel stockLevelLabel = new JLabel("Stock Level:");
        stockLevelField = new JTextField();

        JLabel reorderLevelLabel = new JLabel("Reorder Level:");
        reorderLevelField = new JTextField();

        JButton submitButton = new JButton("Add Item");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItem();
            }
        });

        add(itemCodeLabel);
        add(itemCodeField);
        add(itemNameLabel);
        add(itemNameField);
        add(supplierIdLabel);
        add(supplierIdField);
        add(stockLevelLabel);
        add(stockLevelField);
        add(reorderLevelLabel);
        add(reorderLevelField);
        add(new JLabel()); // Empty cell for spacing
        add(submitButton);
    }

    private void addItem() {
        String itemCode = itemCodeField.getText();
        String itemName = itemNameField.getText();
        // TODO let them select from a list of suppliers
        String supplierId = supplierIdField.getText();
        int stockLevel = Integer.parseInt(stockLevelField.getText());
        int reorderLevel = Integer.parseInt(reorderLevelField.getText());

        Item newItem = new Item(itemCode, itemName, supplierId, stockLevel, reorderLevel);

        // Assuming you have a method in Backend to add the item to the database
        boolean success = backend.addItem(newItem);
        if (success) {
            JOptionPane.showMessageDialog(this, "Item added successfully!");
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add item. Please try again.");
        }
    }

    private void clearFields() {
        itemCodeField.setText("");
        itemNameField.setText("");
        supplierIdField.setText("");
        stockLevelField.setText("");
        reorderLevelField.setText("");
    }
}
