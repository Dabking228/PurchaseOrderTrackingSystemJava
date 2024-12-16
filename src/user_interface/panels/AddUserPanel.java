package user_interface.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import backend.Backend;
import data.Account;
import data.Permission;
import data.Role;
import user_interface.MainMenu;

public class AddUserPanel extends Panel<Account> {
    protected FieldText fieldUsername;
    protected FieldPassword fieldPassword;
    protected JLabel greenLabel, roleLabel;
    protected JComboBox<String> roleComboBox;
    private Role role;
    protected Account account;
    private AccountList accountList;
    private String rowData;
    private JButton editConfirm, editCancel, deleteButton, editButton, confirmButton;
    private JPanel editcfm;

    public AddUserPanel(Backend backend, MainMenu parent) {
        super("Add User", parent, backend.db.accountsMap, backend);
        this.accountList = new AccountList();
        role = backend.getCurrentAccount().getRole();

        fieldUsername = new FieldText("Username:");
        contentPanel.add(fieldUsername);

        fieldPassword = new FieldPassword("Password");
        contentPanel.add(fieldPassword);

        // Creating Panel for Roles
        JPanel rolePanel = new JPanel(new GridLayout(1, 2));

        roleLabel = new JLabel("Role:");
        Font labelFont = new Font(roleLabel.getFont().getName(), Font.BOLD, 15);
        roleLabel.setFont(labelFont);
        rolePanel.add(roleLabel);

        String[] roles = { "Admin", "Finance Manager", "Inventory Manager", "Purchase Manager", "Sales Manager" };
        roleComboBox = new JComboBox<>(roles);
        rolePanel.add(roleComboBox);
        rolePanel.setMaximumSize(new Dimension(getPreferredSize().width, 30));
        contentPanel.add(rolePanel);
        // End Creating Panel for roles

        greenLabel = new JLabel("Account added");
        greenLabel.setVisible(false);
        greenLabel.setForeground(Color.GREEN);
        greenLabel.setFont(new Font(greenLabel.getText(), Font.BOLD, 20));
        contentPanel.add(greenLabel);

        backButton.addActionListener(e -> {
            parent.showPanel("accountsTable");
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
            editcfm.setVisible(false);
            try {
                String username = fieldUsername.getData();
                String password = fieldPassword.getPassword();
                String roleString = (String) roleComboBox.getSelectedItem();
                Role covertedRole = getRoleFromString(roleString);

                if (!username.equals(account.getUsername())) {
                    System.out.println("username changed"); // TODO: remove
                    backend.db.accountsMap.get(account.getId()).setUsername(username);
                }
                if (!password.equals(account.getPassword())) {
                    System.out.println("pass changed"); // TODO: remove
                    account.setPassword(password);
                }
                if (covertedRole  != account.getRole()) {
                    System.out.println("role changed"); // TODO: remove
                    account.setRole(getRoleFromString(roleString));
                }

                System.out.println("item updated"); // TODO: remove
                fieldUsername.setEditable(false);
                fieldPassword.setEditable(false);
                roleComboBox.setEnabled(false);
                System.out.println(account.getUsername());
                System.out.println(account.getPassword());
                System.out.println(account.getRole());
                editcfm.setVisible(false);
            } catch (Exception err) {
                System.out.println(err);
            }

        });

        editCancel.addActionListener(e -> {
            fieldUsername.setEditable(false);
            fieldPassword.setEditable(false);
            roleComboBox.setEnabled(false);

            fieldUsername.setData(account.getUsername());
            fieldPassword.setPassword(account.getPassword());
            roleComboBox.setSelectedItem(getRoleString(account.getRole()));
            editcfm.setVisible(false);
        });

        deleteButton = new JButton("Delete");
        editButton = new JButton("Edit");
        titleButtonPanel.add(deleteButton);
        titleButtonPanel.add(editButton);

        deleteButton.addActionListener(e -> {
            backend.db.accountsMap.remove(accountList.getObjectUUID(account.getUsername()));
            parent.showPanel("accountsTable");
        });

        editButton.addActionListener(e -> {
            editcfm.setVisible(true);
            fieldPassword.setEditable(true);
            roleComboBox.setEnabled(true);
        });
        
        // init item lists
        accountList.setItem(items);
        accountList.setValue();
        // If is a view only
    

        confirmButton = new JButton("Add User");
        confirmButton.addActionListener(e -> {
            // Logic to add the user
            try {
                String username = fieldUsername.getData();
                String password = fieldPassword.getPassword();
                String roleString = (String) roleComboBox.getSelectedItem();
                // Ad thisser to the backend

                if (!username.isEmpty() || !password.isEmpty()) {
                    
                    backend.db.addAccount(new Account(username, password, getRoleFromString(roleString)));
                    greenLabel.setVisible(true);
                    System.out.println("Adding user: " + username + ":" + password + ":" + getRoleFromString(roleString)); // TODO: Remove

                    // Reset field and data
                    fieldUsername.resetField();
                    fieldPassword.resetField();
                    role = null;

                    // timer for showing green label
                    Timer timer = new Timer(2000, g -> greenLabel.setVisible(false));
                    timer.setRepeats(false);
                    timer.start();
                }
            } catch (Exception err) {
                System.out.println(err);
            }

        });
        titleButtonPanel.add(confirmButton);

    }

    void createHideOrShow(boolean bool){
        confirmButton.setVisible(bool);
    }

    void editorHideOrShow(boolean bool){
        deleteButton.setVisible(bool);
        editButton.setVisible(bool);
    }
    
    void editHideOrShow(boolean bool){
        editcfm.setVisible(bool);
    }

    public void CreateUser(){
        createHideOrShow(true);
        editorHideOrShow(false);
        editHideOrShow(false);
    }

    public void viewOnly(){
        createHideOrShow(false);
        editorHideOrShow(false);
        editHideOrShow(false);

        fieldUsername.setEditable(false);
        fieldPassword.setEditable(false);
        roleComboBox.setEnabled(false);
    }

    public void viewUpdate(){
        createHideOrShow(false);
        editorHideOrShow(true);
        editHideOrShow(false);
    }

    public void setData() {
        System.out.println(rowData); // TODO: Remove
        account = backend.db.getAccount(rowData);
        System.out.println("yay"); // TODO: Remove
        System.out.println(account.getPassword()); // TODO: Remove

        fieldUsername.setData(account.getUsername());
        fieldPassword.setPassword(account.getPassword());
        roleComboBox.setSelectedItem(getRoleString(account.getRole()));
        
    }

    public void setRowNum(String data) {
        this.rowData = data;
    }

    String getRoleString(Role role) {
        switch (role) {
            case ADMIN:
                return "Admin";
            case FINANCE_MANAGER:
                return "Finance Manager";
            case INVENTORY_MANAGER:
                return "Inventory Manager";
            case PURCHASE_MANAGER:
                return "Purchase Manager";
            case SALES_MANAGER:
                return "Sales Manager";
            default:
                throw new IllegalArgumentException("Unknown role: " + role);
        }
    }

    Role getRoleFromString(String role){
        switch (role) {
            case "Admin":
                return Role.ADMIN;
            case "Finance Manager":
                return Role.FINANCE_MANAGER;
            case "Inventory Manager":
                return Role.INVENTORY_MANAGER;
            case "Purchase Manager":
                return Role.PURCHASE_MANAGER;            
            case "Sales Manager":
                return Role.SALES_MANAGER;
            default:
                throw new IllegalArgumentException("Unknown role: " + role);
        }
    }
}

class AccountList extends ComboList<Account> {
    @Override
    public void setItem(Map<String, Account> items) {
        this.items = items;
    }

    @Override
    public void setValue() {
        this.values = new Account[this.items.size()];
        initGetValue();
    }

    @Override
    public Account getObject(Object ItemName) {
        for (Account item : items.values()) {
            if (item.getUsername() == String.valueOf(ItemName)) {
                return item;
            }
        }
        return null;
    }

    public String getObjectUUID(Object ItemCode) {
        for (Map.Entry<String, Account> item : items.entrySet()) {
            if (item.getValue().getUsername() == String.valueOf(ItemCode)) {
                return item.getKey();
            }
        }
        return null;
    }
}