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
    /**
     * Retrieves a filtered list of transactions based on the specified type, start date, and end date.
     *
     * @param type  The type of transaction filter ('today', 'total', 'day', or 'range').
     * @param start The start date for filtering transactions (used in 'day' and 'range' types).
     * @param end   The end date for filtering transactions (used in 'range' type).
     * @param flag  The flag representing the transaction type to filter (e.g., 'b' for buy transactions).
     * @return An ArrayList of transactions filtered based on the specified criteria.
     */
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
    //@@author

    // @@ author dtaywd
    /**
     * Retrieves all transactions of a specific type and adds them to the filtered list.
     *
     * @param flag          The flag representing the transaction type to filter.
     * @param filteredList  The list to which filtered transactions will be added.
     */
    private static void getTotalTransactionList(String flag, ArrayList<Transaction> filteredList) {
        for (Transaction transaction : transactionList) {
            String transactionType = transaction.getType();
            if (transactionType.equals(flag)) {
                filteredList.add(transaction);
            }
        }
    }
    //@@author

    // @@ author dtaywd
    /**
     * Retrieves transactions occurring on a specific day of a given type and adds them to the filtered list.
     *
     * @param start         The date to filter transactions for.
     * @param flag          The flag representing the transaction type to filter.
     * @param filteredList  The list to which filtered transactions will be added.
     */
    private static void getDayTransactionList(LocalDate start, String flag, ArrayList<Transaction> filteredList) {
        for (Transaction transaction : transactionList) {
            LocalDate transactionDate = transaction.getTransactionDate();
            String transactionType = transaction.getType();
            if (transactionType.equals(flag) && transactionDate.isEqual(start)) {
                filteredList.add(transaction);
            }
        }
    }
    //@@author

    // @@ author dtaywd
    /**
     * Retrieves transactions occurring within a specified date range of a given type
     * and adds them to the filtered list.
     *
     * @param start         The start date of the date range to filter transactions for (not inclusive).
     * @param end           The end date of the date range to filter transactions for (not inclusive).
     * @param flag          The flag representing the transaction type to filter.
     * @param filteredList  The list to which filtered transactions will be added.
     */
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
    //@@author
}

