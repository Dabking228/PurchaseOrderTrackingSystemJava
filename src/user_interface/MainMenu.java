package user_interface;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import user_interface.add_item_dialog.AddNewItem;
import backend.Backend;
import data.Role;
import user_interface.table.*;
import user_interface.MainMenu;
import user_interface.components.TitlePanel;

public class MainMenu extends JPanel {
    private CardLayout cardLayout;
    Backend backend;
    private UserInterface userInterface;
    private Role userRole;

    public MainMenu(Backend backend, UserInterface userInterface, Role userRole) {
        this.backend = backend;
        this.userInterface = userInterface;
        this.userRole = userRole;
        initComponents();
    }

    private void initComponents() {
        this.cardLayout = new CardLayout();
        setLayout(cardLayout);

        MainMenuPanel mainMenuPanel = new MainMenuPanel(this);
        AccountsTable accountsTable = new AccountsTable(backend, this);
        ItemsTable itemsTable = new ItemsTable(backend, this);
        SalesTable salesTable = new SalesTable(backend, this);
        SuppliersTable suppliersTable = new SuppliersTable(backend, this);
        PurchaseRequisitionTable purchaseRequisitionTable = new PurchaseRequisitionTable(backend, this);
        PurchaseOrdersTable purchaseOrdersTable = new PurchaseOrdersTable(backend, this);
        AddNewItem addNewItem = new AddNewItem(backend);
        add(accountsTable, "accountsTable");
        add(itemsTable, "itemsTable");
        add(mainMenuPanel, "mainMenuPanel");
        add(salesTable, "salesTable");
        add(suppliersTable, "suppliersTable");
        add(purchaseRequisitionTable, "purchaseRequisitionTable");
        add(purchaseOrdersTable, "purchaseOrdersTable");
        add(addNewItem, "addNewItem");

        showPanel("mainMenuPanel");
    }

    void showPanel(String panelName) {
        cardLayout.show(this, panelName);

        Component currentPanel = this.getCurrentComponent(this);
        if (currentPanel instanceof TableRefreshable) {
            ((TableRefreshable) currentPanel).refresh();
        }
    }

    public void showMainMenu() {
        showPanel("mainMenuPanel");
    }

    void logout() {
        backend.logout();
        userInterface.showPanel("login");
    }

    // WONTFIX i don't think there is any other way to do this
    private Component getCurrentComponent(JPanel panel) {
        for (Component component : panel.getComponents()) {
            if (component.isVisible()) {
                return component;
            }
        }
        return null;
    }

    void handleRoleBasedActions() {
        if (userRole == Role.ADMIN) {
        } else if (userRole == Role.FINANCE_MANAGER) {
        } else if (userRole == Role.INVENTORY_MANAGER) {
        } else if (userRole == Role.SALES_MANAGER) {
        }
    }

}

class MainMenuPanel extends JPanel {
    private BorderLayout layout;
    private JPanel nestedPanel;

    private TitlePanel titlePanel;

    private MainMenu mainMenu;

    public MainMenuPanel(MainMenu mainMenu) {
        this.mainMenu = mainMenu;

        createTitlePanel();
        createNestedPanel();
        createButtons();
    }

    private void createButtons() {
        JButton accountsButton = new JButton("Edit Accounts Table");
        accountsButton.addActionListener(e -> {
            mainMenu.showPanel("accountsTable");
        });

        JButton itemsButton = new JButton("Edit Items Table");
        itemsButton.addActionListener(e -> {
            mainMenu.showPanel("itemsTable");
        });

        JButton salesButton = new JButton("Edit Sales Table");
        salesButton.addActionListener(e -> {
            mainMenu.showPanel("salesTable");
        });

        JButton suppliersButton = new JButton("Edit Suppliers Table");
        suppliersButton.addActionListener(e -> {
            mainMenu.showPanel("suppliersTable");
        });

        JButton purchaseRequisitionButton = new JButton("Edit Purchase Requisition Table");
        purchaseRequisitionButton.addActionListener(e -> {
            mainMenu.showPanel("purchaseRequisitionTable");
        });

        JButton purchaseOrderButton = new JButton("Edit Purchase Order Table");
        purchaseOrderButton.addActionListener(e -> {
            mainMenu.showPanel("purchaseOrdersTable");
        });

        JButton stockEntryButton = new JButton("Stock Entry");
        stockEntryButton.addActionListener(e -> {
            // TODO
            throw new UnsupportedOperationException("Not implemented yet");
        });

        JButton salesReportButton = new JButton("Sales Report");
        salesReportButton.addActionListener(e -> {
            // TODO
            throw new UnsupportedOperationException("Not implemented yet");
        });

        JButton purchaseOrderUIButton = new JButton("Purchase Order");
        purchaseOrderUIButton.addActionListener(e -> {
            // TODO
            throw new UnsupportedOperationException("Not implemented yet");
        });

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            mainMenu.logout();
        });

        JButton addNewItemButton = new JButton("Add New Item"); // Add this button
        addNewItemButton.addActionListener(e -> {
            mainMenu.showPanel("addNewItem");
        });

        nestedPanel.add(itemsButton);
        nestedPanel.add(salesButton);
        nestedPanel.add(addNewItemButton);
        nestedPanel.add(suppliersButton);
        nestedPanel.add(accountsButton);
        nestedPanel.add(purchaseRequisitionButton);
        nestedPanel.add(purchaseOrderButton);
        nestedPanel.add(stockEntryButton);
        nestedPanel.add(salesReportButton);
        nestedPanel.add(purchaseOrderUIButton);
        nestedPanel.add(logoutButton);
    }

    private void createNestedPanel() {
        nestedPanel = new JPanel(new GridLayout(0, 2, 20, 20));
        nestedPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(nestedPanel);
    }

    private void createTitlePanel() {
        layout = new BorderLayout();
        setLayout(layout);

        titlePanel = new TitlePanel("Welcome, " + mainMenu.backend.getCurrentAccount().getUsername());
        add(titlePanel, BorderLayout.NORTH);
    }
}
