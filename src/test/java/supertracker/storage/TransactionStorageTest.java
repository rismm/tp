package supertracker.storage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import supertracker.item.Transaction;
import supertracker.item.TransactionList;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionStorageTest {
    private static final LocalDate CURR_DATE = LocalDate.now();

    @BeforeAll
    public static void setUp() throws IOException {
        TransactionList.clear();

        Transaction[] transactions = {
            new Transaction("apple", 10, 2.00, CURR_DATE, "b"),
            new Transaction("orange", 10, 2.00, CURR_DATE.minusDays(1), "b"),
            new Transaction("cool ,,, grapes", 50, 0.91, CURR_DATE, "s"),
            new Transaction("cool beans", 50, 12.21, CURR_DATE.plusMonths(1), "s"),
            new Transaction("cool,,,beans,,,,", 50, 12.21, CURR_DATE, "s"),
            new Transaction("cool beans", 50, 88.912, CURR_DATE, "s"),
            new Transaction("@#!!@#(         )*889.pp", 50, 88.912, CURR_DATE, "b"),
            new Transaction("@#!!@#(         )*889.pp", 50, 88.912, CURR_DATE.minusYears(1), "b")
        };

        for (Transaction t : transactions) {
            TransactionList.add(t);
        }

        TransactionStorage.resaveCurrentTransactions();
        TransactionList.clear();
    }

    @Test
    void loadTransactionData_validData_correctlyRead() throws IOException {
        TransactionStorage.loadTransactionData();

        Transaction[] transactions = {
            new Transaction("apple", 10, 2.00, CURR_DATE, "b"),
            new Transaction("orange", 10, 2.00, CURR_DATE.minusDays(1), "b"),
            new Transaction("cool*&_grapes", 50, 0.91, CURR_DATE, "s"),
            new Transaction("cool,,,beans,,,,", 50, 12.21, CURR_DATE, "s"),
            new Transaction("cool beans", 50, 88.912, CURR_DATE, "s"),
            new Transaction("@#!!@#(         )*889.pp", 50, 88.912, CURR_DATE, "b"),
            new Transaction("@#!!@#(         )*889.pp", 50, 88.912, CURR_DATE.minusYears(1), "b")
        };

        for (int i = 0; i < TransactionList.size(); i++) {
            Transaction loadedTransaction = TransactionList.get(i);
            assertEquals(transactions[i].getName(), loadedTransaction.getName());
            assertEquals(transactions[i].getQuantity(), loadedTransaction.getQuantity());
            assertEquals(transactions[i].getPrice(), loadedTransaction.getPrice());
            assertEquals(transactions[i].getTransactionDate(), loadedTransaction.getTransactionDate());
            assertEquals(transactions[i].getType(), loadedTransaction.getType());
        }

        TransactionList.clear();
        TransactionStorage.resaveCurrentTransactions();
    }
}
