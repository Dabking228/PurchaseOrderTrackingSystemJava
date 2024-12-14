package user_interface;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import user_interface.add_item_dialog.AddNewItem;
import backend.Backend;
import data.Permission;
import data.Role;
import user_interface.table.*;
import user_interface.MainMenu;
import user_interface.panels.Panel;
import user_interface.panels.ItemPanel;
import user_interface.panels.AddNewUser;
import user_interface.panels.StockTaking;
import user_interface.panels.TitlePanel;

public class MainMenu extends JPanel {
    private CardLayout cardLayout;
    Backend backend;
    private UserInterface userInterface;
    private Role role;
    private Map<String, Panel<?>> panels = new HashMap<>();

    public MainMenu(Backend backend, UserInterface userInterface) {
        this.backend = backend;
        this.userInterface = userInterface;
        this.role = backend.getRole();
        initComponents();
    }

    private void initComponents() {
        this.cardLayout = new CardLayout();
        setLayout(cardLayout);

        MainMenuPanel mainMenuPanel = new MainMenuPanel(backend, this);
        this.add(mainMenuPanel, "mainMenuPanel");

        this.createTablePanel("Items", "itemsTable", ItemsTable.class);
        this.createTablePanel("Accounts", "accountsTable", AccountsTable.class);
        this.createTablePanel("Sales", "salesTable", SalesTable.class);
        this.createTablePanel("Suppliers", "suppliersTable", SuppliersTable.class);
        this.createTablePanel("PurchaseRequisition", "purchaseRequisitionTable", PurchaseRequisitionTable.class);
        this.createTablePanel("PurchaseOrder", "purchaseOrdersTable", PurchaseOrdersTable.class);

        // this.createFeaturePanel("stockEntry", "stockEntry", AddNewItem.class);
        this.createFeaturePanel("addItem", "itemPanel", ItemPanel.class);
        this.createFeaturePanelViewOnly("viewItem", "itemPanelView", ItemPanel.class, true);
        // TODO the other panels

        showPanel("mainMenuPanel");
    }

    <T extends TablePanel<?>> void createTablePanel(String permissionName, String panelName, Class<T> tableClass) {
        if (!role.hasPermission("Items", Permission.READ)) {
            return;
        }

        try {
            T tablePanel = tableClass
                    .getDeclaredConstructor(Backend.class, MainMenu.class)
                    .newInstance(backend, this);
            this.add(tablePanel, panelName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    <T extends Panel<?>> void createFeaturePanel(String feature, String panelName, Class<T> panelClass) {
        if (!role.hasFeature(feature)) {
            return;
        }

        try {
            T panel = panelClass
                    .getDeclaredConstructor(Backend.class, MainMenu.class)
                    .newInstance(backend, this);
            this.add(panel, panelName);
            panels.put(panelName,panel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    <T extends Panel<?>> void createFeaturePanelViewOnly(String feature, String panelName, Class<T> panelClass, boolean viewOnly) {
        if (!role.hasFeature(feature)) {
            return;
        }

        try {
            T panel = panelClass
                    .getDeclaredConstructor(Backend.class, MainMenu.class, boolean.class)
                    .newInstance(backend, this, viewOnly);
            this.add(panel, panelName);
            panels.put(panelName, panel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T extends Panel<?>> T getPanel(String panelName, Class<T> panelClass){
       Panel<?> panel = panels.get(panelName);
        if (panelClass.isInstance(panel)) {
            return (T) panel;
        } else {
            throw new IllegalArgumentException("Panel not of expected type: " + panelClass.getName());
        }
    }

    public void showPanel(String panelName) {
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
        if (role == Role.ADMIN) {
        } else if (role == Role.FINANCE_MANAGER) {
        } else if (role == Role.INVENTORY_MANAGER) {
        } else if (role == Role.SALES_MANAGER) {
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
        this.role = backend.getRole();
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
        createTableButton("Items", "Edit Items Table", "itemsTable");
        createTableButton("Accounts", "Edit Accounts Table", "accountsTable");
        createTableButton("Sales", "Edit Sales Table", "salesTable");
        createTableButton("Suppliers", "Edit Suppliers Table", "suppliersTable");
        createTableButton("PurchaseRequisition", "Edit Purchase Requisition Table", "purchaseRequisitionTable");
        createTableButton("PurchaseOrder", "Edit Purchase Order Table", "purchaseOrdersTable");

        createFeatureButton("stockEntry", "Stock Entry", "stockEntry");
        createFeatureButton("salesReport", "Sales Report", "salesReport");
        createFeatureButton("trackPurchaseOrder", "Track Purchase Order", "trackPurchaseOrder");

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            mainMenu.logout();
        });
        nestedPanel.add(logoutButton);
    }

    private void createTableButton(String permissionName, String buttonTitle, String panelName) {
        if (role.hasPermission(permissionName, Permission.READ)) {
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
