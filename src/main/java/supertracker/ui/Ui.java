package supertracker.ui;

import supertracker.item.Item;
import supertracker.item.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.HashSet;
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
    private static final String CLEAR_CONFIRMATION_FIRST_LINE =
            "Are you sure you want to clear all transactions before ";
    private static final String CLEAR_CONFIRMATION_SECOND_LINE = "Enter 'y' or 'Y' if you wish to proceed";
    private static final String CLEAR_CONFIRMATION_THIRD_LINE =
            "Enter anything else if you wish to cancel the clear operation";
    private static final String CLEAR_CANCELLED = "Clear operation has been cancelled";
    private static final DateTimeFormatter DATE_FORMAT_NULL = DateTimeFormatter.ofPattern("dd-MM-yyyyy");
    private static final DateTimeFormatter DATE_FORMAT_PRINT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final LocalDate UNDEFINED_DATE = LocalDate.parse("01-01-99999", DATE_FORMAT_NULL);
    private static final String TODAY = "today";
    private static final String TOTAL = "total";
    private static final String DAY = "day";
    private static final String RANGE = "range";
    private static final String PROFIT_MESSAGE = "Nice! You have a profit.";
    private static final String BREAK_EVEN_MESSAGE = "You have broken even.";
    private static final String LOSS_MESSAGE = "You lost money.";


    private static String getListSize(int size){
        return ("There are " + size + " unique items in your inventory:");
    }
  
    private static String getPriceMessage(Item item) {
        return "Price: " + item.getPriceString();
    }

    private static String getNameMessage(Item item) {
        return "Name: " + item.getName();
    }

    private static String getQuantityMessage(Item item) {
        return "Quantity: " + item.getQuantity();
    }

    private static String getNewItemOpening(Item item) {
        return item.getName() + " has been added to the inventory!";
    }

    /**
     * Formats the expiry date to be printed in the correct format and alignment.
     *
     * @param item Item from inventory.
     * @return Formatted expiry date message.
     */
    private static String getExpiryDateMessage(Item item) {
        if (!item.getExpiryDate().isEqual(UNDEFINED_DATE)) {
            return "Expiry Date: " + item.getExpiryDateString();
        }
        return EMPTY_STRING;
    }

    private static String updateItemOpening(Item item) {
        return item.getName() + " has been successfully updated!";
    }

    private static String renameItemOpening(Item item, String name) {
        return name + " has been successfully renamed to " + item.getName() + ".";
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

    private static String nothingRemovedOpening(Item item) {
        return "No " + item.getName() + " removed as you don't have any!";
    }

    private static String buyItemOpening(Transaction transaction) {
        return transaction.getQuantity() + " " + transaction.getName() + " bought at "
                + transaction.getPriceString() + " each for "
                + transaction.getTotalPriceString() + " in total";
    }

    private static String sellItemOpening(Transaction transaction) {
        return transaction.getQuantity() + " " + transaction.getName() + " sold at "
                + transaction.getPriceString() + " each for "
                + transaction.getTotalPriceString() + " in total";
    }

    private static String nothingSoldOpening(Item item) {
        return "No " + item.getName() + " sold as you don't have any!";
    }

    /**
     * Generates a message indicating the total number of items low on stock.
     *
     * @param quantity The quantity of items that are low on stock.
     * @return A string message describing the low stock situation.
     */
    private static String reportLowStockOpening(int quantity) {
        assert quantity >= 0;
        String isOrAre = quantity == 1 ? "is " : "are ";
        String plural = quantity == 1 ? EMPTY_STRING : "s";
        return "There " + isOrAre + quantity + " item" + plural + " low on stocks!";
    }

    /**
     * Generates a message indicating the total number of items close to expiry.
     *
     * @param quantity The quantity of items that are close to expiry.
     * @return A string message describing the items close to expiry.
     */
    private static String reportExpiryHasItemsOpening(int quantity) {
        String isOrAre = quantity == 1 ? "is " : "are ";
        String itemOrItems = quantity == 1 ? " item " : "items ";
        return "There " + isOrAre + quantity + itemOrItems +"close to expiry!";
    }

    /**
     * Generates a message indicating the total number of items that have expired.
     *
     * @param quantity The quantity of items that have expired.
     * @return A string message describing the items that have expired.
     */
    private static String reportExpiredHasItemsOpening(int quantity) {
        String isOrAre = quantity == 1 ? "is " : "are ";
        String itemOrItems = quantity == 1 ? " item " : " items ";
        return "There " + isOrAre + quantity + itemOrItems +"that " + isOrAre + "expired!";
    }

    /**
     * Generates a message detailing the name of an item with its count for the report.
     *
     * @param reportItem The item being reported.
     * @param count      The count or index of the item in the report list.
     * @return A string message describing the item's name.
     */
    private static String reportNameMessage(Item reportItem, int count) {
        return count + ". Name: " + reportItem.getName();
    }

    /**
     * Generates a message detailing the quantity of an item for the report.
     *
     * @param reportItem The item being reported.
     * @return A string message describing the item's quantity.
     */
    private static String reportQuantityMessage(Item reportItem) {
        return "   Quantity: " + reportItem.getQuantity();
    }

    /**
     * Generates a message detailing the expiry date of an item for the report.
     *
     * @param reportItem The item being reported.
     * @return A string message describing the item's expiry date.
     */
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

    public static void renameCommandSuccess(Item item, String name) {
        printIndent(renameItemOpening(item, name));
        printIndent(getNameMessage(item));
        printIndent(getQuantityMessage(item));
        printIndent(getPriceMessage(item));
        printIndent(getExpiryDateMessage(item));
    }

    /**
     * Prints message for successful deletion of item from inventory.
     *
     * @param name Name of item in inventory to be deleted.
     */
    public static void deleteCommandSuccess(String name) {
        printIndent(deleteItemOpening(name));
    }

    public static void addCommandSuccess(Item item, int quantityAdded) {
        assert quantityAdded >= 0;
        printIndent(addItemOpening(item, quantityAdded));
        printIndent(getQuantityMessage(item));
    }

    /**
     * Prints message for a successful removal of a specified quantity of the chosen item in the inventory.
     *
     * @param item Name of item in inventory whose quantity has been removed.
     * @param quantityRemoved The amount of quantity that has been removed from the item.
     */
    public static void removeCommandSuccess(Item item, int quantityRemoved) {
        assert quantityRemoved >= 0;
        if (quantityRemoved == 0) {
            printIndent(nothingRemovedOpening(item));
            return;
        }
        printIndent(removeItemOpening(item, quantityRemoved));
        printIndent(getQuantityMessage(item));
    }

    public static void buyCommandSuccess(Item item, Transaction transaction) {
        printIndent(buyItemOpening(transaction));
        printIndent(getQuantityMessage(item));
    }

    public static void sellCommandSuccess(Item item, Transaction transaction) {
        if (transaction.getQuantity() == 0) {
            printIndent(nothingSoldOpening(item));
            return;
        }
        printIndent(sellItemOpening(transaction));
        printIndent(getQuantityMessage(item));
    }

    /**
     * Generates a message for the report if the inventory has no items.
     */
    public static void reportNoItems() {
        printIndent(REPORT_INVENTORY_NO_ITEMS);
    }

    /**
     * Displays a success message based on the report type and the list of reported items.
     *
     * @param reportItems The list of items that meet the criteria for the reported.
     * @param reportType  The type of report (e.g., "low stock", "expiry", "expired").
     */
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

    /**
     * Displays a success message for an expiry report, listing items and their expiry dates that are close to the
     * expiry date (1 week before the expiry date).
     *
     * @param reportItems    The list of items that are close to expiry.
     * @param numReportItems The number of items in the report.
     */
    private static void expirySuccess(List<Item> reportItems, int numReportItems) {
        int count = 1;
        printIndent(reportExpiryHasItemsOpening(numReportItems));
        for (Item item : reportItems) {
            printIndent(reportNameMessage(item, count));
            printIndent(reportExpiryDateMessage(item));
            count += 1;
        }
    }

    /**
     * Displays a success message for items that have already expired, listing items and their expiry dates.
     *
     * @param reportItems    The list of items that have expired.
     * @param numReportItems The number of items in the report.
     */
    private static void expiredSuccess(List<Item> reportItems, int numReportItems) {
        int count = 1;
        printIndent(reportExpiredHasItemsOpening(numReportItems));
        for (Item item : reportItems) {
            printIndent(reportNameMessage(item, count));
            printIndent(reportExpiryDateMessage(item));
            count += 1;
        }
    }

    /**
     * Displays a success message for a low stock report, listing items and their quantities.
     *
     * @param reportItems    The list of items reported as low in stock.
     * @param numReportItems The number of items in the report.
     */
    private static void lowStockSuccess(List<Item> reportItems, int numReportItems) {
        int count = 1;
        printIndent(reportLowStockOpening(numReportItems));
        for (Item item : reportItems) {
            printIndent(reportNameMessage(item, count));
            printIndent(reportQuantityMessage(item));
            count += 1;
        }
    }

    /**
     * Displays the revenue/expenditure value and corresponding transactions
     * over a length of time according to the task, start and end dates.
     *
     * @param task          The task type (e.g., "today", "total", "day", "range").
     * @param amount        Revenue or expenditure value.
     * @param startDate     The start date for filtering transactions.
     * @param endDate       The end date for filtering transactions (used with "range" task).
     * @param financeType   Revenue or expenditure command.
     * @param filteredList  List of transaction items fitting the criteria that has been sorted alphabetically.
     */
    public static void printRevenueExpenditure(String task, BigDecimal amount, LocalDate startDate, LocalDate endDate,
                                               String financeType, ArrayList<Transaction> filteredList) {
        String amountString = String.format("%.2f", amount);
        switch (task) {
        case TODAY:
            printIndent("Today's " + financeType + " is $" + amountString);
            printFilteredList(filteredList);
            break;
        case TOTAL:
            printIndent("Total " + financeType + " is $" + amountString);
            printFilteredList(filteredList);
            break;
        case DAY:
            printIndent(financeType + " on " + startDate.format(DATE_FORMAT_PRINT) + " was $" + amountString);
            printFilteredList(filteredList);
            break;
        case RANGE:
            printIndent( financeType + " between " + startDate.format(DATE_FORMAT_PRINT) + " and "
                    + endDate.format(DATE_FORMAT_PRINT) + " was $" + amountString);
            printFilteredList(filteredList);
            break;
        default: assert task.isEmpty();
            break;
        }
    }

    /**
     * Displays the profit value over a length of time according to the task, start and end dates.
     *
     * @param task      The task type (e.g., "today", "total", "day", "range").
     * @param amount    Revenue or expenditure value.
     * @param startDate The start date for filtering transactions.
     * @param endDate   The end date for filtering transactions (used with "range" task)
     */
    public static void printProfit(String task, BigDecimal amount, LocalDate startDate, LocalDate endDate) {
        int profitSign = Integer.compare(amount.compareTo(BigDecimal.valueOf(0)), 0);
        String amountString = String.format("%.2f", amount);
        switch (task) {
        case TODAY:
            printIndent("Today's profit is $" + amountString);
            break;
        case TOTAL:
            printIndent("Total profit is $" + amountString);
            break;
        case DAY:
            printIndent("Your profit on " + startDate.format(DATE_FORMAT_PRINT) + " was $" + amountString);
            break;
        case RANGE:
            printIndent( "Your profit between " + startDate.format(DATE_FORMAT_PRINT) + " and "
                    + endDate.format(DATE_FORMAT_PRINT) + " was $" + amountString);
            break;
        default: assert task.isEmpty();
            break;
        }
        if (profitSign == 1) {
            printIndent(PROFIT_MESSAGE);
        } else if (profitSign == 0) {
            printIndent(BREAK_EVEN_MESSAGE);
        } else {
            printIndent(LOSS_MESSAGE);
        }
    }

    //@@ author dtaywd
    /**
     * Prints a formatted list of transactions with details including name, quantity, price, and transaction date.
     *
     * @param filteredList The list of transactions to be printed.
     */
    private static void printFilteredList(ArrayList<Transaction> filteredList) {
        int count = 1;
        for (Transaction transaction: filteredList) {
            String formattedTransactionDate = transaction.getTransactionDate().format(DATE_FORMAT_PRINT);
            printIndent(count + ". Name: " + transaction.getName());
            printIndent("   Quantity: " + transaction.getQuantity());
            printIndent("   Price: " + transaction.getPriceString());
            printIndent("   Transaction Date: " + formattedTransactionDate);
            count += 1;
        }
    }
    //@@author

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
        if (index == 1) {
            Ui.findIntro();
        }
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

    public static void clearCommandConfirmation(LocalDate beforeDate) {
        printIndent(CLEAR_CONFIRMATION_FIRST_LINE + beforeDate.format(DATE_FORMAT_PRINT) + "?");
        printIndent(CLEAR_CONFIRMATION_SECOND_LINE);
        printIndent(CLEAR_CONFIRMATION_THIRD_LINE);
        printLine();
    }

    public static void clearCommandCancelled() {
        printIndent(CLEAR_CANCELLED);
    }

    public static void clearCommandSuccess(int transactionsCleared, LocalDate beforeDate) {
        String dateString = beforeDate.format(DATE_FORMAT_PRINT);
        if (transactionsCleared == 0) {
            printIndent("Nothing cleared. No transactions before " + dateString + " available to clear");
            return;
        }
        String plural = transactionsCleared == 1 ? "" : "s";
        printIndent(transactionsCleared + " transaction" + plural
                + " before " + dateString + " successfully cleared!");
    }

    public static void printDuplicatesInSavefile(HashSet<String> duplicates) {
        StringBuilder duplicateItemNames = new StringBuilder();
        for (String dup : duplicates) {
            duplicateItemNames.append(dup);
            duplicateItemNames.append(", ");
        }
        printIndent("SuperTracker has detected the following duplicate item names in the save file:");
        printIndent(duplicateItemNames.toString());
        printIndent("Only the latest occurrence of data lines with these item names in");
        printIndent("the save file will be loaded into the inventory list.");
    }
}
