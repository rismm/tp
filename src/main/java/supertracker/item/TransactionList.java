package supertracker.item;

import java.time.LocalDate;
import java.util.ArrayList;

public class TransactionList {
    private static ArrayList<Transaction> transactionList = new ArrayList<>();
    private static final String SELL_FLAG = "s";

    public static Item get(int index) {
        return transactionList.get(index);
    }

    public static void add(Transaction transaction) {
        transactionList.add(transaction);
    }

    //@@vimalapugazhan
    public static double calculateRangeRevenue(LocalDate start, LocalDate end) {
        double revenue = 0;
//        int totalItemsSold = 0;
        for (Transaction transaction : transactionList) {
            LocalDate transactionDate = transaction.expiryDate;
            String transactionType = transaction.getType();
            if (transactionType.equals(SELL_FLAG) && transactionDate.isBefore(end) && transactionDate.isAfter(start)) {
                revenue += transaction.getPrice() * transaction.getQuantity();
//                totalItemsSold += transaction.getQuantity();
            }
        }
        return revenue;
    }

    //@@vimalapugazhan
    public static double calculateDayRevenue(LocalDate day) {
        double revenue = 0;
//        int totalItemsSold = 0;
        for (Transaction transaction : transactionList) {
            LocalDate transactionDate = transaction.expiryDate;
            String transactionType = transaction.getType();
            if (transactionType.equals(SELL_FLAG) && transactionDate.isEqual(day)) {
                revenue = transaction.getPrice() * transaction.getQuantity();
//                totalItemsSold += transaction.getQuantity();
            }
        }
        return revenue;
    }

    //@@vimalapugazhan
    public static double calculateTotalRevenue() {
        double revenue = 0;
//        int totalItemsSold = 0;
        for (Transaction transaction : transactionList) {
            LocalDate transactionDate = transaction.expiryDate;
            String transactionType = transaction.getType();
            if (transactionType.equals(SELL_FLAG)) {
                revenue = transaction.getPrice() * transaction.getQuantity();
//                totalItemsSold += transaction.getQuantity();
            }
        }
        return revenue;
    }
}
