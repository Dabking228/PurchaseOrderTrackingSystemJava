package user_interface.panels;

import backend.Backend;
import data.Item;
import data.Sale;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Map;

import user_interface.*;

public class SaleForm extends BasePanel<Item> {
    protected FieldDropdown<Item> itemNameDrop;
    protected FieldText fieldItemStock, fieldSellQuantity, fieldSaleManager;
    private JButton confirmButton, deleteButton, editButton, editConfirm, editCancel;
    protected JPanel buttonGroup;
    private JLabel greenLabel, redLabel;
    private JPanel editcfm;

    private String rowData;
    private Item item;
    private Sale sale;
    private String panelName;

    public SaleForm(Backend backend, MainMenu parent) {
        super("Sales Form", parent, backend.db.itemsMap, backend);

        itemNameDrop = new FieldDropdown<Item>("Item Name: ", backend.db.itemsMap, new ItemList());
        itemNameDrop.fieldCombo.setSelectedIndex(-1);
        contentPanel.add(itemNameDrop);

        fieldItemStock = new FieldText("Stock: ", true);
        fieldItemStock.setEditable(false);
        contentPanel.add(fieldItemStock);

        fieldSellQuantity = new FieldText("Sell Quantity", true);
        contentPanel.add(fieldSellQuantity);

        fieldSaleManager = new FieldText("PIC");
        fieldSaleManager.setEditable(false);
        fieldSaleManager.setVisible(false);
        contentPanel.add(fieldSaleManager);

        greenLabel = new JLabel("Item added");
        greenLabel.setVisible(false);
        greenLabel.setForeground(Color.GREEN);
        greenLabel.setFont(new Font(greenLabel.getText(), Font.BOLD, 20));
        contentPanel.add(greenLabel);

        redLabel = new JLabel("Sell Quantity is more than stock or 0");
        redLabel.setVisible(false);
        redLabel.setForeground(Color.RED);
        redLabel.setFont(new Font(redLabel.getText(), Font.BOLD, 20));
        contentPanel.add(redLabel);

        backButton.addActionListener(e -> {
            editorHideOrShow(false);
            editHideOrShow(false);
            itemNameDrop.fieldCombo.setSelectedIndex(-1);
            itemNameDrop.setEditable(true);
            fieldItemStock.setData("");
            fieldSellQuantity.setData("");
            fieldSellQuantity.setEditable(true);
            confirmButton.setVisible(true);

            fieldSaleManager.setData("");
            fieldSaleManager.setVisible(false);

            if (panelName != null) {
                parent.showPanel(panelName);
                panelName = null;
            } else {
                parent.showMainMenu();
            }
        });

        itemNameDrop.fieldCombo.addActionListener(e -> {

            data.Item item = (itemNameDrop.getSelected() != null)
                    ? (data.Item) itemNameDrop.getSelected().getValue()
                    : null;
            if (item != null) {
                fieldItemStock.setData(String.valueOf(item.getStockLevel()));
            }
        });

        // Confirm Button
        confirmButton = new JButton("Create Sale");
        titleButtonPanel.add(confirmButton);
        confirmButton.addActionListener(e -> {
            try {
                String itemID = itemNameDrop.getSelected().getValue().getId();
                int itemStock = fieldItemStock.getIntData();
                int sellQuantity = fieldSellQuantity.getIntData();

                if (sellQuantity < itemStock && sellQuantity != 0) {
                    backend.db.addSale(
                            new Sale(itemID, sellQuantity, new java.util.Date(), backend.getCurrentAccount().getId()));
                    backend.db.itemsMap.get(itemID).setStockLevel(itemStock - sellQuantity);
                    greenLabel.setVisible(true);

                    itemNameDrop.fieldCombo.setSelectedIndex(-1);
                    fieldItemStock.setData("");
                    fieldSellQuantity.setData("");

                    Timer timerG = new Timer(2000, g -> {
                        greenLabel.setVisible(false);
                        parent.showMainMenu();
                    });
                    timerG.setRepeats(false);
                    timerG.start();
                } else {
                    fieldSellQuantity.setData("");
                    redLabel.setVisible(true);

                    Timer timerR = new Timer(2000, g -> redLabel.setVisible(false));
                    timerR.setRepeats(false);
                    timerR.start();
                }
            } catch (Exception err) {
                System.out.println(err);
            }

        });

        // edit delete button
        deleteButton = new JButton("Delete");
        deleteButton.setVisible(false);
        editButton = new JButton("Edit");
        editButton.setVisible(false);
        titleButtonPanel.add(deleteButton);
        titleButtonPanel.add(editButton);

        editButton.addActionListener(e -> {
            editcfm.setVisible(true);
            fieldSellQuantity.setEditable(true);
        });

        deleteButton.addActionListener(e -> {
            Item item = backend.db.itemsMap.get(sale.getItemId());
            int itemsold = sale.getQuantitySold();

            itemNameDrop.fieldCombo.setSelectedIndex(-1);
            fieldItemStock.setData("");
            fieldSellQuantity.setData("");
            fieldSaleManager.setData("");

            item.setStockLevel(item.getStockLevel() + itemsold);
            backend.db.salesMap.remove(sale.getId());
            parent.showPanel("saleTable");
        });

        editcfm = new JPanel(new GridLayout(1, 2));
        editConfirm = new JButton("Confirm");
        editCancel = new JButton("Cancel");
        editcfm.add(editConfirm);
        editcfm.add(editCancel);
        editcfm.setMaximumSize(new Dimension(300, 30));
        editcfm.setVisible(false);
        contentPanel.add(editcfm);

        editConfirm.addActionListener(e -> {
            try {
                Item item = backend.db.itemsMap.get(sale.getItemId());
                int oldSellQuantity = sale.getQuantitySold();
                int newSellQuantity = fieldSellQuantity.getIntData();

                if (item.getStockLevel() + oldSellQuantity > newSellQuantity) {
                    item.setStockLevel(item.getStockLevel() + oldSellQuantity - newSellQuantity);
                    sale.setQuantitySold(newSellQuantity);

                    fieldItemStock.setData(String.valueOf(item.getStockLevel()));
                    editcfm.setVisible(false);
                } else {

                    fieldSellQuantity.setEditable(false);
                    fieldSellQuantity.setData(String.valueOf(sale.getQuantitySold()));
                    editcfm.setVisible(false);
                }

            } catch (Exception err) {
                fieldSellQuantity.setEditable(false);
                fieldSellQuantity.setData(String.valueOf(sale.getQuantitySold()));

                editcfm.setVisible(false);
                System.out.println(err);
            }
        });

        editCancel.addActionListener(e -> {
            fieldSellQuantity.setEditable(false);
            fieldSellQuantity.setData(String.valueOf(sale.getQuantitySold()));

            editcfm.setVisible(false);
        });
    }

    void editHideOrShow(boolean bool) {
        editcfm.setVisible(bool);
    }

    void editorHideOrShow(boolean bool) {
        deleteButton.setVisible(bool);
        editButton.setVisible(bool);
    }

    public void viewUpdate() {
        editorHideOrShow(true);
    }

    public void viewOnly() {
        // createHideOrShow(false);
        editorHideOrShow(false);
        editHideOrShow(false);
        // approveHideOrSHow(false);

        itemNameDrop.setEditable(false);
        fieldItemStock.setEditable(false);
        fieldSellQuantity.setEditable(false);
        fieldSaleManager.setVisible(true);
        confirmButton.setVisible(false);
    }

    public void setData() {
        // PR = backend.db.getPurchaseRequisition(rowData);
        sale = backend.db.getSale(rowData);
        item = backend.db.getItem(sale.getItemId());

        itemNameDrop.setData(item.getId());
        fieldItemStock.setData(String.valueOf(item.getStockLevel()));
        fieldSellQuantity.setData(String.valueOf(sale.getQuantitySold()));
        fieldSaleManager.setData(sale.getSalesManagerId());

    }

    public void setRowNum(String data) {
        this.rowData = data;
    }

    public void setBack(String PanelName) {
        this.panelName = PanelName;
    }
}

class ItemList extends ComboList<data.Item> {
    @Override
    public void setItem(Map<String, data.Item> items) {
        this.items = items;
    }

    @Override
    public String getName(String UUID) {
        return this.items.get(UUID).getItemName();
    }

    @SuppressWarnings("unlikely-arg-type")
    @Override
    public String toString() {
        return this.items.get(UUID).getId();
    }

    @Override
    public void setValue() {
        this.values = new data.Item[this.items.size()];
        initGetValue();
    }

    @Override
    public data.Item getObject(Object ItemName) {
        for (data.Item item : items.values()) {
            if (item.getItemName() == String.valueOf(ItemName)) {
                return item;
            }
        }
        return null;
    }
}
