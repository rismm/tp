package supertracker.command;

import java.time.LocalDate;

import supertracker.item.TransactionList;
import supertracker.ui.Ui;


public class RevenueCommand implements Command {
    private String task;

    private LocalDate startDate;
    private LocalDate endDate;
    private double revenue;
    private static final String SELL_FLAG = "s";

    //@@vimalapugazhan
    public RevenueCommand (String task, LocalDate startDate, LocalDate endDate) {
        this.task = task;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //@@vimalapugazhan
    @Override
    public void execute() {
        switch (task) {
        case "today":
            revenue = TransactionList.calculateDay(LocalDate.now(), SELL_FLAG);
            break;
        case "total":
            revenue = TransactionList.calculateTotal(SELL_FLAG);
            break;
        case "day":
            revenue = TransactionList.calculateDay(startDate, SELL_FLAG);
            break;
        case "range":
            revenue = TransactionList.calculateRange(startDate, endDate, SELL_FLAG);
            break;
        default: assert task.isEmpty();
            break;
        }
        Ui.printRevenue(task, revenue, startDate, endDate);
    }

    @Override
    public boolean isQuit() {
        return false;
    }
}
