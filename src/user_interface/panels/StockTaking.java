package user_interface.panels;


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
public class StockTaking extends Panel<Item>{
    public StockTaking(Backend backend, MainMenu parent){
        super("Daily Stock Taking", parent, backend.db.itemsMap, backend);

        FieldText fieldItemID = new FieldText("Item ID");
        contentPanel.add(fieldItemID);

        // JPanel contents = new JPanel();
        // contents.setLayout(new BoxLayout(contents,BoxLayout.PAGE_AXIS));

        FieldText fieldSupplierName = new FieldText("Supplier Name");
        contentPanel.add(fieldSupplierName);

        FieldText fieldItemName = new FieldText("Item Name");
        contentPanel.add(fieldItemName);

        FieldText fieldNumStock = new FieldText("Stock", true);
        contentPanel.add(fieldNumStock);

        FieldText fieldMinStock = new FieldText("Minimum Stock", true);
        contentPanel.add(fieldMinStock);

        // contentPanel.add(contents);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener((e -> {

        }));

        titleButtonPanel.add(confirmButton,1);
    }
}
