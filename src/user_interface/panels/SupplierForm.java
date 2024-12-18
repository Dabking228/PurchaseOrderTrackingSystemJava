package user_interface.panels;

import backend.Backend;
import data.Supplier;
import user_interface.MainMenu;

import javax.swing.*;
import java.awt.*;

public class SupplierForm extends BasePanel<Supplier> {
    protected FieldText fieldSupplierCode, fieldSupplierName, fieldContactInfo;
    private JButton confirmButton, deleteButton, editButton, editConfirm, editCancel;
    private JPanel editConfirmPanel;

    private String panelName;
    private Supplier supplier;
    private String rowData;

    public SupplierForm(Backend backend, MainMenu parent) {
        super("Supplier Form", parent, backend.db.suppliersMap, backend);

        // Supplier Code Field
        fieldSupplierCode = new FieldText("Supplier Code: ");
        contentPanel.add(fieldSupplierCode);

        // Supplier Name Field
        fieldSupplierName = new FieldText("Supplier Name: ");
        contentPanel.add(fieldSupplierName);

        // Contact Information Field
        fieldContactInfo = new FieldText("Contact Info: ");
        contentPanel.add(fieldContactInfo);

        // Confirm Button
        confirmButton = new JButton("Add Supplier");
        titleButtonPanel.add(confirmButton);

        confirmButton.addActionListener(e -> {
            try {
                String code = fieldSupplierCode.getData();
                String name = fieldSupplierName.getData();
                String contact = fieldContactInfo.getData();

                if (!code.isEmpty() && !name.isEmpty() && !contact.isEmpty()) {
                    backend.db.addSupplier(new Supplier(code, name, contact));
                    JOptionPane.showMessageDialog(this, "Supplier added successfully!");
                    clearFields();
                    parent.showMainMenu();
                } else {
                    JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error adding supplier.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Delete and Edit Buttons
        deleteButton = new JButton("Delete");
        editButton = new JButton("Edit");
        deleteButton.setVisible(false);
        editButton.setVisible(false);

        titleButtonPanel.add(deleteButton);
        titleButtonPanel.add(editButton);

        // Edit Confirmation Panel
        editConfirmPanel = new JPanel(new GridLayout(1, 2));
        editConfirm = new JButton("Confirm Edit");
        editCancel = new JButton("Cancel");
        editConfirmPanel.add(editConfirm);
        editConfirmPanel.add(editCancel);
        editConfirmPanel.setVisible(false);
        contentPanel.add(editConfirmPanel);

        // Edit Button Action
        editButton.addActionListener(e -> {
            setFieldsEditable(true);
            editConfirmPanel.setVisible(true);
        });

        // Delete Button Action
        deleteButton.addActionListener(e -> {
            backend.db.suppliersMap.remove(supplier.getId());
            JOptionPane.showMessageDialog(this, "Supplier deleted successfully.");
            parent.showPanel("supplierTable");
        });

        // Edit Confirm Action
        editConfirm.addActionListener(e -> {
            try {
                supplier.setSupplierCode(fieldSupplierCode.getData());
                supplier.setSupplierName(fieldSupplierName.getData());
                supplier.setContactInfo(fieldContactInfo.getData());
            } catch (Exception err) {
                err.printStackTrace();
            }

            JOptionPane.showMessageDialog(this, "Supplier updated successfully.");
            setFieldsEditable(false);
            editConfirmPanel.setVisible(false);
        });

        // Edit Cancel Action
        editCancel.addActionListener(e -> {
            setData();
            setFieldsEditable(false);
            editConfirmPanel.setVisible(false);
        });

        backButton.addActionListener(e -> {
            clearFields();
            if (panelName != null) {
                parent.showPanel(panelName);
                panelName = null;
            } else {
                parent.showMainMenu();
            }
        });
    }

    // Update the form with supplier data
    public void setData() {
        supplier = backend.db.getSupplier(rowData);
        fieldSupplierCode.setData(supplier.getSupplierCode());
        fieldSupplierName.setData(supplier.getSupplierName());
        fieldContactInfo.setData(supplier.getContactInfo());
    }

    // Set row number (for identifying which supplier to edit)
    public void setRowNum(String rowData) {
        this.rowData = rowData;
    }

    // Set the panel to return to
    public void setBack(String panelName) {
        this.panelName = panelName;
    }

    // View-only mode
    public void viewOnly() {
        setFieldsEditable(false);
        confirmButton.setVisible(false);
        deleteButton.setVisible(false);
        editButton.setVisible(false);
    }

    // Enable editing mode
    public void viewUpdate() {
        deleteButton.setVisible(true);
        editButton.setVisible(true);
    }

    // Helper methods
    private void clearFields() {
        fieldSupplierCode.setData("");
        fieldSupplierName.setData("");
        fieldContactInfo.setData("");
    }

    private void setFieldsEditable(boolean editable) {
        fieldSupplierCode.setEditable(editable);
        fieldSupplierName.setEditable(editable);
        fieldContactInfo.setEditable(editable);
    }
}
