package supertracker.command;

import supertracker.item.Item;
import supertracker.item.Transaction;
import supertracker.item.TransactionList;
import supertracker.ui.Ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

// @@ author dtaywd
/**
 * Represents a command to calculate and display expenditure information based on specified criteria.
 */
public class ExpenditureCommand implements Command {
    private static final String BUY_FLAG = "b";
    private LocalDate startDate;
    private LocalDate endDate;
    private String task;
    private BigDecimal expenditure;

    /**
     * Constructs an ExpenditureCommand with the specified task, start date, and end date.
     *
     * @param task      The task type (e.g., "today", "total", "day", "range").
     * @param startDate The start date for filtering transactions.
     * @param endDate   The end date for filtering transactions (used with "range" task).
     */
    public ExpenditureCommand(String task, LocalDate startDate, LocalDate endDate) {
        this.task = task;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Executes the expenditure command based on the specified task.
     * Calculates expenditure and displays relevant information using UI utilities.
     */
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

        ArrayList<Transaction> filteredList = TransactionList.getFilteredTransactionList(
                task, startDate, endDate, BUY_FLAG);
        filteredList.sort(Item.sortByDate());
        Collections.reverse(filteredList);
        Ui.printRevenueExpenditure(task, expenditure, startDate, endDate, "expenditure", filteredList);
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
