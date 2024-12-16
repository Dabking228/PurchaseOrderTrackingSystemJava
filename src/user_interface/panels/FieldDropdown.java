package user_interface.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import backend.Backend;
import data.BaseItem;

public class FieldDropdown<T extends BaseItem> extends JPanel {
    protected Backend backend;
    protected ComboList<T> lists;
    protected Map<String, T> items;
    protected JLabel fieldLabel;
    protected JComboBox<ComboItem<T>> fieldCombo;

    public FieldDropdown(String fieldName, Map<String, T> items, ComboList<T> lists, Backend backend) {
        this.items = items;
        this.lists = lists;
        setLayout(new GridLayout(1, 2));

        // Label
        fieldLabel = new JLabel(fieldName);
        Font labelFont = new Font(fieldLabel.getFont().getName(), Font.BOLD, 15);
        fieldLabel.setFont(labelFont);
        add(fieldLabel);

        // Dropdown bish
        fieldCombo = new JComboBox<ComboItem<T>>();
        // fieldCombo.setSize(new Dimension(15, getPreferredSize().height));
        Font comboFont = new Font(fieldCombo.getFont().getName(), Font.PLAIN, 15);
        fieldCombo.setFont(comboFont);
        fieldCombo.setPreferredSize(new Dimension(208, fieldCombo.getPreferredSize().height));
        add(fieldCombo);

        this.backend = backend;
        lists.setBackend(backend);

        lists.setItem(items);
        lists.setValue();
        for (String UUID : lists.UUID) {
            fieldCombo.addItem(new ComboItem<T>(lists.getName(UUID), lists.getValue(UUID)));
            // System.out.println(lists.getName(UUID));
            // System.out.println(lists.getValue(UUID));
        }

        setMaximumSize(getPreferredSize());
    }

    public FieldDropdown(String fieldName, Map<String, T> items, ComboList<T> lists){
        this(fieldName, items, lists,null);
       

    }
    public ComboItem<T> getSelected() {
        if (fieldCombo.getSelectedIndex() != -1) {
            return (ComboItem<T>) fieldCombo.getSelectedItem();
        }
        return null;
    }

    public void setEditable(boolean value) {
        fieldCombo.setEnabled(value);
    }

    public void setData(Object object) {
        for (int i = 0; i < fieldCombo.getItemCount(); i++) {
            if (fieldCombo.getItemAt(i).getValue().getId() == object) {
                fieldCombo.setSelectedIndex(i);
            }
        }
    }

}

class ComboItem<T extends BaseItem> {
    private String key;
    private T value;

    public ComboItem(String key, T value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return key;
    }

    public String getKey() {
        return key;
    }

    public T getValue() {
        return value;
    }
}

abstract class ComboList<T extends BaseItem> {
    protected Map<String, T> items;
    protected String[] UUID;
    protected T[] values;
    protected Backend backend;
    
    public void setBackend(Backend backend){
        this.backend = backend;
    }

    public void setItem(Map<String, T> items) {
        this.items = items;
    }

    public void setValue() {
        initGetValue();
    }

    void initGetValue() {
        UUID = new String[items.size()];
        int i = 0;
        for (Map.Entry<String, T> item : items.entrySet()) {
            UUID[i] = item.getKey();
            values[i] = item.getValue();
            i++;
            // System.out.println(item.getValue());
        }
    }

    public String getName(String UUID) {
        return "";
    }

    public T getValue(String UUID) {
        return items.get(UUID);
    }

    public T getObject(Object ItemName) {
        for (T item : items.values()) {
            return item;
        }
        return null;
    }

}
