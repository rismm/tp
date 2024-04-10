//@@author vimalapugazhan
package supertracker.command;

import supertracker.item.TransactionList;
import supertracker.ui.Ui;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents a command to calculate and display profit information based on specified criteria.
 */
public class ProfitCommand implements Command{
    private static final String BUY_FLAG = "b";
    private static final String SELL_FLAG = "s";
    private String task;
    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * Constructs an ProfitCommand with the specified task, start date, and end date.
     *
     * @param task      The task type (e.g., "today", "total", "day", "range").
     * @param startDate The start date for filtering transactions.
     * @param endDate   The end date for filtering transactions (used with "range" task).
     */
    public ProfitCommand (String task, LocalDate startDate, LocalDate endDate) {
        this.task = task;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Executes the profit command based on the specified task.
     * Calculates profit and displays relevant information using UI utilities.
     */
    @Override
    public void execute() {
        BigDecimal revenue = BigDecimal.valueOf(0);
        BigDecimal expenditure = BigDecimal.valueOf(0);
        switch (task) {
        case "today":
            revenue = TransactionList.calculateDay(LocalDate.now(), SELL_FLAG);
            expenditure = TransactionList.calculateDay(LocalDate.now(), BUY_FLAG);
            break;
        case "total":
            revenue = TransactionList.calculateTotal(SELL_FLAG);
            expenditure = TransactionList.calculateTotal(BUY_FLAG);
            break;
        case "day":
            revenue = TransactionList.calculateDay(startDate, SELL_FLAG);
            expenditure = TransactionList.calculateDay(startDate, BUY_FLAG);
            break;
        case "range":
            revenue = TransactionList.calculateRange(startDate, endDate, SELL_FLAG);
            expenditure = TransactionList.calculateRange(startDate, endDate, BUY_FLAG);
            break;
        default: assert task.isEmpty();
            break;
        }
        BigDecimal profit = revenue.subtract(expenditure);
        Ui.printProfit(task, profit, startDate, endDate);
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
