package supertracker.item;

import java.util.ArrayList;

public class TransactionList {
    private static ArrayList<Transaction> transactionList = new ArrayList<>();

    public static Item get(int index) {
        return transactionList.get(index);
    }

    public static void add(Transaction transaction) {
        transactionList.add(transaction);
    }
}
