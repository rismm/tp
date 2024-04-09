package supertracker.storage;

import supertracker.item.Inventory;
import supertracker.item.Item;
import supertracker.ui.ErrorMessage;
import supertracker.ui.Ui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ItemStorage extends FileManager {
    protected static final String SAVE_FILE_NAME = "items.txt";
    protected static final String FILE_PATH = DATA_PATH + SAVE_FILE_NAME;
    protected static final int MAX_NUMBER_OF_PARAMS = 5;
    protected static final int NAME_INDEX = 0;
    protected static final int QUANTITY_INDEX = 1;
    protected static final int PRICE_INDEX = 2;
    protected static final int DATE_INDEX = 3;
    protected static final int EXTRA_INDEX = 3;

    /**
     * Saves all items currently in the inventory by writing into a text file.
     *
     * @throws IOException if text file cannot be opened or accessed for whatever reason
     */
    public static void saveData() throws IOException {
        checkDataDirectory();
        File saveFile = new File(FILE_PATH);
        if (!saveFile.createNewFile()) {
            saveFile.delete();
            saveFile.createNewFile();
        }

        List<Item> items = Inventory.getItems();
        FileWriter fw = new FileWriter(saveFile);
        BufferedWriter writer = new BufferedWriter(fw);

        for (Item item : items) {
            String itemData = getItemData(item);
            writer.write(itemData);
        }

        writer.close();
        fw.close();
    }

    /**
     * Loads and reads item data from a designated text file from the path specified in the class.
     * Parses each line of data into an Item class and adds to the item list in the Inventory class.
     * If data is corrupted, prints to the UI the number of corrupted lines.
     *
     * @throws IOException if specified path is unable to be opened or found
     */
    public static void loadData() throws IOException {
        checkDataDirectory();

        File saveFile = new File(FILE_PATH);
        if (!saveFile.exists()) {
            return;
        }

        Inventory.clear();
        Scanner fileScanner = new Scanner(saveFile);
        String itemData;
        boolean hasCorruptedData = false;
        while (fileScanner.hasNext()) {
            try {
                itemData = fileScanner.nextLine();
                Item item = parseItemData(itemData);
                Inventory.put(item.getName(), item);
            } catch (Exception e) {
                hasCorruptedData = true;
            }
        }
        if (hasCorruptedData) {
            Ui.printError(ErrorMessage.ITEM_FILE_CORRUPTED_ERROR);
            saveData();
        }
        fileScanner.close();
    }

    private static String getItemData(Item item) {
        String[] itemDataStrings = getNameQtyPriceStrings(item);
        assert itemDataStrings.length == 4;

        String name = itemDataStrings[NAME_INDEX];
        String excess = itemDataStrings[EXTRA_INDEX];
        String quantity = itemDataStrings[QUANTITY_INDEX];
        String price = itemDataStrings[PRICE_INDEX];

        LocalDate exDate = item.getExpiryDate();
        String date = NO_DATE;
        if (!exDate.isEqual(UNDEFINED_DATE)) {
            date = exDate.format(DATE_FORMAT);
        }

        return name + SEPARATOR + quantity + SEPARATOR + price + SEPARATOR
                + date + SEPARATOR + excess + System.lineSeparator();
    }

    private static Item parseItemData(String itemData) throws Exception {
        String[] data = itemData.split(SEPARATOR, MAX_NUMBER_OF_PARAMS);

        if (data.length < MAX_NUMBER_OF_PARAMS) {
            throw new Exception();
        }
        assert data.length == MAX_NUMBER_OF_PARAMS;

        String name = data[NAME_INDEX].trim();

        int quantity;
        double price;
        quantity = Integer.parseInt(data[QUANTITY_INDEX].trim());
        price = Double.parseDouble(data[PRICE_INDEX].trim());
        if (quantity < 0 && price < 0) {
            throw new Exception();
        }

        LocalDate date = UNDEFINED_DATE;
        if (!data[DATE_INDEX].equals(NO_DATE)) {
            date = LocalDate.parse(data[DATE_INDEX], DATE_FORMAT);
        }

        return new Item(name, quantity, price, date);
    }
}
