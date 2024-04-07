package supertracker.command;

import java.time.LocalDate;
import java.util.ArrayList;

import supertracker.item.Item;
import supertracker.item.Transaction;
import supertracker.item.TransactionList;
import supertracker.ui.Ui;


public class RevenueCommand implements Command {
    private static final String SELL_FLAG = "s";
    private String task;
    private LocalDate startDate;
    private LocalDate endDate;
    private double revenue;


    //@@author vimalapugazhan
    public RevenueCommand (String task, LocalDate startDate, LocalDate endDate) {
        this.task = task;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //@@author vimalapugazhan
    @Override
    public void execute() {
        ArrayList<Transaction> filteredList = new ArrayList<>();
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
        filteredList = TransactionList.getFilteredTransactionList(task, startDate, endDate, SELL_FLAG);
        filteredList.sort(Item.sortByName());
        Ui.printRevenueExpenditure(task, revenue, startDate, endDate, "revenue", filteredList);
    }

    @Override
    public boolean isQuit() {
        return false;
    }
}
