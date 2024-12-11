package user_interface.panels;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class FieldPassword extends JPanel{
    protected JLabel fieldLabel;
    protected JPasswordField fieldText;

    public FieldPassword(String fieldName) {
        setLayout(new GridLayout(1,2));

        // Label
        fieldLabel = new JLabel(fieldName);
        Font labelFont = new Font(fieldLabel.getFont().getName(), Font.BOLD, 15);
        fieldLabel.setFont(labelFont);
        add(fieldLabel,BorderLayout.WEST);       

        // Field
        fieldText = new JPasswordField(15);
        Font textFont = new Font(fieldText.getFont().getName(), Font.PLAIN, 15);
        fieldText.setFont(textFont);
        add(fieldText,BorderLayout.EAST);

        setMaximumSize(getPreferredSize());

    }

    public String getPassword(){
        
        return new String(fieldText.getPassword());
    }
}
