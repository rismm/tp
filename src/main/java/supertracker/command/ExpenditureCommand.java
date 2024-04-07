package supertracker.command;

import supertracker.item.TransactionList;
import supertracker.ui.Ui;

import java.time.LocalDate;


public class ExpenditureCommand implements Command {
    private static final String BUY_FLAG = "b";
    private LocalDate from;
    private LocalDate to;
    private String task;

    private double expenditure;

    public ExpenditureCommand(String task, LocalDate startDate, LocalDate endDate) {
        this.task = task;
        this.from = startDate;
        this.to = endDate;
    }

    @Override
    public void execute() {

        switch (task) {
        case "today":
            LocalDate currDate = LocalDate.now();
            expenditure = TransactionList.calculateDay(currDate, BUY_FLAG);
            break;

        case "total":
            expenditure = TransactionList.calculateTotal(BUY_FLAG);
            break;

        case "day":
            expenditure = TransactionList.calculateDay(from, BUY_FLAG);
            break;

        case "range":
            expenditure = TransactionList.calculateRange(to, from, BUY_FLAG);
            break;

        default:
            assert task.isEmpty();
            break;
        }
        Ui.printRevenueExpenditure(task, expenditure, to, from, "expenditure");
    }

    @Override
    public boolean isQuit() {
        return false;
    }
}
