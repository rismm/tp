package supertracker.storage;

import supertracker.item.Transaction;
import supertracker.item.TransactionList;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TransactionStorage extends FileManager {
    protected static final String SAVE_FILE_NAME = "transactions.txt";
    protected static final String FILE_PATH = DATA_PATH + SAVE_FILE_NAME;

    public static void saveTransaction(Transaction newTransaction) throws IOException {
        checkDataDirectory();

        File saveFile = new File(FILE_PATH);
        FileWriter fw = new FileWriter(saveFile, true);
        BufferedWriter writer = new BufferedWriter(fw);
        if (saveFile.exists()) {
            String newData = getTransactionData(newTransaction);
            writer.write(newData);
            writer.close();
            fw.close();
            return;
        }

        int transactionSize = TransactionList.getLength();
        for (int i = 0; i < transactionSize; i++) {
            String transactionData = getTransactionData(TransactionList.get(i));
            writer.write(transactionData);
        }

        writer.close();
        fw.close();
    }

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
     * Loads and reads data from a designated text file from the path specified in the class.
     *
     * @throws IOException if specified path is unable to be opened or found
     */
    private static void loadTransactionData() throws IOException {
        checkDataDirectory();

        File saveFile = new File(FILE_PATH);
        if (!saveFile.exists()) {
            return;
        }
    }
}
