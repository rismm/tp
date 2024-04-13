package supertracker.storage;

import supertracker.TrackerException;
import supertracker.item.Transaction;
import supertracker.item.TransactionList;
import supertracker.ui.ErrorMessage;
import supertracker.ui.Ui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class TransactionStorage extends FileManager {
    protected static final String SAVE_FILE_NAME = "transactions.txt";
    protected static final String FILE_PATH = DATA_PATH + SAVE_FILE_NAME;
    protected static final int MAX_NUMBER_OF_PARAMS = 6;
    protected static final int NAME_INDEX = 0;
    protected static final int QUANTITY_INDEX = 1;
    protected static final int PRICE_INDEX = 2;
    protected static final int DATE_INDEX = 3;
    protected static final int TYPE_INDEX = 4;
    protected static final int EXTRA_INDEX = 3;
    protected static final String[] PARAM_LABELS = {"NAME: ", "QTY: ", "PRICE: ", "DATE: ", "T: "};

    /**
     * Saves a new transaction by writing into a text file. If the text file does not exist, all transactional data
     * currently in the system will be saved.
     *
     * @throws IOException if text file cannot be opened or accessed for whatever reason
     */
    public static void saveTransaction(Transaction newTransaction) throws IOException {
        checkDataDirectory();

        File saveFile = new File(FILE_PATH);

        if (!saveFile.exists()) {
            saveFile.createNewFile();
            saveAllTransactions();
            return;
        }

        FileWriter fw = new FileWriter(saveFile, true);
        BufferedWriter writer = new BufferedWriter(fw);
        String newData = getTransactionData(newTransaction);
        writer.write(newData);

        writer.close();
        fw.close();
    }

    /**
     * Saves all transactional data currently in the transaction list. A public method to call saveAllTransactions().
     *
     * @throws IOException if text file cannot be opened or accessed for whatever reason
     */
    public static void resaveCurrentTransactions() throws IOException {
        checkDataDirectory();

        File saveFile = new File(FILE_PATH);
        if (!saveFile.exists()) {
            saveFile.createNewFile();
        }
        saveAllTransactions();
    }

    /**
     * Saves all transactional data in the transaction list. Assumes that the save file already exists.
     *
     * @throws IOException if text file cannot be opened or accessed for whatever reason
     */
    private static void saveAllTransactions() throws IOException {
        File saveFile = new File(FILE_PATH);
        FileWriter fw = new FileWriter(saveFile);
        BufferedWriter writer = new BufferedWriter(fw);
        assert saveFile.exists();

        int transactionSize = TransactionList.size();
        for (int i = 0; i < transactionSize; i++) {
            String transactionData = getTransactionData(TransactionList.get(i));
            writer.write(transactionData);
        }

        writer.close();
        fw.close();
    }

    /**
     * Takes a Transaction object and converts its attributes to a String to be saved in a data file.
     * String is in the format of "NAME: (name) DELIMITER QTY: (qty) DELIMITER PRICE: (price) DELIMITER DATE: (date)
     * DELIMITER T: (type) DELIMITER end"
     *
     * @param transaction a Transaction object to convert its attributes to a String
     * @return a String containing the Transaction object's attributes
     */
    private static String getTransactionData(Transaction transaction) {
        String[] itemDataStrings = getNameQtyPriceStrings(transaction);
        assert itemDataStrings.length == 4;

        String name = itemDataStrings[NAME_INDEX];
        String excess = itemDataStrings[EXTRA_INDEX];
        String quantity = itemDataStrings[QUANTITY_INDEX];
        String price = itemDataStrings[PRICE_INDEX];

        assert !transaction.getTransactionDate().isEqual(UNDEFINED_DATE);
        String date = transaction.getTransactionDate().format(DATE_FORMAT);

        return "NAME: " + name + SEPARATOR + "QTY: " + quantity + SEPARATOR + "PRICE: " + price
                + SEPARATOR + "DATE: " + date + SEPARATOR + "T: " + transaction.getType() + SEPARATOR + excess
                + System.lineSeparator();
    }

    /**
     * Loads and reads transaction data from a designated text file from the path specified in the class.
     * Parses each line of data into a Transaction class and adds to the item list in the TransactionList class.
     * If data is corrupted, prints to the UI that there are corrupted lines.
     * If the transaction date parsed is a date that is larger than the current date, prints to the UI a message
     * to alert the user that the date is not a possible transactional date.
     *
     * @throws IOException if specified path is unable to be opened or found
     */
    public static void loadTransactionData() throws IOException {
        checkDataDirectory();

        File saveFile = new File(FILE_PATH);
        if (!saveFile.exists()) {
            return;
        }

        Scanner fileScanner = new Scanner(saveFile);
        String transactionData;
        boolean hasCorruptedData = false;
        boolean hasDateAfterToday = false;
        while (fileScanner.hasNext()) {
            try {
                transactionData = fileScanner.nextLine();
                Transaction transaction = parseTransactionData(transactionData);
                TransactionList.add(transaction);
            } catch (TrackerException e) {
                hasCorruptedData = true;
                hasDateAfterToday = true;
            } catch (Exception e) {
                hasCorruptedData = true;
            }
        }
        if (hasCorruptedData) {
            Ui.printError(ErrorMessage.TRANSACTION_FILE_CORRUPTED_ERROR);
            if (hasDateAfterToday) {
                Ui.printIndent(ErrorMessage.TRANSACTION_DATE_LOAD_ERROR);
            }
            saveAllTransactions();
        }
        fileScanner.close();
    }

    /**
     * Takes string data from a line extracted from the data file and parses it to a Transaction object
     *
     * @param transactionData a String containing the data of a Transaction object's attributes
     * @return a Transaction object parsed from the data string
     * @throws Exception if the relevant attributes are unable to be extracted from the data string
     * @throws TrackerException if the transaction date extracted from the string data is of a date that has not
     * occurred yet
     */
    private static Transaction parseTransactionData(String transactionData) throws Exception {
        String[] data = transactionData.split(SEPARATOR, MAX_NUMBER_OF_PARAMS);

        if (data.length < MAX_NUMBER_OF_PARAMS) {
            throw new Exception();
        }
        for (int i = 0; i < MAX_NUMBER_OF_PARAMS - 1; i++) {
            if (!data[i].startsWith(PARAM_LABELS[i])) {
                throw new Exception();
            }
            data[i] = data[i].substring(data[i].indexOf(" ")).trim();
        }

        String name = data[NAME_INDEX];

        int quantity = Integer.parseInt(data[QUANTITY_INDEX]);
        double price = Double.parseDouble(data[PRICE_INDEX]);
        if (quantity < 0 && price < 0) {
            throw new Exception();
        }

        LocalDate transactionDate = LocalDate.parse(data[DATE_INDEX], DATE_FORMAT);
        if (transactionDate.isAfter(LocalDate.now())) {
            throw new TrackerException(ErrorMessage.TRANSACTION_FILE_CORRUPTED_ERROR);
        }

        if (!data[TYPE_INDEX].equals("b") && !data[TYPE_INDEX].equals("s")) {
            throw new Exception();
        }

        return new Transaction(name, quantity, price, transactionDate, data[TYPE_INDEX]);
    }
}
