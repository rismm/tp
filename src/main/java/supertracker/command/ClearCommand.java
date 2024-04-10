package supertracker.command;

import supertracker.item.Transaction;
import supertracker.item.TransactionList;
import supertracker.ui.Ui;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Scanner;

public class ClearCommand implements Command {
    private static final String CLEAR_CONFIRM = "y";
    private LocalDate beforeDate;

    public ClearCommand(LocalDate beforeDate) {
        this.beforeDate = beforeDate;
    }

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
    }

    @Override
    public boolean isQuit() {
        return false;
    }
}
