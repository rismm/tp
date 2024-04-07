package supertracker.item;

import java.time.LocalDate;
import java.util.ArrayList;

public class TransactionList {
    private static ArrayList<Transaction> transactionList = new ArrayList<>();

    public static Item get(int index) {
        return transactionList.get(index);
    }

    public static void add(Transaction transaction) {
        transactionList.add(transaction);
    }

    //@@vimalapugazhan
    public static double calculateRange(LocalDate start, LocalDate end, String flag) {
        double totalAmount = 0;
        for (Transaction transaction : transactionList) {
            LocalDate transactionDate = transaction.getTransactionDate();
            String transactionType = transaction.getType();
            if (transactionType.equals(flag) && transactionDate.isBefore(end) && transactionDate.isAfter(start)) {
                totalAmount += transaction.getPrice() * transaction.getQuantity();
            }
        }
        return totalAmount;
    }

    //@@vimalapugazhan
    public static double calculateDay(LocalDate day, String flag) {
        double totalAmount = 0;
        for (Transaction transaction : transactionList) {
            LocalDate transactionDate = transaction.getTransactionDate();
            String transactionType = transaction.getType();
            if (transactionType.equals(flag) && transactionDate.isEqual(day)) {
                totalAmount = transaction.getPrice() * transaction.getQuantity();
            }
        }
        return totalAmount;
    }

    //@@vimalapugazhan
    public static double calculateTotal(String flag) {
        double totalAmount = 0;
        for (Transaction transaction : transactionList) {
            String transactionType = transaction.getType();
            if (transactionType.equals(flag)) {
                totalAmount = transaction.getPrice() * transaction.getQuantity();
            }
        }
        return totalAmount;
    }
}