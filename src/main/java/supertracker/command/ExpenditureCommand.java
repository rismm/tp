package supertracker.command;

import supertracker.item.Item;
import supertracker.item.Transaction;
import supertracker.item.TransactionList;
import supertracker.ui.Ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;


// @@ author dtaywd
public class ExpenditureCommand implements Command {
    private static final String BUY_FLAG = "b";
    private LocalDate startDate;
    private LocalDate endDate;
    private String task;

    private BigDecimal expenditure;

    public ExpenditureCommand(String task, LocalDate startDate, LocalDate endDate) {
        this.task = task;
        this.startDate = startDate;
        this.endDate = endDate;
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
            expenditure = TransactionList.calculateDay(startDate, BUY_FLAG);
            break;

        case "range":
            expenditure = TransactionList.calculateRange(startDate, endDate, BUY_FLAG);
            break;

        default:
            assert task.isEmpty();
            break;
        }
        ArrayList<Transaction> filteredList = TransactionList.getFilteredTransactionList(task, startDate, endDate, BUY_FLAG);
        filteredList.sort(Item.sortByName());

        Ui.printRevenueExpenditure(task, expenditure, startDate, endDate, "expenditure", filteredList);
    }

    @Override
    public boolean isQuit() {
        return false;
    }
}
