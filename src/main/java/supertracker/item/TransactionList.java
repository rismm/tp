package supertracker.item;

import java.util.ArrayList;

public class TransactionList {
    private static ArrayList<Item> transactionList = new ArrayList<>();

    public static Item get(int index) {
        return transactionList.get(index);
    }

    public static void add(Item item) {
        transactionList.add(item);
    }
}
