package user_interface;

import javax.swing.JOptionPane;
import backend.Backend;

public class Login extends javax.swing.JPanel {
    Backend backend;

    public Login() {
        initComponents();
    }

    public Login(Backend backend) {
        this.backend = backend;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        username = new javax.swing.JLabel();
        Usernametxt = new javax.swing.JTextField();
        password = new javax.swing.JLabel();
        Passwordtxt = new javax.swing.JPasswordField();
        loginButts = new javax.swing.JLabel();
        LoginBTN = new javax.swing.JButton();

        username.setFont(new java.awt.Font("Dialog", 0, 48));
        username.setText("Please Login Here");

        password.setText("Username: ");

        loginButts.setText("Password: ");

        LoginBTN.setText("Login");
        LoginBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(178, 178, 178)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(username, javax.swing.GroupLayout.DEFAULT_SIZE, 463,
                                                        Short.MAX_VALUE)
                                                .addGap(193, 193, 193))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(Usernametxt,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 263,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                64, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(LoginBTN, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(Passwordtxt,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 263,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(loginButts,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 64,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 0, Short.MAX_VALUE)))));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(91, 91, 91)
                                .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 52,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 24,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Usernametxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(loginButts, javax.swing.GroupLayout.PREFERRED_SIZE, 24,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Passwordtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(LoginBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 47,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(188, Short.MAX_VALUE)));
    }

    private void LoginBTNActionPerformed(java.awt.event.ActionEvent evt) {
        String username = Usernametxt.getText();
        String password = new String(Passwordtxt.getPassword());

        if (validateLogin(username, password)) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            // TODO open main menu

        } else {
            JOptionPane.showMessageDialog(this, "Invalid Login! Try again.");
        }
    }

    private boolean validateLogin(String username, String password) {
        if (backend == null) {
            return false;
        }

        return backend.login(username, password);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    private javax.swing.JButton LoginBTN;
    private javax.swing.JPasswordField Passwordtxt;
    private javax.swing.JTextField Usernametxt;
    private javax.swing.JLabel username;
    private javax.swing.JLabel password;
    private javax.swing.JLabel loginButts;
}
