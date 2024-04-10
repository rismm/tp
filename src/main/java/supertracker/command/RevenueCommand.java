//@@author vimalapugazhan
package supertracker.command;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import supertracker.item.Item;
import supertracker.item.Transaction;
import supertracker.item.TransactionList;
import supertracker.ui.Ui;

/**
 * Represents a command to calculate and display revenue information based on specified criteria.
 */
public class RevenueCommand implements Command {
    private static final String SELL_FLAG = "s";
    private String task;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal revenue;

    /**
     * Constructs an RevenueCommand with the specified task, start date, and end date.
     *
     * @param task      The task type (e.g., "today", "total", "day", "range").
     * @param startDate The start date for filtering transactions.
     * @param endDate   The end date for filtering transactions (used with "range" task).
     */
    public RevenueCommand (String task, LocalDate startDate, LocalDate endDate) {
        this.task = task;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Executes the revenue command based on the specified task.
     * Calculates revenue and displays relevant information using UI utilities.
     */
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
        ArrayList<Transaction> filteredList = TransactionList.getFilteredTransactionList(
                task, startDate, endDate, SELL_FLAG);
        filteredList.sort(Item.sortByDate());
        Collections.reverse(filteredList);
        Ui.printRevenueExpenditure(task, revenue, startDate, endDate, "revenue", filteredList);
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
