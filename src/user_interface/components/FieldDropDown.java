package user_interface.components;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FieldDropDown extends JPanel {
    protected JLabel fieldLabel;
    protected JComboBox<String> fieldCombo;

    public FieldDropDown(String fieldName, String[] options) {
        setLayout(new GridLayout(1,2));

        // Labelllll
        fieldLabel = new JLabel(fieldName);
        Font labelFont = new Font(fieldLabel.getFont().getName(), Font.BOLD, 15);
        fieldLabel.setFont(labelFont);
        add(fieldLabel, BorderLayout.WEST);

        // Dropdown bish
        fieldCombo = new JComboBox<>(options);
        Font comboFont = new Font(fieldCombo.getFont().getName(), Font.PLAIN, 15);
        fieldCombo.setFont(comboFont);
        add(fieldCombo, BorderLayout.EAST);

        setMaximumSize(getPreferredSize());
    }

    public String getSelected() {
        return (String) fieldCombo.getSelectedItem();
    }

    public void setSelected(String item) {
        fieldCombo.setSelectedItem(item);
    }
}
