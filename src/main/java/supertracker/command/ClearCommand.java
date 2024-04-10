package supertracker.command;

import supertracker.item.Transaction;
import supertracker.item.TransactionList;
import supertracker.storage.TransactionStorage;
import supertracker.ui.ErrorMessage;
import supertracker.ui.Ui;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Represents a command for clearing transactions before a specified date.
 */
public class ClearCommand implements Command {
    private static final String CLEAR_CONFIRM = "y";
    private LocalDate beforeDate;

    /**
     * Constructs a new ClearCommand object with the specified date.
     *
     * @param beforeDate  Date before which transactions should be cleared
     */
    public ClearCommand(LocalDate beforeDate) {
        this.beforeDate = beforeDate;
    }

    /**
     * Executes the ClearCommand
     * <p>
     * This method confirms with the user whether they want to clear transactions
     * before the specified date. If confirmed, it removes transactions from the transaction list
     * that occurred before the specified date. It then informs the user about the success of the command
     * by displaying a success message containing the number of transactions cleared and the specified date.
     * </p>
     */
    @Override
    public void execute() {
        Ui.clearCommandConfirmation(beforeDate);
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        Ui.printLine();

        if (!input.equalsIgnoreCase(CLEAR_CONFIRM)) {
            Ui.clearCommandCancelled();
            return;
        }

        int oldTransactionListSize = TransactionList.size();

        Iterator<Transaction> iterator = TransactionList.iterator();
        while (iterator.hasNext()) {
            Transaction transaction = iterator.next();
            if (transaction.getTransactionDate().isBefore(beforeDate)) {
                iterator.remove();
            }
        }

        int newTransactionListSize = TransactionList.size();
        int transactionsCleared = oldTransactionListSize - newTransactionListSize;
        Ui.clearCommandSuccess(transactionsCleared, beforeDate);

        try {
            TransactionStorage.resaveCurrentTransactions();
        } catch (IOException e) {
            Ui.printError(ErrorMessage.FILE_SAVE_ERROR);
        }
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
