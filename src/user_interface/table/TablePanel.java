package user_interface.table;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import backend.Backend;
import data.BaseItem;
import user_interface.MainMenu;
import user_interface.components.ButtonColumn;
import user_interface.components.TitlePanel;

abstract class TablePanel<T extends BaseItem> extends JPanel implements TableRefreshable {
    protected JPanel panel, titleButtonPanel;
    protected JTable itemsTable;
    protected int buttonColumnIndex;
    protected TablePanelModel<T> tableModel;
    protected Map<String, T> items;
    protected Backend backend;

    // Create the UI
    public TablePanel(String title, int buttonColumnIndex, MainMenu parent, Map<String, T> items,
            TablePanelModel<T> tableModel, Backend backend) {
        this.tableModel = tableModel;
        this.items = items;
        this.backend = backend;

        this.buttonColumnIndex = buttonColumnIndex;
        setLayout(new BorderLayout());

        // Title
        JPanel titlePanel = new TitlePanel(title);
        add(titlePanel, BorderLayout.NORTH);

        // Title button panel
        titleButtonPanel = new JPanel(new FlowLayout());
        titlePanel.add(titleButtonPanel, BorderLayout.EAST);

        // Refresh table button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> {
            refresh();
        });
        titleButtonPanel.add(refreshButton, BorderLayout.EAST);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            parent.showMainMenu();
        });
        titleButtonPanel.add(backButton, 0);

        // Table of items
        itemsTable = new JTable();
        itemsTable.setModel(tableModel);

        itemsTable.setRowHeight(25);

        Action goToItem = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                int modelRow = Integer.valueOf(e.getActionCommand());
                itemButtonAction(modelRow);
                refresh();
            }
        };

        ButtonColumn buttonColumn = new ButtonColumn(itemsTable, goToItem, buttonColumnIndex);
        buttonColumn.setMnemonic(KeyEvent.VK_D);

        JScrollPane scrollPane = new JScrollPane(itemsTable);
        itemsTable.setFillsViewportHeight(true);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void refresh() {
        ArrayList<T> array = new ArrayList<>(items.values());
        tableModel.setItems(array);
    }

    abstract public void itemButtonAction(int modelRow);
}

abstract class TablePanelModel<T extends BaseItem> extends AbstractTableModel {
    protected ArrayList<T> items = new ArrayList<>();

    public void setItems(ArrayList<T> items) {
        this.items = items;
        fireTableDataChanged();
    }
}
