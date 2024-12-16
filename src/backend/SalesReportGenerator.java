package backend;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;

import data.Sale;
import data.Account;
import data.Item;

public class SalesReportGenerator {

    private SalesReportGenerator() {
    }

    public static void generateSalesReport(String outputFilePath, Date month, HashMap<String, Sale> sales,
            HashMap<String, Account> Accounts, HashMap<String, Item> items) {
        try {
            // filter sales
            Calendar cal = Calendar.getInstance();
            cal.setTime(month);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            Date firstDayOfMonth = cal.getTime();

            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            Date lastDayOfMonth = cal.getTime();

            // filter this month's sales
            HashMap<String, Sale> salesFiltered = new HashMap<>(sales);
            salesFiltered.values().removeIf(sale -> {
                Date saleDate = sale.getSaleDate();
                return saleDate.before(firstDayOfMonth) || saleDate.after(lastDayOfMonth);
            });

            // init
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(outputFilePath));
            document.open();
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 24, Font.BOLD);
            Font headingFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD);

            // title
            document.add(new Paragraph("Monthly Sales Report", titleFont));
            document.add(new Paragraph("Generated on: " + java.time.LocalDate.now()));
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
            document.add(new Paragraph("Month: " + sdf.format(month)));
            document.add(new Paragraph("\n"));

            // summary section
            int totalQuantity = 0;
            double totalRevenue = 0;
            HashMap<String, Integer> managerSalesCount = new HashMap<>();

            for (Sale sale : salesFiltered.values()) {
                totalQuantity += sale.getQuantitySold();
                BigDecimal itemPrice = items.get(sale.getItemId()).getPrice();
                totalRevenue += itemPrice.multiply(BigDecimal.valueOf(sale.getQuantitySold())).doubleValue();
                String managerId = sale.getSalesManagerId();
                managerSalesCount.put(managerId, managerSalesCount.getOrDefault(managerId, 0) + sale.getQuantitySold());
            }

            String topManagerId = managerSalesCount.entrySet().stream()
                    .max((entry1, entry2) -> entry1.getValue().compareTo(entry2.getValue()))
                    .map(HashMap.Entry::getKey)
                    .orElse("");
            if (topManagerId.equals("")) {
                throw new RuntimeException("No sales manager found");
            }
            String topManagerName = Accounts.get(topManagerId).getUsername();

            document.add(new Paragraph("Summary", headingFont));
            document.add(new Paragraph("Total Quantity Sold: " + totalQuantity));
            document.add(new Paragraph("Total Revenue: $" + totalRevenue));
            document.add(new Paragraph("Top Sales Manager: " + topManagerName));
            document.add(new Paragraph("\n"));

            // sales table grouped by week
            document.add(new Paragraph("Weekly Sales Breakdown", headingFont));
            document.add(new Paragraph("\n"));

            HashMap<Integer, HashMap<String, Integer>> weeklySales = new HashMap<>();

            for (Sale sale : salesFiltered.values()) {
                Date saleDate = sale.getSaleDate();
                Calendar saleCal = Calendar.getInstance();
                saleCal.setTime(saleDate);
                int weekOfYear = saleCal.get(Calendar.WEEK_OF_YEAR);

                weeklySales.putIfAbsent(weekOfYear, new HashMap<>());
                HashMap<String, Integer> weekSales = weeklySales.get(weekOfYear);
                weekSales.put(sale.getItemId(), weekSales.getOrDefault(sale.getItemId(), 0) + sale.getQuantitySold());
            }

            PdfPTable weekTable = new PdfPTable(3);
            weekTable.setWidthPercentage(100);
            weekTable.addCell("Week");
            weekTable.addCell("Quantity Sold");
            weekTable.addCell("Revenue");

            for (Integer week : weeklySales.keySet()) {
                HashMap<String, Integer> weekSales = weeklySales.get(week);
                int quantitySold = 0;
                double revenue = 0;

                for (String itemId : weekSales.keySet()) {
                    quantitySold += weekSales.get(itemId);
                    BigDecimal itemPrice = items.get(itemId).getPrice();
                    revenue += itemPrice.multiply(BigDecimal.valueOf(quantitySold)).doubleValue();
                }

                weekTable.addCell(String.valueOf(week));
                weekTable.addCell(String.valueOf(quantitySold));
                weekTable.addCell(String.valueOf(revenue));
            }

            document.add(weekTable);
            document.add(new Paragraph("\n"));

            document.close();
        } catch (Exception e) {
            System.err.println("Error generating report: " + e.getMessage());
        }
    }
}
