package supertracker.command;

import supertracker.ui.Ui;
import supertracker.item.Inventory;
import supertracker.item.Item;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class ReportCommand implements Command{
    private String reportType;
    private int threshold;

    public ReportCommand(String reportType, int threshold) {
        this.reportType = reportType;
        this.threshold = threshold;
    }

    @Override
    public void execute() {
        List<Item> items = Inventory.getItems();
        if (items.isEmpty()) {
            Ui.reportNoItems();
        } else {
            reportHasItemsExecute(items);
        }
    }

    private void reportHasItemsExecute(List<Item> items) {
        LocalDate currDate = LocalDate.now();
        LocalDate expiryThresholdDate = currDate.plusWeeks(1);
        LocalDate dayBeforeCurrDay = currDate.minusDays(1);
        if (reportType.equals("low stock")) {
            createLowStockReport(items);
        } else if (reportType.equals("expiry")) {
            createExpiryReport(items, expiryThresholdDate, currDate, dayBeforeCurrDay);
        }
    }

    private void createExpiryReport(List<Item> items, LocalDate expiryThresholdDate, LocalDate currDate,
                                    LocalDate dayBeforeCurrDay) {
        assert threshold == -1;
        List<Item> reportExpiryItems = new ArrayList<>();
        List<Item> reportExpiredItems = new ArrayList<>();
        for (Item item : items) {
            if (item.getExpiryDate().isBefore(expiryThresholdDate) && item.getExpiryDate().isAfter(dayBeforeCurrDay)) {
                reportExpiryItems.add(item);
            }
            if (item.getExpiryDate().isBefore(currDate)) {
                reportExpiredItems.add(item);
            }
        }
        reportExpiryItems.sort(Item.sortByDate());
        reportExpiredItems.sort(Item.sortByDate());
        Ui.reportCommandSuccess(reportExpiryItems, reportType);
        Ui.reportCommandSuccess(reportExpiredItems, "expired");
    }

    private void createLowStockReport(List<Item> items) {
        List<Item> reportLowStockItems = new ArrayList<>();
        for (Item item : items) {
            if (item.getQuantity() < threshold) {
                reportLowStockItems.add(item);
            }
        }
        reportLowStockItems.sort(Item.sortByQuantity());
        Ui.reportCommandSuccess(reportLowStockItems, reportType);
    }

    @Override
    public boolean isQuit() {
        return false;
    }

}
