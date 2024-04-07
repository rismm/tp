package supertracker.command;

import java.time.LocalDate;
import supertracker.item.TransactionList;

public class RevenueCommand implements Command {
    private String task;
    private LocalDate startDate;
    private LocalDate endDate;
    private double revenue;

    //@@vimalapugazhan
    public RevenueCommand (String task, LocalDate startDate, LocalDate endDate) {
        this.task = task;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //@@vimalapugazhan
    @Override
    public void execute() {
//        int numberOfOrders;
        switch (task) {
        case "today":
            revenue = TransactionList.calculateDayRevenue(LocalDate.now());
            break;
        case "total":
            revenue = TransactionList.calculateTotalRevenue();
            break;
        case "day":
            revenue = TransactionList.calculateDayRevenue(startDate);
            break;
        case "range":
            revenue = TransactionList.calculateRangeRevenue(startDate, endDate);
            break;
        default: assert task.isEmpty();
            break;
        }
    }

    @Override
    public boolean isQuit() {
        return false;
    }

    public double getRevenue() {
        return revenue;
    }
}
