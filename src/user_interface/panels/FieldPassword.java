package user_interface.panels;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class FieldPassword extends JPanel {
    protected JLabel fieldLabel;
    protected JPasswordField fieldText;

    public FieldPassword(String fieldName) {
        setLayout(new GridLayout(1, 2));

        // Label
        fieldLabel = new JLabel(fieldName);
        Font labelFont = new Font(fieldLabel.getFont().getName(), Font.BOLD, 15);
        fieldLabel.setFont(labelFont);
        add(fieldLabel, BorderLayout.WEST);

        // Field
        fieldText = new JPasswordField(15);
        Font textFont = new Font(fieldText.getFont().getName(), Font.PLAIN, 15);
        fieldText.setFont(textFont);
        add(fieldText, BorderLayout.EAST);

        setMaximumSize(getPreferredSize());

    }

    public String getPassword() {

        return new String(fieldText.getPassword());
    }

    public void resetField() {
        fieldText.setText("");
    }

    public void setPassword(String pass) {
        fieldText.setText(pass);
    }

    public void setEditable(boolean bool) {
        fieldText.setEditable(bool);
        fieldText.setFocusable(bool);
    }
}
