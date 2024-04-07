package supertracker.command;

import supertracker.item.Transaction;
import supertracker.item.TransactionList;

import java.time.LocalDate;

public class BuyCommand extends AddCommand {
    private static final String BUY_FLAG = "b";
    private double price;
    private LocalDate currentDate;

    public BuyCommand(String name, int quantity, double price, LocalDate currentDate) {
        super(name, quantity);
        this.price = price;
        this.currentDate = currentDate;
    }

    @Override
    public void execute() {
        super.execute();
        Transaction transaction = new Transaction(name, quantity, price, currentDate, BUY_FLAG, currentDate);
        TransactionList.add(transaction);
    }
}
