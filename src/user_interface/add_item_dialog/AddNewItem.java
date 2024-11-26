package user_interface.add_item_dialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import backend.Backend;
import user_interface.MainMenu;

import java.awt.*;
import java.awt.event.*;

public class AddNewItem extends JPanel {
    private JTextField itemCodeField;
    private JTextField itemNameField;
    private JTextField supplierIdField;
    private JTextField stockLevelField;
    private JTextField reorderLevelField;
    private JTable table;
    private DefaultTableModel tableModel;

    public AddNewItem(JTable table) {
        this.table = table;
        this.tableModel = (DefaultTableModel) table.getModel();
        setLayout(new GridLayout(6, 2, 5, 5));

        add(new JLabel("Item Code:"));
        itemCodeField = new JTextField();
        add(itemCodeField);

        add(new JLabel("Item Name:"));
        itemNameField = new JTextField();
        add(itemNameField);

        add(new JLabel("Supplier ID:"));
        supplierIdField = new JTextField();
        add(supplierIdField);

        add(new JLabel("Stock Level:"));
        stockLevelField = new JTextField();
        add(stockLevelField);

        add(new JLabel("Reorder Level:"));
        reorderLevelField = new JTextField();
        add(reorderLevelField);

        JButton addButton = new JButton("Add Item");
        addButton.addActionListener(e -> addItem());
        add(addButton);

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> clearFields());
        add(clearButton);
    }

    public AddNewItem(Backend backend) {
        // TODO Auto-generated constructor stub
    }

    private void addItem() {
        try {
            String code = itemCodeField.getText();
            String name = itemNameField.getText();
            String supplierId = supplierIdField.getText();
            int stockLevel = Integer.parseInt(stockLevelField.getText());
            int reorderLevel = Integer.parseInt(reorderLevelField.getText());

            tableModel.addRow(new Object[] {
                    code,
                    name,
                    supplierId,
                    stockLevel,
                    reorderLevel
            });

            JOptionPane.showMessageDialog(this, "Item added successfully!");
            clearFields();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter valid numbers for stock and reorder levels",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
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
