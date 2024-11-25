package data;

import javax.swing.*;

import backend.Backend;
import backend.Database;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import user_interface.MainMenu;  

public class AddNewItem extends JDialog {
    private JTextField nameField;
    private JTextField descriptionField; 
    private JTextField priceField;
    private JTextField quantityField;
    private JButton submitButton;
    private Connection connection;

    public AddNewItem(Backend backend, MainMenu parent) {
        super((JFrame) SwingUtilities.getWindowAncestor(parent), "Add New Item", true);
        this.connection = backend.getDatabase().getConnection();
        setLayout(new GridLayout(5, 2, 10, 10));
        
        setLayout(new GridLayout(5, 2, 10, 10));
        
        setLayout(new GridLayout(5, 2, 10, 10));
        
        nameField = new JTextField(20);
        descriptionField = new JTextField(20);
        priceField = new JTextField(10);
        quantityField = new JTextField(10);
        
        add(new JLabel("Item Name:"));
        add(nameField);
        add(new JLabel("Description:"));
        add(descriptionField);
        add(new JLabel("Price:"));
        add(priceField);
        add(new JLabel("Quantity:"));
        add(quantityField);
        
        submitButton = new JButton("Add Item");
        submitButton.addActionListener(e -> addItemToDatabase());
        add(submitButton);

        pack();
        setLocationRelativeTo(parent);
    }

    private void addItemToDatabase() {
        try {
            String sql = "INSERT INTO items (name, description, price, quantity) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            
            stmt.setString(1, nameField.getText());
            stmt.setString(2, descriptionField.getText());
            stmt.setDouble(3, Double.parseDouble(priceField.getText()));
            stmt.setInt(4, Integer.parseInt(quantityField.getText()));
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Item added successfully!");
            nameField.setText("");
            descriptionField.setText("");
            priceField.setText("");
            quantityField.setText("");
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error adding item: " + ex.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for price and quantity",
                                        "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}