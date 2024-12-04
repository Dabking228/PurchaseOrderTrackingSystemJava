package user_interface;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import user_interface.add_item_dialog.AddNewItem;
import backend.Backend;
import data.Permission;
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

        MainMenuPanel mainMenuPanel = new MainMenuPanel(backend, this);
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

    private Role role;

    public MainMenuPanel(Backend backend, MainMenu mainMenu) {
        this.mainMenu = mainMenu;

        createTitlePanel();
        createNestedPanel();
        this.role = backend.getCurrentAccount().getRole();
        createButtons();
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

    private void createButtons() {
        createTableButton("itemsTable", "Edit Items Table", "itemsTable");
        createTableButton("accountsTable", "Edit Accounts Table", "accountsTable");
        createTableButton("salesTable", "Edit Sales Table", "salesTable");
        createTableButton("suppliersTable", "Edit Suppliers Table", "suppliersTable");
        createTableButton("purchaseRequisitionTable", "Edit Purchase Requisition Table", "purchaseRequisitionTable");
        createTableButton("purchaseOrdersTable", "Edit Purchase Order Table", "purchaseOrdersTable");

        createFeatureButton("stockEntry", "Stock Entry", "stockEntry");
        createFeatureButton("salesReport", "Sales Report", "salesReport");
        createFeatureButton("trackPurchaseOrder", "Track Purchase Order", "trackPurchaseOrder");

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            mainMenu.logout();
        });
        nestedPanel.add(logoutButton);
    }

    private void createTableButton(String tableName, String buttonTitle, String panelName) {
        if (role.hasPermission(tableName, Permission.READ)) {
            JButton button = new JButton(buttonTitle);
            button.addActionListener(e -> {
                mainMenu.showPanel(panelName);
            });
            nestedPanel.add(button);
        }
    }

    private void createFeatureButton(String feature, String buttonTitle, String panelName) {
        if (role.hasFeature(feature)) {
            JButton button = new JButton(buttonTitle);
            button.addActionListener(e -> {
                mainMenu.showPanel(panelName);
            });
            nestedPanel.add(button);
        }
    }
}
