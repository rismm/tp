package supertracker.command;

import supertracker.item.Transaction;
import supertracker.item.TransactionList;
import supertracker.ui.Ui;

import java.time.LocalDate;

public class SellCommand extends RemoveCommand {
    private static final String SELL_FLAG = "s";
    private LocalDate currentDate;

    public SellCommand(String name, int quantity, LocalDate currentDate) {
        super(name, quantity);
        this.currentDate = currentDate;
    }

    @Override
    public void execute() {
        super.executeWithoutUi();
        Transaction transaction = new Transaction(
            newItem.getName(),
            quantityRemoved,
            newItem.getPrice(),
            currentDate,
            SELL_FLAG
        );
        TransactionList.add(transaction);
        Ui.sellCommandSuccess(newItem, transaction);
    }
}
