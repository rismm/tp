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
        List<Item> reportItems = new ArrayList<>();
        LocalDate currDate = LocalDate.now();
        LocalDate expiryThresholdDate = currDate.plusWeeks(1);
        if (reportType.equals("low stock")) {
            for (Item item : items) {
                if (item.getQuantity() < threshold) {
                    reportItems.add(item);
                }
            }
            Ui.reportCommandSuccess(reportItems, reportType);
        } else if (reportType.equals("expiry")) {
            assert threshold == -1;
            for (Item item : items) {
                if (item.getExpiryDate().isBefore(expiryThresholdDate)) {
                    reportItems.add(item);
                }
            }
            Ui.reportCommandSuccess(reportItems, reportType);
        }
    }

    @Override
    public boolean isQuit() {
        return false;
    }

}
