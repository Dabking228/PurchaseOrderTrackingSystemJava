package user_interface.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class FieldText extends JPanel {
    private boolean isNumber;
    protected JLabel fieldLabel;
    protected JTextField fieldText;

    public FieldText(String fieldName, boolean isNumber) {
        this.isNumber = isNumber;
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
            Border defaultBorder = fieldText.getBorder();
            Border errorBorder = new LineBorder(Color.RED, 2);
            Border paddingBorder = BorderFactory.createEmptyBorder(2, 2, 2, 2); // Adjust insets as needed
            fieldText.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e){
                    try{
                        String text = fieldText.getText();
                        
                        // Check if the text can be parsed as an integer
                        if (text.matches("-?\\d+")) { // Matches integers
                            Integer.parseInt(text);
                        }
                        // Check if the text can be parsed as a double
                        else if (text.matches("-?\\d+(\\.\\d+)?")) { // Matches decimals
                            Double.parseDouble(text);
                        } else {
                            throw new NumberFormatException("Not a valid number");
                        }

                        fieldText.setBorder(defaultBorder);
                    } catch (NumberFormatException err){
                        fieldText.setText("");
                        fieldText.setBorder(BorderFactory.createCompoundBorder(errorBorder, paddingBorder));
                    }
                }
            });
        }



        setMaximumSize(getPreferredSize());

    }

    public FieldText(String fieldName) {
        this(fieldName, false);
    }

    public void setData(String t){
        fieldText.setText(t);
    }


    public String getData() throws Exception{
        if(!isNumber){
            return fieldText.getText();
        }
        throw new Exception("Text field is a number value, use getIntData");
    }

    public int getIntData() throws Exception  {
        if(isNumber){
            return Integer.parseInt(fieldText.getText());
        }
        throw new Exception("Text field is not a number value, use getData");
    }

    public double getDoubleData() throws Exception{
        if(isNumber){
            return Double.parseDouble(fieldText.getText());
        }
        throw new Exception("Text field is not a number value, use getData");
    }

    public void resetField(){
        fieldText.setText("");
    }

    public void setEditable(boolean value){
        fieldText.setEditable(value);
        fieldText.setFocusable(value);
    }
}
