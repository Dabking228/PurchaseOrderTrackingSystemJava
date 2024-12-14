package user_interface.panels;

import backend.Backend;
import data.*;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import user_interface.MainMenu;
public class StockTaking extends Panel<Item>{
    public StockTaking(Backend backend, MainMenu parent){
        super("Daily Stock Taking", parent, backend.db.itemsMap, backend);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));

        FieldText fieldItemID = new FieldText("Item ID");
        contentPanel.add(fieldItemID);
        FieldText fieldSupplierName = new FieldText("Supplier Name");
        contentPanel.add(fieldSupplierName);

        FieldText fieldItemName = new FieldText("Item Name");
        contentPanel.add(fieldItemName);

        FieldText fieldNumStock = new FieldText("Stock", true);
        contentPanel.add(fieldNumStock);

        FieldText fieldMinStock = new FieldText("Minimum Stock", true);
        contentPanel.add(fieldMinStock);

        this.add(contentPanel, BorderLayout.CENTER);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener((e -> {

        }));

        titleButtonPanel.add(confirmButton,1);
    }
}
