package user_interface.add_item_dialog;

import backend.Backend;

import javax.swing.*;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class AbstractAddItemPanel extends JPanel {

    protected JTextField itemCodeField;
    protected JTextField itemNameField;
    protected JTextField supplierIdField;
    protected JTextField stockLevelField;
    protected JTextField reorderLevelField;
    protected Backend backend;

    public AbstractAddItemPanel(Backend backend) {
        this.backend = backend;
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridLayout(6, 2, 10, 18));

        JLabel itemCodeLabel = new JLabel("Item Code:");
        itemCodeField = new JTextField();

        JLabel itemNameLabel = new JLabel("Item Name:");
        itemNameField = new JTextField();

        JLabel supplierIdLabel = new JLabel("Supplier ID:");
        supplierIdField = new JTextField();

        JLabel stockLevellLabel = new JLabel("Stock Level:");
        stockLevelField = new JTextField();

        JLabel reorderLevellabel = new JLabel("Reorder Level:");
        reorderLevelField = new JTextField();

        JButton submitButton = new JButton("Add Item");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO addItem();
            }
        });

        add(itemCodeLabel);
        add(itemCodeField);
        add(itemNameLabel);
    }
}
