package supertracker.command;

import supertracker.item.Transaction;
import supertracker.item.TransactionList;

import java.time.LocalDate;

public class SellCommand extends RemoveCommand {
    private static final String SELL_FLAG = "s";
    private double price;
    private LocalDate currentDate;

    public SellCommand(String name, int quantity, double price, LocalDate currentDate) {
        super(name, quantity);
        this.price = price;
        this.currentDate = currentDate;
    }

    @Override
    public void execute() {
        super.execute();
        Transaction transaction = new Transaction(name, quantity, price, currentDate, SELL_FLAG);
        TransactionList.add(transaction);
    }
}
