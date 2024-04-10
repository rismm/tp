//@@author vimalapugazhan
package supertracker.command;

import supertracker.item.TransactionList;
import supertracker.ui.Ui;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ProfitCommand implements Command{
    private static final String BUY_FLAG = "b";
    private static final String SELL_FLAG = "s";
    private String task;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal profit;

    public ProfitCommand (String task, LocalDate startDate, LocalDate endDate) {
        this.task = task;
        this.startDate = startDate;
        this.endDate = endDate;
    }

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
        profit = revenue.subtract(expenditure);
        Ui.printProfit(task, profit, startDate, endDate);
    }

    @Override
    public boolean isQuit() {
        return false;
    }
}
