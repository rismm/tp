package supertracker.ui;

import supertracker.item.Item;
import supertracker.item.Transaction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

public class Ui {
    private static final String LINE = "    --------------------------------------------------------------------------";
    private static final String QUANTITY_FLAG = "q";
    private static final String PRICE_FLAG = "p";
    private static final String EX_DATE_FLAG = "e";
    private static final String EMPTY_STRING = "";
    private static final String EMPTY_LIST_MESSAGE = "Nothing to list! No items in inventory!";
    private static final String SINGLE_ITEM_LIST_MESSAGE= "There is 1 unique item in your inventory:";
    private static final String INVALID_COMMAND_MESSAGE = "Sorry! Invalid command!";
    private static final String WELCOME_MESSAGE = "Hello, welcome to SuperTracker, how may I help you?";
    private static final String FAREWELL_MESSAGE = "Goodbye!";
    private static final String BASIC_ERROR_MESSAGE = "Oh no! An error has occurred";
    private static final String FIND_OPENING_MESSAGE = "Here are your found items:";
    private static final String REPORT_LOW_STOCK_NO_ITEMS_OPENING = "There are no items that fit the criteria!";
    private static final String REPORT_EXPIRY_NO_ITEMS_OPENING = "There are no items close to expiry!";
    private static final String REPORT_EXPIRED_NO_ITEMS_OPENING = "There are no items that are expired!";
    private static final String REPORT_INVENTORY_NO_ITEMS = "There are no items in the inventory, " +
            "please consider adding some in!";
    private static final DateTimeFormatter DATE_FORMAT_NULL = DateTimeFormatter.ofPattern("dd-MM-yyyyy");
    private static final DateTimeFormatter DATE_FORMAT_PRINT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final LocalDate UNDEFINED_DATE = LocalDate.parse("01-01-99999", DATE_FORMAT_NULL);
    private static final String TODAY = "today";
    private static final String TOTAL = "total";
    private static final String DAY = "day";
    private static final String RANGE = "range";
    private static final double ROUNDING_FACTOR = 100.0;

    private static String getListSize(int size){
        return ("There are " + size + " unique items in your inventory:");
    }
  
    private static String getPriceMessage(Item item) {
        return "Price: " + item.getPriceString();
    }

    private static String getQuantityMessage(Item item) {
        return "Quantity: " + item.getQuantity();
    }

    private static String getNewItemOpening(Item item) {
        return item.getName() + " has been added to the inventory!";
    }

    private static String getExpiryDateMessage(Item item) {
        if (!item.getExpiryDate().isEqual(UNDEFINED_DATE)) {
            return "Expiry Date: " + item.getExpiryDateString();
        }
        return EMPTY_STRING;
    }

    private static String updateItemOpening(Item item) {
        return item.getName() + " has been successfully updated!";
    }

    private static String deleteItemOpening(String name) {
        return name + " has been deleted!";
    }

    private static String addItemOpening(Item item, int quantityAdded) {
        return quantityAdded + " " + item.getName() + " added to inventory!";
    }

    private static String removeItemOpening(Item item, int quantityRemoved) {
        return quantityRemoved + " " + item.getName() + " removed from inventory!";
    }

    private static String reportLowStockOpening(int quantity) {
        assert quantity >= 0;
        String isOrAre = quantity == 1 ? "is " : "are ";
        return "There " + isOrAre + quantity + " items low on stocks!";
    }
    private static String reportExpiryHasItemsOpening(int quantity) {
        String isOrAre = quantity == 1 ? "is " : "are ";
        String itemOrItems = quantity == 1 ? " item " : "items ";
        return "There " + isOrAre + quantity + itemOrItems +"close to expiry!";
    }

    private static String reportExpiredHasItemsOpening(int quantity) {
        String isOrAre = quantity == 1 ? "is " : "are ";
        String itemOrItems = quantity == 1 ? " item " : " items ";
        return "There " + isOrAre + quantity + itemOrItems +"that " + isOrAre + "expired!";
    }

    private static String reportNameMessage(Item reportItem, int count) {
        return count + ". Name: " + reportItem.getName();
    }

    private static String reportQuantityMessage(Item reportItem) {
        return "   Quantity: " + reportItem.getQuantity();
    }

    private static String reportExpiryDateMessage(Item reportItem) {
        return "   Expiry Date: " + reportItem.getExpiryDateString();
    }

    public static void printIndent(String string) {
        if (string.isEmpty()) {
            return;
        }
        System.out.println("     " + string);
    }

    public static void printLine() {
        System.out.println(LINE);
    }

    public static void greetUser() {
        printLine();
        printIndent(WELCOME_MESSAGE);
        printLine();
    }

    public static void sayGoodbye() {
        printIndent(FAREWELL_MESSAGE);
    }

    public static void printInvalidCommand() {
        printIndent(INVALID_COMMAND_MESSAGE);
    }

    public static void newCommandSuccess(Item item) {
        printIndent(getNewItemOpening(item));
        printIndent(getQuantityMessage(item));
        printIndent(getPriceMessage(item));
        printIndent(getExpiryDateMessage(item));
    }

    public static void updateCommandSuccess(Item item) {
        printIndent(updateItemOpening(item));
        printIndent(getQuantityMessage(item));
        printIndent(getPriceMessage(item));
        printIndent(getExpiryDateMessage(item));
    }

    public static void deleteCommandSuccess(String name) {
        printIndent(deleteItemOpening(name));
    }

    public static void addCommandSuccess(Item item, int quantityAdded) {
        assert quantityAdded >= 0;
        printIndent(addItemOpening(item, quantityAdded));
        printIndent(getQuantityMessage(item));
    }

    public static void removeCommandSuccess(Item item, int quantityRemoved) {
        assert quantityRemoved >= 0;
        printIndent(removeItemOpening(item, quantityRemoved));
        printIndent(getQuantityMessage(item));
    }

    public static void reportNoItems() {
        printIndent(REPORT_INVENTORY_NO_ITEMS);
    }

    public static void reportCommandSuccess(List<Item> reportItems, String reportType) {
        int numReportItems = reportItems.size();
        switch (reportType) {
        case "low stock":
            if (reportItems.isEmpty()) {
                printIndent(REPORT_LOW_STOCK_NO_ITEMS_OPENING);
            } else {
                lowStockSuccess(reportItems, numReportItems);
            }
            break;
        case "expiry":
            if (reportItems.isEmpty()) {
                printIndent(REPORT_EXPIRY_NO_ITEMS_OPENING);
            } else {
                expirySuccess(reportItems, numReportItems);
            }
            break;
        case "expired":
            if (reportItems.isEmpty()) {
                printIndent(REPORT_EXPIRED_NO_ITEMS_OPENING);
            } else {
                expiredSuccess(reportItems, numReportItems);
            }
            break;
        default:
            assert reportType.isEmpty();
            break;
        }
    }

    private static void expirySuccess(List<Item> reportItems, int numReportItems) {
        int count = 1;
        printIndent(reportExpiryHasItemsOpening(numReportItems));
        for (Item item : reportItems) {
            printIndent(reportNameMessage(item, count));
            printIndent(reportExpiryDateMessage(item));
            count += 1;
        }
    }

    private static void expiredSuccess(List<Item> reportItems, int numReportItems) {
        int count = 1;
        printIndent(reportExpiredHasItemsOpening(numReportItems));
        for (Item item : reportItems) {
            printIndent(reportNameMessage(item, count));
            printIndent(reportExpiryDateMessage(item));
            count += 1;
        }
    }

    private static void lowStockSuccess(List<Item> reportItems, int numReportItems) {
        int count = 1;
        printIndent(reportLowStockOpening(numReportItems));
        for (Item item : reportItems) {
            printIndent(reportNameMessage(item, count));
            printIndent(reportQuantityMessage(item));
            count += 1;
        }
    }

    //@@vimalapugazhan
    public static void printRevenueExpenditure(String task, double amount, LocalDate startDate, LocalDate endDate,
                                               String financeType, ArrayList<Transaction> filteredList) {
        amount = roundTo2Dp(amount);
        switch (task) {
        case TODAY:
            printIndent("Today's " + financeType + " is $" + amount);
            printFilteredList(filteredList);
            break;
        case TOTAL:
            printIndent("Total  " + financeType + "  is $" + amount);
            printFilteredList(filteredList);
            break;
        case DAY:
            printIndent(financeType + " on " + startDate.format(DATE_FORMAT_PRINT) + " was $" + amount);
            printFilteredList(filteredList);
            break;
        case RANGE:
            printIndent( financeType + " between " + startDate.format(DATE_FORMAT_PRINT) + " and "
                    + endDate.format(DATE_FORMAT_PRINT) + " was $" + amount);
            printFilteredList(filteredList);
            break;
        default: assert task.isEmpty();
            break;
        }
    }

    private static void printFilteredList(ArrayList<Transaction> filteredList) {
        int count = 1;
        for (Transaction transaction: filteredList) {
            String formattedTransactionDate = transaction.getTransactionDate().format(DATE_FORMAT_PRINT);
            printIndent(count + ". Name: " + transaction.getName());
            printIndent("   Quantity: " + transaction.getQuantity());
            printIndent("   Price: " + transaction.getPrice());
            printIndent("   Transaction Date: " + formattedTransactionDate);
            count += 1;
        }
    }

    public static void listIntro(int size) {
        assert size >= 0;
        if (size == 0) {
            printIndent(EMPTY_LIST_MESSAGE);
            return;
        }
        if (size == 1) {
            printIndent(SINGLE_ITEM_LIST_MESSAGE);
            return;
        }
        printIndent(getListSize(size));
    }

    public static void findIntro() {
        printIndent(FIND_OPENING_MESSAGE);
    }

    public static void listItem(Item item, int index, String firstParam, String secondParam, String thirdParam) {
        String nameString = index + ". Name: " + item.getName();
        String quantityString = "    Quantity: " + item.getQuantity();
        String priceString = "    Price: " + item.getPriceString();
        String expiryString;
        if (!item.getExpiryDate().isEqual(UNDEFINED_DATE)) {
            expiryString = "    Expiry Date: " + item.getExpiryDateString();
        } else {
            expiryString = EMPTY_STRING;
        }
        String itemString = getItemString(firstParam, secondParam, thirdParam,
                nameString, quantityString, priceString, expiryString);
        printIndent(itemString);
    }

    private static String buildItemString(
        String param,
        String itemString,
        String quantityString,
        String priceString,
        String expiryString
    ) {
        switch (param) {
        case QUANTITY_FLAG:
            itemString += quantityString;
            break;
        case PRICE_FLAG:
            itemString += priceString;
            break;
        case EX_DATE_FLAG:
            itemString += expiryString;
            break;
        default:
            assert param.isEmpty();
            break;
        }
        return itemString;
    }

    private static String getItemString(
        String firstParam,
        String secondParam,
        String thirdParam,
        String nameString,
        String quantityString,
        String priceString,
        String expiryString
    ) {
        String itemString = nameString;
        itemString = buildItemString(firstParam, itemString, quantityString, priceString, expiryString);
        itemString = buildItemString(secondParam, itemString, quantityString, priceString, expiryString);
        itemString = buildItemString(thirdParam, itemString, quantityString, priceString, expiryString);
        return itemString;
    }

    public static void printError(String errorMessage) {
        printIndent(BASIC_ERROR_MESSAGE);
        printIndent(errorMessage);
    }

    public static void printFoundItem(Item item, int index) {
        String stringToPrint = index + ". Name: " + item.getName();
        printIndent(stringToPrint);
        String quantityString = "   Quantity: " + item.getQuantity();
        printIndent(quantityString);
        String priceString = "   Price: " + item.getPriceString();
        printIndent(priceString);
        if (!item.getExpiryDate().isEqual(UNDEFINED_DATE)) {
            printIndent("   Expiry Date: " + item.getExpiryDateString());
        }
    }

    public static void printNoItemFound(String name) {
        String stringToPrint = "So sorry, Your item: " + name + " could not be found.";
        printIndent(stringToPrint);
    }

    public static void printItemNameLimitation(String name, String delimiter, String newName) {
        String nameOutputString = padStringWithQuotes(name, true);
        String delimiterOutputString = padStringWithQuotes(delimiter, false);
        String newNameOutputString = padStringWithQuotes(newName, false);
        printIndent("It appears that the input item name, " + nameOutputString);
        printIndent("contains the program's file delimiter, " + delimiterOutputString);
        printIndent("Unfortunately due to system limitations, " + nameOutputString);
        printIndent("will be renamed and saved as " + newNameOutputString);
        printIndent("Please avoid using the file delimiter in your item names" + System.lineSeparator());
    }

    private static String padStringWithQuotes(String name, boolean hasComma) {
        String end = hasComma ? "\"," : "\"";
        return "\"" + name + end;
    }

    private static double roundTo2Dp(double unroundedValue) {
        return Math.round(unroundedValue * ROUNDING_FACTOR) / ROUNDING_FACTOR;
    }
}
