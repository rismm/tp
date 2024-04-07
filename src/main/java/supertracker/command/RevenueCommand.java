package supertracker.command;

import java.time.LocalDate;
import supertracker.item.TransactionList;

public class RevenueCommand implements Command {
    private String timeFrame;
    private LocalDate startDate;
    private LocalDate endDate;

    public RevenueCommand (String timeFrame, LocalDate startDate, LocalDate endDate) {
        this.timeFrame = timeFrame;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public void execute() {
        int numberOfOrders;
        double revenue;
        switch (timeFrame) {
        case "today":
            revenue = TransactionList.calculateDayRevenue(LocalDate.now());
            break;

        default:
        }
    }

    @Override
    public boolean isQuit() {
        return false;
    }
}
