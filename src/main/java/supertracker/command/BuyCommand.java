package supertracker.command;

import supertracker.item.Transaction;
import supertracker.item.TransactionList;
import supertracker.storage.TransactionStorage;
import supertracker.ui.ErrorMessage;
import supertracker.ui.Ui;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Represents a command for buying items and adding them to the inventory.
 */
public class BuyCommand extends AddCommand {
    private static final String BUY_FLAG = "b";
    private double price;
    private LocalDate currentDate;

    /**
     * Constructs a new BuyCommand object with the specified name, quantity, price, and current date.
     *
     * @param name         Name of the item to be bought
     * @param quantity     Quantity of the item to be bought
     * @param price        Price of each unit of the item
     * @param currentDate  Current date of the transaction
     */
    public BuyCommand(String name, int quantity, double price, LocalDate currentDate) {
        super(name, quantity);
        this.price = price;
        this.currentDate = currentDate;
    }

    /**
     * Executes the BuyCommand, including user interface interactions.
     * <p>
     * This method executes the BuyCommand by first adding the specified quantity
     * of an item to the inventory using the {@code executeWithoutUi()} method inherited
     * from the AddCommand class. After that, it creates a new transaction record,
     * adds it to the transaction list, and informs the user about the success of the command
     * by displaying a success message containing the details of the bought item and transaction.
     */
    @Override
    public void execute() {
        super.executeWithoutUi();
        Transaction transaction = new Transaction(newItem.getName(), quantity, price, currentDate, BUY_FLAG);
        TransactionList.add(transaction);
        Ui.buyCommandSuccess(newItem, transaction);

        try {
            TransactionStorage.saveTransaction(transaction);
        } catch (IOException e) {
            Ui.printError(ErrorMessage.FILE_SAVE_ERROR);
        }
    }
}
