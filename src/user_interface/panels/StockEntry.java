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

        String itemCode = JOptionPane.showInputDialog("Enter Item Code:");
        String itemName = JOptionPane.showInputDialog("Enter Item Name:");
        String supplierId = JOptionPane.showInputDialog("Enter Supplier ID:");
        String stockLevelStr = JOptionPane.showInputDialog("Enter Stock Level:");
        String reorderLevelStr = JOptionPane.showInputDialog("Enter Reorder Level:");

        int stockLevel = Integer.parseInt(stockLevelStr);
        int reorderLevel = Integer.parseInt(reorderLevelStr);

        return new Item(itemCode, itemName, supplierId, stockLevel, reorderLevel);
    }

}
