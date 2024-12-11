package user_interface.panels;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import backend.Backend;
import data.Account;
import user_interface.MainMenu;

public class AddNewUser extends Panel<Account> {
    public AddNewUser(Backend backend, MainMenu parent) {
        super("Add User", parent, backend.db.accountsMap,backend);

        FieldText fieldUsername = new FieldText("Username:");
        // usernameLabel.setBounds(10, 10, 100, 25);
        contentPanel.add(fieldUsername);

        FieldPassword fieldPassword = new FieldPassword("Password");
        contentPanel.add(fieldPassword);

        // JLabel roleLabel = new JLabel("Role:");
        // roleLabel.setBounds(10, 50, 100, 25);
        // add(roleLabel);

        // String[] roles = {"Admin", "Finance Manager", "Inventory Manager", "Purchase Manager", "Sales Manager"};
        // JComboBox<String> roleComboBox = new JComboBox<>(roles);
        // roleComboBox.setBounds(120, 50, 150, 25);
        // add(roleComboBox);

        // JButton confirmButton = new JButton("Add User");
        // confirmButton.setBounds(10, 90, 120, 25);
        // confirmButton.addActionListener(e -> {
        //     // Logic to add the user
        //     char[] username = fieldPassword.getPassword();
        //     String role = (String) roleComboBox.getSelectedItem();
        //     // Ad thisser to the backend
        //     System.out.println("Adding user: " + username + " with role: " + role);
        // });
        // add(confirmButton);
    }
}