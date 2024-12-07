package user_interface.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class FieldCombo extends JPanel {
    private boolean isNumber;
    private int extractNum; 
    protected JLabel fieldLabel;
    protected JTextField fieldText;

    public FieldCombo(String fieldName, boolean isNumber) {
        setLayout(new GridLayout(1,2));

        // Label
        fieldLabel = new JLabel(fieldName);
        Font labelFont = new Font(fieldLabel.getFont().getName(), Font.BOLD, 15);
        fieldLabel.setFont(labelFont);
        add(fieldLabel,BorderLayout.WEST);       

        // Field
        fieldText = new JTextField(15);
        Font textFont = new Font(fieldText.getFont().getName(), Font.PLAIN, 15);
        fieldText.setFont(textFont);
        add(fieldText,BorderLayout.EAST);

        

        if(isNumber){
            fieldText.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e){
                    try{
                        extractNum = Integer.parseInt(fieldText.getText());
                        fieldText.setBorder(null);
                    } catch (NumberFormatException err){
                        fieldText.setText("");
                        fieldText.setBorder(new LineBorder(Color.RED,2));
                    }
                }
            });
        }

        setMaximumSize(getPreferredSize());

    }

    public FieldCombo(String fieldName) {
        this(fieldName, false);
    }

    public String getText(){
        if(!isNumber){
            return fieldText.getText();
        }
        return Integer.toString(extractNum);
    }
}
