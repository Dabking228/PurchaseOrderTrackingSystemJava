package user_interface;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import backend.Backend;

public class MainMenu extends JPanel {
    private CardLayout cardLayout;
    private AccountsTable accountsTable;

    private Backend backend;
    private UserInterface userInterface;

    public MainMenu(Backend backend, UserInterface userInterface) {
        this.backend = backend;
        this.userInterface = userInterface;
        initComponents();
    }

    private void initComponents() {
        this.cardLayout = new CardLayout();
        setLayout(cardLayout);

        MainMenuPanel mainMenuPanel = new MainMenuPanel(this);
        accountsTable = new AccountsTable(backend, this);
        ItemsTable itemsTable = new ItemsTable(backend, this);
        add(accountsTable, "accountsTable");
        add(itemsTable, "itemsTable");
        add(mainMenuPanel, "mainMenuPanel");

        showPanel("mainMenuPanel");
    }

    void showPanel(String panelName) {
        cardLayout.show(this, panelName);

        Component currentPanel = this.getCurrentComponent(this);
        if (currentPanel instanceof TableRefreshable) {
            ((TableRefreshable) currentPanel).refresh();
        }
    }

    void showMainMenu() {
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
}

class MainMenuPanel extends JPanel {
    private BorderLayout layout;
    private JPanel nestedPanel;
    private JButton accountsButton;

    private TitlePanel titlePanel;

    private MainMenu mainMenu;

    public MainMenuPanel(MainMenu mainMenu) {
        this.mainMenu = mainMenu;

        createTitlePanel();
        createNestedPanel();
        createButtons();
    }

    private void createButtons() {
        accountsButton = new JButton("Accounts");
        accountsButton.addActionListener(e -> {
            mainMenu.showPanel("accountsTable");
        });

        JButton itemsButton = new JButton("Items");
        itemsButton.addActionListener(e -> {
            mainMenu.showPanel("itemsTable");
        });

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            mainMenu.logout();
        });

        nestedPanel.add(accountsButton);
        nestedPanel.add(itemsButton);
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

        titlePanel = new TitlePanel("Main Menu");
        add(titlePanel, BorderLayout.NORTH);
    }
}
