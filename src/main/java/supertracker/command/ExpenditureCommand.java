package supertracker.command;

import supertracker.item.Transaction;
import supertracker.item.TransactionList;

import java.time.LocalDate;


public class ExpenditureCommand implements Command {
    private static final String BUY_FLAG = "b";
    private LocalDate from;
    private LocalDate to;
    private String task;

    public ExpenditureCommand(String task, LocalDate startDate, LocalDate endDate) {
        this.task = task;
        this.from = startDate;
        this.to = endDate;
    }

    @Override
    public void execute() {
        switch (task) {
        case "today":
            todayExpenditure();
            break;

        case "total":
            totalExpenditure();
            break;

        case "day":
            dayExpenditure(to);
            break;

        case "range":
            rangeExpenditure(to, from);
            break;

        default:
            assert task.isEmpty();
            break;
        }
    }

    private void todayExpenditure() {
        LocalDate currDate = LocalDate.now();
        double expenditure = TransactionList.calculateDay(currDate, BUY_FLAG);

    }

    private void totalExpenditure() {
        double expenditure = TransactionList.calculateTotal(BUY_FLAG);
    }

    private void dayExpenditure(LocalDate day) {
        double expenditure = TransactionList.calculateDay(day, BUY_FLAG);

    }

    private void rangeExpenditure(LocalDate to, LocalDate from) {
        double expenditure = TransactionList.calculateRange(to, from, BUY_FLAG);
    }

    @Override
    public boolean isQuit() {
        return false;
    }
}
