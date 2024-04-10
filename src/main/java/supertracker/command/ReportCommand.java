package supertracker.command;

import supertracker.ui.Ui;
import supertracker.item.Inventory;
import supertracker.item.Item;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a command to generate and display reports based on inventory items.
 */
public class ReportCommand implements Command{
    private String reportType;
    private int threshold;

    /**
     * Constructs a ReportCommand with the specified report type and threshold.
     *
     * @param reportType The type of report to generate ("low stock" or "expiry").
     * @param threshold  The threshold value used for filtering items in the report.
     */
    public ReportCommand(String reportType, int threshold) {
        this.reportType = reportType;
        this.threshold = threshold;
    }

    /**
     * Executes the report command to generate and display the specified report type.
     * Retrieves inventory items and generates the appropriate report based on the report type.
     */
    @Override
    public void execute() {
        List<Item> items = Inventory.getItems();
        if (items.isEmpty()) {
            Ui.reportNoItems();
        } else {
            reportHasItemsExecute(items);
        }
    }

    /**
     * Generates the appropriate report based on the available inventory items.
     *
     * @param items The list of inventory items used for generating the report.
     */
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

    /**
     * Creates and displays a report for items that are close to expiry or have expired.
     *
     * @param items               The list of inventory items to check for expiry.
     * @param expiryThresholdDate The threshold date to determine items close to expiry.
     * @param currDate            The current date used for comparison.
     * @param dayBeforeCurrDay    The date one day before the current date for expiry comparison.
     */
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

    /**
     * Creates and displays a report for items with low stock quantities.
     *
     * @param items The list of inventory items to check for low stock.
     */
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

    /**
     * Indicates whether executing this command should result in quitting the application.
     *
     * @return Always returns false, as executing this command does not trigger application quit.
     */
    @Override
    public boolean isQuit() {
        return false;
    }

}
