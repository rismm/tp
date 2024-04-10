package supertracker.item;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

public class TransactionList {
    private static final String TODAY = "today";
    private static final String TOTAL = "total";
    private static final String DAY = "day";
    private static final String RANGE = "range";
    private static ArrayList<Transaction> transactionList = new ArrayList<>();

    public static Transaction get(int index) {
        return transactionList.get(index);
    }

    public static void add(Transaction transaction) {
        transactionList.add(transaction);
    }

    public static int size() {
        return transactionList.size();
    }

    public static void clear() {
        transactionList.clear();
    }

    public static Iterator<Transaction> iterator() {
        return transactionList.iterator();
    }

    //@@author vimalapugazhan
    public static BigDecimal calculateRange(LocalDate start, LocalDate end, String flag) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Transaction transaction : transactionList) {
            LocalDate transactionDate = transaction.getTransactionDate();
            String transactionType = transaction.getType();
            if (transactionType.equals(flag) && transactionDate.isBefore(end) && transactionDate.isAfter(start)) {
                BigDecimal newAmount = transaction.getTotalPrice();
                totalAmount = totalAmount.add(newAmount);
            }
        }
        return totalAmount;
    }

    public static BigDecimal calculateDay(LocalDate day, String flag) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Transaction transaction : transactionList) {
            LocalDate transactionDate = transaction.getTransactionDate();
            String transactionType = transaction.getType();
            if (transactionType.equals(flag) && transactionDate.isEqual(day)) {
                BigDecimal newAmount = transaction.getTotalPrice();
                totalAmount = totalAmount.add(newAmount);
            }
        }
        return totalAmount;
    }

    public static BigDecimal calculateTotal(String flag) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Transaction transaction : transactionList) {
            String transactionType = transaction.getType();
            if (transactionType.equals(flag)) {
                BigDecimal newAmount = transaction.getTotalPrice();
                totalAmount = totalAmount.add(newAmount);
            }
        }
        return totalAmount;
    }
    //@@author

    // @@ author dtaywd
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

    // @@ author dtaywd
    private static void getTotalTransactionList(String flag, ArrayList<Transaction> filteredList) {
        for (Transaction transaction : transactionList) {
            String transactionType = transaction.getType();
            if (transactionType.equals(flag)) {
                filteredList.add(transaction);
            }
        }
    }

    // @@ author dtaywd
    private static void getDayTransactionList(LocalDate start, String flag, ArrayList<Transaction> filteredList) {
        for (Transaction transaction : transactionList) {
            LocalDate transactionDate = transaction.getTransactionDate();
            String transactionType = transaction.getType();
            if (transactionType.equals(flag) && transactionDate.isEqual(start)) {
                filteredList.add(transaction);
            }
        }
    }

    // @@ author dtaywd
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

