package supertracker.storage;

import supertracker.item.Item;

import java.io.File;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FileManager {
    protected static final String DATA_PATH = "./data/";
    // Do note that the separator should also follow the file delimiter constant in the Parser class accordingly
    protected static final String SEPARATOR = " ,,, ";
    protected static final String PLACEHOLDER = "*&_";
    protected static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    protected static final String NO_DATE = "no date";
    protected static final DateTimeFormatter DATE_FORMAT_NULL = DateTimeFormatter.ofPattern("dd-MM-yyyyy");
    protected static final LocalDate UNDEFINED_DATE = LocalDate.parse("01-01-99999", DATE_FORMAT_NULL);

    /**
     * Checks whether the data directory exists.
     * If it does not exist, the directory will be created
     */
    protected static void checkDataDirectory() {
        File directory = new File(DATA_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    /**
     * Takes an item and returns a String array of the name, quantity and price of the item.
     * The last element in the array is a string to signify the end of the array, for use in the respective data to
     * string conversion methods.
     *
     * @param item an Item object to extract the name, quantity and price from
     * @return a String array of size 4 with item name, quantity, price and excess in this order
     */
    protected static String[] getNameQtyPriceStrings(Item item) {
        String name = item.getName();
        String excess = "end";
        // The item name should not contain the separator, but we perform another check
        // as an additional means of security.
        if (name.contains(SEPARATOR)) {
            excess = "bad end";
            name = name.replace(SEPARATOR, PLACEHOLDER);
        }

        String quantity = String.valueOf(item.getQuantity());
        String price = String.valueOf(item.getPrice());

        return new String[]{name, quantity, price, excess};
    }
}
