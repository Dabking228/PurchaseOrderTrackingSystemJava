package user_interface.panels;

import backend.Backend;
import data.Item;
import data.PurchaseOrder;
import data.PurchaseRequisition;
import data.Status;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import user_interface.*;

public class POForm extends BasePanel<PurchaseOrder> {
    private String rowData;
    private PurchaseOrder PO;
    private boolean FMView = false;
    private String panelName;

    protected FieldText fieldItemID, fieldSupplierID, fieldPRStatus, fieldPOStatus, fieldtotalAmount, fieldCurrStock,
            fieldTotalStock;
    protected FieldDropdown<PurchaseRequisition> dropPR;
    private JLabel greenLabel;
    private JPanel editcfm, approvecfm;
    private JButton confirmButton, deleteButton, editButton, editConfirm, editCancel, approveButton, rejectButton,
            paymentButton;

    public POForm(Backend backend, MainMenu parent) {
        super("Purchase Order Panel", parent, backend.db.purchaseOrdersMap, backend);

        dropPR = new FieldDropdown<PurchaseRequisition>("Name || Order Amount", backend.db.purchaseRequisitionsMap,
                new PRListPurOrder(), backend);
        dropPR.fieldCombo.setSelectedIndex(-1);
        contentPanel.add(dropPR);

        fieldItemID = new FieldText("Item Name");
        fieldItemID.setEditable(false);
        contentPanel.add(fieldItemID);

        fieldSupplierID = new FieldText("Supplier");
        fieldSupplierID.setEditable(false);
        contentPanel.add(fieldSupplierID);

        fieldPRStatus = new FieldText("PR Status");
        fieldPRStatus.setEditable(false);
        contentPanel.add(fieldPRStatus);

        fieldPOStatus = new FieldText("PO Status");
        fieldPOStatus.setEditable(false);
        contentPanel.add(fieldPOStatus);

        fieldCurrStock = new FieldText("Current Stock");
        fieldCurrStock.setEditable(false);
        contentPanel.add(fieldCurrStock);

        fieldTotalStock = new FieldText("Order Stock");
        fieldTotalStock.setEditable(false);
        contentPanel.add(fieldTotalStock);

        fieldtotalAmount = new FieldText("Total", true);
        fieldtotalAmount.setEditable(false);
        contentPanel.add(fieldtotalAmount);

        greenLabel = new JLabel("Item added");
        greenLabel.setVisible(false);
        greenLabel.setForeground(Color.GREEN);
        greenLabel.setFont(new Font(greenLabel.getText(), Font.BOLD, 20));
        contentPanel.add(greenLabel);

        confirmButton = new JButton("Confirm");
        confirmButton.setEnabled(false);
        titleButtonPanel.add(confirmButton);
        confirmButton.addActionListener(e -> {
            try {
                double totalAmount = fieldtotalAmount.getDoubleData();
                PurchaseRequisition item = (PurchaseRequisition) dropPR.getSelected().getValue();
                String supplierID = backend.db.getItem(item.getItemId()).getSupplierId();

                dropPR.fieldCombo.setSelectedIndex(-1);
                fieldItemID.setData("");
                fieldSupplierID.setData("");
                fieldPRStatus.setData("");
                fieldPOStatus.setData("");
                fieldtotalAmount.setData("");
                fieldTotalStock.setData("");
                fieldCurrStock.setData("");

                backend.db.addPurchaseOrder(new PurchaseOrder(item.getId(), supplierID,
                        backend.getCurrentAccount().getId(), Status.PENDING, new java.util.Date(), totalAmount));
                greenLabel.setVisible(true);

                Timer timer = new Timer(2000, g -> {
                    greenLabel.setVisible(false);
                    parent.showPanel("purchaseOrdersTable");
                });
                timer.setRepeats(false);
                timer.start();
            } catch (Exception err) {
                System.out.println(err);
            }

        });

        backButton.addActionListener(e -> {
            parent.showPanel(panelName);
            dropPR.fieldCombo.setSelectedIndex(-1);
            fieldItemID.setData("");
            fieldSupplierID.setData("");
            fieldPRStatus.setData("");
            fieldPOStatus.setData("");
            fieldtotalAmount.setData("");
            fieldTotalStock.setData("");
            fieldCurrStock.setData("");
            this.FMView = false;
            paymentButton.setVisible(false);
        });

        dropPR.fieldCombo.addActionListener(e -> {
            // System.out.println(dropItemID.getSelected().getValue());

            PurchaseRequisition item = (dropPR.getSelected() != null)
                    ? (PurchaseRequisition) dropPR.getSelected().getValue()
                    : null;
            if (item != null) {
                Item typeItem = backend.db.getItem(item.getItemId());
                String SupplierName = backend.db.getSupplier(typeItem.getSupplierId()).getSupplierName();

                fieldItemID.setData(typeItem.getItemName());
                fieldSupplierID.setData(SupplierName);
                fieldPRStatus.setData(item.getStatus().toString());
                fieldCurrStock.setData(String.valueOf(typeItem.getStockLevel()));
                fieldTotalStock.setData(String.valueOf(item.getQuantity()));

                if (item.getStatus() == Status.PENDING || item.getStatus() == Status.REJECTED) {
                    fieldtotalAmount.setVisible(false);
                    confirmButton.setEnabled(false);

                } else {
                    fieldtotalAmount.setVisible(true);
                    fieldtotalAmount.setEditable(true);
                    confirmButton.setEnabled(true);
                }
            }

        });

        deleteButton = new JButton("Delete");
        editButton = new JButton("Edit");
        titleButtonPanel.add(deleteButton);
        titleButtonPanel.add(editButton);

        editButton.addActionListener(e -> {
            editcfm.setVisible(true);
            fieldtotalAmount.setEditable(true);
        });

        deleteButton.addActionListener(e -> {
            backend.db.purchaseOrdersMap.remove(PO.getId());
            parent.showPanel("purchaseOrdersTable");
        });

        editcfm = new JPanel(new GridLayout(1, 2));
        editConfirm = new JButton("Confirm");
        editCancel = new JButton("Cancel");
        editcfm.add(editConfirm);
        editcfm.add(editCancel);
        editcfm.setMaximumSize(new Dimension(300, 30));
        contentPanel.add(editcfm);

        editConfirm.addActionListener(e -> {
            editcfm.setVisible(false);

            try {
                double totalVal = fieldtotalAmount.getDoubleData();

                if (PO.getTotalAmount() != totalVal) {
                    System.out.println("total amount changed"); // TODO: remove
                    backend.db.purchaseOrdersMap.get(PO.getId()).setTotalAmount(totalVal);
                }

                fieldtotalAmount.setData(String.valueOf(PO.getTotalAmount()));
                fieldtotalAmount.setEditable(false);

            } catch (Exception err) {
                System.out.println(err);
                fieldtotalAmount.setEditable(false);
                fieldtotalAmount.setData(String.valueOf(PO.getTotalAmount()));
            }
        });

        editCancel.addActionListener(e -> {
            fieldtotalAmount.setEditable(false);
            fieldtotalAmount.setData(String.valueOf(PO.getTotalAmount()));

            editcfm.setVisible(false);
        });

        approvecfm = new JPanel(new GridLayout(1, 2));
        approveButton = new JButton("Approve");
        rejectButton = new JButton("Reject");
        approvecfm.add(approveButton);
        approvecfm.add(rejectButton);
        approvecfm.setMaximumSize(new Dimension(300, 30));
        approvecfm.setVisible(false);
        contentPanel.add(approvecfm);

        approveButton.addActionListener(e -> {
            backend.db.purchaseOrdersMap.get(PO.getId()).setPoStatus(Status.APPROVED);
            fieldPOStatus.setData(PO.getPoStatus().toString());
            approvecfm.setVisible(false);
            FMPayment();
        });
        rejectButton.addActionListener(e -> {
            backend.db.purchaseOrdersMap.get(PO.getId()).setPoStatus(Status.REJECTED);
            fieldPOStatus.setData(PO.getPoStatus().toString());
            approvecfm.setVisible(false);
        });

        paymentButton = new JButton("Pay");
        paymentButton.setVisible(false);
        titleButtonPanel.add(paymentButton);

        paymentButton.addActionListener(e -> {
            backend.db.purchaseOrdersMap.get(PO.getId()).setPoStatus(Status.PAID);
            fieldPOStatus.setData(PO.getPoStatus().toString());
            paymentButton.setVisible(false);
        });
    }

    void createHideOrShow(Boolean bool) {
        dropPR.setVisible(bool);
        confirmButton.setVisible(bool);
        fieldtotalAmount.setVisible(!bool);
        fieldPOStatus.setVisible(!bool);
        approvecfm.setVisible(false);
    }

    void editHideOrShow(boolean bool) {
        editcfm.setVisible(bool);
        approvecfm.setVisible(false);
    }

    void editorHideOrShow(boolean bool) {
        deleteButton.setVisible(bool);
        editButton.setVisible(bool);
        approvecfm.setVisible(false);
    }

    public void createPO() {
        dropPR.AddUpdateItems();
        createHideOrShow(true);
        editorHideOrShow(false);
        editHideOrShow(false);
    }

    public void viewOnly() {
        createHideOrShow(false);
        editorHideOrShow(false);
        editHideOrShow(false);
    }

    public void viewOnlyUpdate() {
        editorHideOrShow(true);
        createHideOrShow(false);
        editHideOrShow(false);
    }

    public void FMView(boolean bool) {
        this.FMView = bool;
    }

    void FMPayment() {
        paymentButton.setVisible(true);
    }

    public void setData() {
        dropPR.AddUpdateItems();
        PO = backend.db.purchaseOrdersMap.get(rowData);
        PurchaseRequisition PR = backend.db.purchaseRequisitionsMap.get(PO.getPurchaseRequisitionId());
        Item item = backend.db.itemsMap.get(PR.getItemId());

        String itemName = item.getItemName();
        String supplierName = backend.db.suppliersMap.get(item.getSupplierId()).getSupplierName();

        fieldItemID.setData(itemName);
        fieldSupplierID.setData(supplierName);
        fieldPRStatus.setData(PR.getStatus().toString());
        fieldPOStatus.setData(PO.getPoStatus().toString());
        fieldCurrStock.setData(String.valueOf(item.getStockLevel()));
        fieldTotalStock.setData(String.valueOf(PR.getQuantity()));
        fieldtotalAmount.setData(String.valueOf(PO.getTotalAmount()));
        System.out.println(FMView);
        if (FMView) {
            System.out.println("am i in fm? yess");
            if (PO.getPoStatus() == Status.PENDING && PR.getStatus() == Status.APPROVED) {
                approvecfm.setVisible(true);
            }
            if (PO.getPoStatus() == Status.APPROVED && PR.getStatus() == Status.APPROVED) {
                FMPayment();
            }
        }
    }

    public void setRowNum(String data) {
        this.rowData = data;
    }

    public void setBack(String PanelName) {
        this.panelName = PanelName;
    }
}

class PRListPurOrder extends ComboList<PurchaseRequisition> {

    @Override
    public void setItem(Map<String, PurchaseRequisition> items) {
        this.items = items;
    }

    @Override
    public String getName(String UUID) {
        String itemName = backend.db.getItem(this.items.get(UUID).getItemId()).getItemName();
        String name = itemName + " || " + this.items.get(UUID).getQuantity() + " Stocks";
        return name;
    }

    @Override
    public void setValue() {
        this.values = new PurchaseRequisition[this.items.size()];
        initGetValue();
    }

    @Override
    public PurchaseRequisition getObject(Object ItemName) {
        for (PurchaseRequisition item : items.values()) {
            if (item.getId() == String.valueOf(ItemName)) {
                return item;
            }
        }
        return null;
    }
}
