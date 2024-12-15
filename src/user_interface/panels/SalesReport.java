package user_interface.panels;

import backend.Backend;
import data.Sale;
import user_interface.MainMenu;
import data.Account;
import data.Item;
import backend.SalesReportGenerator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class SalesReport extends Panel<Sale> {

    public SalesReport(Backend backend, MainMenu parent) {
        super("Sales Report", parent, backend.db.salesMap, backend);
        backMainMenu("");

        contentPanel.setLayout(new BorderLayout());

        // Summary Section
        JPanel summaryPanel = new JPanel(new GridLayout(3, 1));
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Summary"));

        JLabel totalQuantityLabel = new JLabel();
        JLabel totalRevenueLabel = new JLabel();
        JLabel topManagerLabel = new JLabel();

        summaryPanel.add(totalQuantityLabel);
        summaryPanel.add(totalRevenueLabel);
        summaryPanel.add(topManagerLabel);

        // Weekly Breakdown Section
        JTable weeklyTable = new JTable(new DefaultTableModel(new Object[] { "Week", "Quantity Sold", "Revenue" }, 0));
        JScrollPane tableScrollPane = new JScrollPane(weeklyTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Weekly Sales Breakdown"));

        contentPanel.add(summaryPanel, BorderLayout.NORTH);
        contentPanel.add(tableScrollPane, BorderLayout.CENTER);

        populateData(totalQuantityLabel, totalRevenueLabel, topManagerLabel,
                (DefaultTableModel) weeklyTable.getModel());

        // Export as PDF
        JButton exportButton = new JButton("Export as PDF");
        exportButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");

            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                SalesReportGenerator.generateSalesReport(
                        filePath,
                        new Date(),
                        backend.db.salesMap,
                        backend.db.accountsMap,
                        backend.db.itemsMap);
            }
        });
        titleButtonPanel.add(exportButton, BorderLayout.SOUTH);
    }

    private void populateData(JLabel totalQuantityLabel, JLabel totalRevenueLabel, JLabel topManagerLabel,
            DefaultTableModel tableModel) {
        Map<String, Sale> sales = backend.db.salesMap;
        Map<String, Account> accounts = backend.db.accountsMap;
        Map<String, Item> items = backend.db.itemsMap;

        // Filter sales for the current month
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        Date firstDayOfMonth = cal.getTime();

        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date lastDayOfMonth = cal.getTime();

        List<Sale> salesFiltered = new ArrayList<>();
        for (Sale sale : sales.values()) {
            Date saleDate = sale.getSaleDate();
            if (!saleDate.before(firstDayOfMonth) && !saleDate.after(lastDayOfMonth)) {
                salesFiltered.add(sale);
            }
        }

        // Calculate summary
        int totalQuantity = 0;
        double totalRevenue = 0;
        Map<String, Integer> managerSalesCount = new HashMap<>();

        for (Sale sale : salesFiltered) {
            totalQuantity += sale.getQuantitySold();
            BigDecimal itemPrice = items.get(sale.getItemId()).getPrice();
            totalRevenue += itemPrice.multiply(BigDecimal.valueOf(sale.getQuantitySold())).doubleValue();

            String managerId = sale.getSalesManagerId();
            managerSalesCount.put(managerId, managerSalesCount.getOrDefault(managerId, 0) + sale.getQuantitySold());
        }

        String topManagerId = managerSalesCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("");
        String topManagerName = topManagerId.isEmpty() ? "N/A" : accounts.get(topManagerId).getUsername();

        totalQuantityLabel.setText("Total Quantity Sold: " + totalQuantity);
        totalRevenueLabel.setText("Total Revenue: $" + totalRevenue);
        topManagerLabel.setText("Top Sales Manager: " + topManagerName);

        // Weekly sales breakdown
        Map<Integer, Map<String, Integer>> weeklySales = new HashMap<>();

        for (Sale sale : salesFiltered) {
            Date saleDate = sale.getSaleDate();
            Calendar saleCal = Calendar.getInstance();
            saleCal.setTime(saleDate);
            int weekOfYear = saleCal.get(Calendar.WEEK_OF_YEAR);

            weeklySales.putIfAbsent(weekOfYear, new HashMap<>());
            Map<String, Integer> weekSales = weeklySales.get(weekOfYear);
            weekSales.put(sale.getItemId(), weekSales.getOrDefault(sale.getItemId(), 0) + sale.getQuantitySold());
        }

        for (Integer week : weeklySales.keySet()) {
            Map<String, Integer> weekSales = weeklySales.get(week);
            int quantitySold = 0;
            double revenue = 0;

            for (String itemId : weekSales.keySet()) {
                quantitySold += weekSales.get(itemId);
                BigDecimal itemPrice = items.get(itemId).getPrice();
                revenue += itemPrice.multiply(BigDecimal.valueOf(weekSales.get(itemId))).doubleValue();
            }

            tableModel.addRow(new Object[] { week, quantitySold, revenue });
        }
    }
}
