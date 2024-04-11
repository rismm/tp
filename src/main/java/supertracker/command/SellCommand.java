package supertracker.command;

import supertracker.item.Transaction;
import supertracker.item.TransactionList;
import supertracker.storage.TransactionStorage;
import supertracker.ui.ErrorMessage;
import supertracker.ui.Ui;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Represents a command for selling items and removing them from the inventory.
 */
public class SellCommand extends RemoveCommand {
    private static final String SELL_FLAG = "s";
    private LocalDate currentDate;

    /**
     * Constructs a new SellCommand object with the specified name, quantity, and current date.
     *
     * @param name         Name of the item to be sold
     * @param quantity     Quantity of the item to be sold
     * @param currentDate  Current date of the transaction
     */
    public SellCommand(String name, int quantity, LocalDate currentDate) {
        super(name, quantity);
        this.currentDate = currentDate;
    }

    /**
     * Executes the SellCommand, including user interface interactions.
     * <p>
     * This method executes the SellCommand by first removing the specified quantity
     * of an item from the inventory using the {@code executeWithoutUi()} method inherited
     * from the RemoveCommand class. After that, it creates a new transaction record,
     * adds it to the transaction list, and informs the user about the success of the command
     * by displaying a success message containing the details of the sold item and transaction.
     */
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

        try {
            TransactionStorage.saveTransaction(transaction);
        } catch (IOException e) {
            Ui.printError(ErrorMessage.FILE_SAVE_ERROR);
        }
    }
}
