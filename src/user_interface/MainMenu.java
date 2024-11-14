package user_interface;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.*;

import backend.Backend;

public class MainMenu extends JPanel {
    private CardLayout cardLayout;
    private AccountsTable accountsTable;
    private JPanel currentPanel;

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

        MainMenuPanel mainMenuPanel = new MainMenuPanel(backend, this);
        accountsTable = new AccountsTable(backend);
        add(accountsTable, "accountsTable");
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
    private Backend backend;

    public MainMenuPanel(Backend backend, MainMenu mainMenu) {
        this.backend = backend;
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

        nestedPanel.add(accountsButton);
    }

    private void createNestedPanel() {
        nestedPanel = new JPanel(new FlowLayout());
        add(nestedPanel, BorderLayout.CENTER);
    }

    private void createTitlePanel() {
        layout = new BorderLayout();
        setLayout(layout);

        titlePanel = new TitlePanel("Main Menu");
        add(titlePanel, BorderLayout.NORTH);
    }
}
