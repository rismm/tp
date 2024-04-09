package supertracker.command;

import supertracker.item.Transaction;
import supertracker.item.TransactionList;
import supertracker.storage.FileManager;
import supertracker.ui.ErrorMessage;
import supertracker.ui.Ui;

import java.io.IOException;
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

        try {
            FileManager.saveTransaction(transaction);
        } catch (IOException e) {
            Ui.printError(ErrorMessage.FILE_SAVE_ERROR);
        }
    }
}
