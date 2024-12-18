package user_interface;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import backend.Backend;
import data.Permission;
import data.Role;
import user_interface.MainMenu;
import user_interface.components.TitlePanel;
import user_interface.panels.BasePanel;
import user_interface.panels.POForm;
import user_interface.panels.SaleForm;
import user_interface.panels.ItemForm;
import user_interface.panels.AccountForm;
import user_interface.panels.SalesReport;
import user_interface.panels.SupplierForm;
import user_interface.tables.*;
import user_interface.panels.PRForm;;

public class MainMenu extends JPanel {
    private CardLayout cardLayout;
    Backend backend;
    private UserInterface userInterface;
    private Role role;
    private Map<String, BasePanel<?>> panels = new HashMap<>();

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

        this.createTable("Items", "itemTable", ItemTable.class);
        this.createTable("Accounts", "accountTable", AccountTable.class);
        this.createTable("Sales", "saleTable", SaleTable.class);
        this.createTable("Suppliers", "supplierTable", SupplierTable.class);
        this.createTable("PurchaseRequisition", "PRTable", PRTable.class);
        this.createTable("PurchaseOrder", "POTable", POTable.class);

        this.createFeature("salesReport", "salesReport", SalesReport.class);
        this.createTableWithFeatureCheck("trackPurchaseOrder", "trackPO", TrackPurchaseOrderTable.class);
        this.createTableWithFeatureCheck("restockItem", "restockItem", ItemRestockTable.class);

        this.createAddPanel("AddAccount", AccountForm.class);
        this.createAddPanel("AddItem", ItemForm.class);
        this.createAddPanel("AddSale", SaleForm.class);
        this.createAddPanel("AddPR", PRForm.class);
        this.createAddPanel("AddPO", POForm.class);
        this.createAddPanel("AddSupplier", SupplierForm.class);

        showPanel("mainMenuPanel");
    }

    // create panel methods
    private <T extends TablePanel<?>> void createTable(String panelName, Class<T> tableClass) {
        try {
            T tablePanel = tableClass
                    .getDeclaredConstructor(Backend.class, MainMenu.class)
                    .newInstance(backend, this);
            this.add(tablePanel, panelName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private <T extends TablePanel<?>> void createTable(String permissionName, String panelName,
            Class<T> tableClass) {
        if (!role.hasPermission(permissionName, Permission.READ)) {
            return;
        }
        createTable(panelName, tableClass);
    }

    private <T extends BasePanel<?>> void createPanel(String panelName, Class<T> panelClass) {
        try {
            T panel = panelClass
                    .getDeclaredConstructor(Backend.class, MainMenu.class)
                    .newInstance(backend, this);
            this.add(panel, panelName);
            panels.put(panelName, panel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private <T extends BasePanel<?>> void createFeature(String feature, String panelName, Class<T> panelClass) {
        if (!role.hasFeature(feature)) {
            return;
        }
        createPanel(panelName, panelClass);
    }

    private <T extends TablePanel<?>> void createTableWithFeatureCheck(String feature, String panelName,
            Class<T> panelClass) {
        if (!role.hasFeature(feature)) {
            return;
        }
        createTable(panelName, panelClass);
    }

    // TODO
    private <T extends BasePanel<?>> void createAddPanel(String panelName, Class<T> panelClass) {
        createPanel(panelName, panelClass);
    }

    // other methods
    public <T extends BasePanel<?>> T getPanel(String panelName, Class<T> panelClass) {
        BasePanel<?> panel = panels.get(panelName);
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
        createTableButton("Items", "Edit Items Table", "itemTable");
        createTableButton("Accounts", "Edit Accounts Table", "accountTable");
        createTableButton("Sales", "Edit Sales Table", "saleTable");
        createTableButton("Suppliers", "Edit Suppliers Table", "supplierTable");
        createTableButton("PurchaseRequisition", "Edit Purchase Requisition Table", "PRTable");
        createTableButton("PurchaseOrder", "Edit Purchase Order Table", "POTable");

        createFeatureButton("CreateSale", "Create Sales", "AddSale");
        createFeatureButton("salesReport", "Sales Report", "salesReport");
        createFeatureButton("trackPurchaseOrder", "Track Purchase Order", "trackPO");
        createFeatureButton("restockItem", "Restock Items", "restockItem");

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
