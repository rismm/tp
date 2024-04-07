package supertracker.item;

import java.time.LocalDate;
import java.util.ArrayList;

public class TransactionList {
    private static final String TODAY = "today";
    private static final String TOTAL = "total";
    private static final String DAY = "day";
    private static final String RANGE = "range";
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
                totalAmount += transaction.getPrice() * transaction.getQuantity();
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
                totalAmount += transaction.getPrice() * transaction.getQuantity();
            }
        }
        return totalAmount;
    }

    public static ArrayList<Transaction> getFilteredTransactionList(String type, LocalDate start, LocalDate end,
                                                                 String flag) {
        ArrayList<Transaction> filteredList= new ArrayList<>();
        LocalDate currDate = LocalDate.now();
        switch (type) {
        case TODAY:
            getDayTransactionList(currDate, flag, filteredList);
            break;
        case TOTAL:
            getTotalTransactionList(flag, filteredList);
            break;
        case DAY:
            getDayTransactionList(start, flag, filteredList);
            break;
        case RANGE:
            getRangeTransactionList(start, end, flag, filteredList);
            break;
        default:
            assert type.isEmpty();
            break;
        }
        return filteredList;
    }

    private static void getTotalTransactionList(String flag, ArrayList<Transaction> filteredList) {
        for (Transaction transaction : transactionList) {
            String transactionType = transaction.getType();
            if (transactionType.equals(flag)) {
                filteredList.add(transaction);
            }
        }
    }

    private static void getDayTransactionList(LocalDate start, String flag, ArrayList<Transaction> filteredList) {
        for (Transaction transaction : transactionList) {
            LocalDate transactionDate = transaction.getTransactionDate();
            String transactionType = transaction.getType();
            if (transactionType.equals(flag) && transactionDate.isEqual(start)) {
                filteredList.add(transaction);
            }
        }
    }

    private static void getRangeTransactionList(LocalDate start, LocalDate end, String flag,
                                                ArrayList<Transaction> filteredList) {
        for (Transaction transaction : transactionList) {
            LocalDate transactionDate = transaction.getTransactionDate();
            String transactionType = transaction.getType();
            if (transactionType.equals(flag) && transactionDate.isBefore(end) && transactionDate.isAfter(start)) {
                filteredList.add(transaction);
            }
        }
    }
}

