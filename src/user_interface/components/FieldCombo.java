package user_interface.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FieldCombo extends JPanel {
    protected JLabel fieldLabel;
    protected JTextField fieldText;

    public FieldCombo(String fieldName) {
        setLayout(new FlowLayout());

        // Label
        fieldLabel = new JLabel(fieldName);
        Font labelFont = new Font(fieldLabel.getFont().getName(), Font.BOLD, 15);
        fieldLabel.setFont(labelFont);
        add(fieldLabel);

        // Seperator
        add(Box.createHorizontalStrut(3));

        // Field
        fieldText = new JTextField(15);
        Font textFont = new Font(fieldText.getFont().getName(), Font.PLAIN, 15);
        fieldText.setFont(textFont);
        add(fieldText, BorderLayout.NORTH);
    }

    public String getText(){
        return fieldText.getText();
    }
}
