package supertracker.ui;

import supertracker.item.Item;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.List;

public class Ui {
    private static final String LINE = "    --------------------------------------------------------------------------";
    private static final String QUANTITY_FLAG = "q";
    private static final String PRICE_FLAG = "p";
    private static final String EX_DATE_FLAG = "e";
    private static final String EMPTY_LIST_MESSAGE = "Nothing to list! No items in inventory!";
    private static final String SINGLE_ITEM_LIST_MESSAGE= "There is 1 unique item in your inventory:";
    private static final String INVALID_COMMAND_MESSAGE = "Sorry! Invalid command!";
    private static final String WELCOME_MESSAGE = "Hello, welcome to SuperTracker, how may I help you?";
    private static final String FAREWELL_MESSAGE = "Goodbye!";
    private static final String BASIC_ERROR_MESSAGE = "Oh no! An error has occurred in your input";
    private static final String FIND_OPENING_MESSAGE = "Here are your found items:";
    private static final String REPORT_NO_ITEMS_OPENING = "There are no items that fit the criteria!";

    private static String listSize(int size){
        return ("There are " + size + " unique items in your inventory:");
    }

    private static String priceMessage(Item item) {
        return "Price: " + item.getPriceString();
    }

    private static String quantityMessage(Item item) {
        return "Quantity: " + item.getQuantity();
    }

    private static String newItemOpening(Item item) {
        return item.getName() + " has been added to the inventory!";
    }

    private static final DateTimeFormatter DATE_FORMAT_PRINT  = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static String expiryDateMessage(Item item) {
        return "Expiry Date: " + item.getExpiryDate().format(DATE_FORMAT_PRINT);
    }

    protected static final DateTimeFormatter DATE_FORMAT_NULL = DateTimeFormatter.ofPattern("dd-MM-yyyyy");

    protected static final LocalDate DATE_NOT_EXIST = LocalDate.parse("01-01-99999", DATE_FORMAT_NULL);

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

    private static String reportLowStockOpening(Item reportItem, int count) {
        return count + ". Name: " + reportItem.getName();
    }

    private static String reportLowStockQuantityMessage(Item reportItem) {
        return "   Current Quantity: " + reportItem.getQuantity();
    }

    public static void printIndent(String string) {
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
        printIndent(newItemOpening(item));
        printIndent(quantityMessage(item));
        printIndent(priceMessage(item));
        try {
            if (!item.getExpiryDate().isEqual(DATE_NOT_EXIST)) {
                printIndent(expiryDateMessage(item));
            }
        } catch (NullPointerException e) {
            assert (item.getExpiryDate().isEqual(null));
        }
    }

    public static void updateCommandSuccess(Item item) {
        printIndent(updateItemOpening(item));
        printIndent(quantityMessage(item));
        printIndent(priceMessage(item));
        printIndent(expiryDateMessage(item));
    }

    public static void deleteCommandSuccess(String name) {
        printIndent(deleteItemOpening(name));
    }

    public static void addCommandSuccess(Item item, int quantityAdded) {
        assert quantityAdded >= 0;
        printIndent(addItemOpening(item, quantityAdded));
        printIndent(quantityMessage(item));
    }

    public static void removeCommandSuccess(Item item, int quantityRemoved) {
        assert quantityRemoved >= 0;
        printIndent(removeItemOpening(item, quantityRemoved));
        printIndent(quantityMessage(item));
    }

    public static void reportCommandSuccess(List<Item> reportItems, String reportType) {
        if (reportItems.isEmpty()) {
            printIndent(REPORT_NO_ITEMS_OPENING);
        } else {
            int count = 1;
            for (Item item : reportItems) {
                if (reportType.equals("low stock")) {
                    printIndent(reportLowStockOpening(item, count));
                    printIndent(reportLowStockQuantityMessage(item));
                }
                count += 1;
            }
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
        printIndent(listSize(size));
    }

    public static void findIntro() {
        printIndent(FIND_OPENING_MESSAGE);
    }

    public static void listItem(Item item, int index, boolean hasQuantity, boolean hasPrice,
            boolean hasExpiry, String firstParam, String secondParam) {

        String stringToPrint = index + ". Name: " + item.getName();
        String quantityString = "    Quantity: " + item.getQuantity();
        String priceString = "    Price: " + item.getPriceString();
        String expiryString;
        if (!item.getExpiryDate().isEqual(DATE_NOT_EXIST)) {
            expiryString = "    Expiry Date: " + item.getExpiryDate().format(DATE_FORMAT_PRINT);
        } else {
            expiryString = "";
        }
        stringToPrint = getStringToPrint(hasQuantity, hasPrice, hasExpiry, firstParam, secondParam,
                stringToPrint, quantityString, priceString, expiryString);
        printIndent(stringToPrint);
    }

    private static String getStringToPrint(boolean hasQuantity, boolean hasPrice, boolean hasExpiry, String firstParam,
            String secondParam, String stringToPrint, String quantityString, String priceString, String expiryString) {

        if (hasQuantity && hasPrice && hasExpiry) {
            switch (firstParam) {
            case QUANTITY_FLAG:
                if (secondParam.equals(PRICE_FLAG)) {
                    stringToPrint += (quantityString + priceString + expiryString);
                } else {
                    stringToPrint += (quantityString + expiryString + priceString);
                }
                break;
            case PRICE_FLAG:
                if (secondParam.equals(QUANTITY_FLAG)) {
                    stringToPrint += (priceString + quantityString + expiryString);
                } else {
                    stringToPrint += (priceString + expiryString + quantityString);
                }
                break;
            case EX_DATE_FLAG:
                if (secondParam.equals(quantityString)) {
                    stringToPrint += (expiryString + quantityString + priceString);
                } else {
                    stringToPrint += (expiryString + priceString + quantityString);
                }
                break;
            default:
                return stringToPrint;       //review
            }
        } else if (hasQuantity && hasPrice) {
            if (firstParam.equals(QUANTITY_FLAG)) {
                stringToPrint += (quantityString + priceString);
            } else {
                stringToPrint += (priceString + quantityString);
            }
        } else if (hasQuantity && hasExpiry) {
            if (firstParam.equals(QUANTITY_FLAG)) {
                stringToPrint += (quantityString + expiryString);
            } else {
                stringToPrint += (expiryString + quantityString);
            }
        } else if (hasPrice && hasExpiry) {
            if (firstParam.equals(PRICE_FLAG)) {
                stringToPrint += (priceString + expiryString);
            } else {
                stringToPrint += (expiryString + priceString);
            }
        } else if (hasQuantity) {
            stringToPrint += quantityString;
        } else if (hasPrice) {
            stringToPrint += priceString;
        } else if (hasExpiry) {
            stringToPrint += expiryString;
        }
        return stringToPrint;
    }

    public static void printError(String errorMessage) {
        printIndent(BASIC_ERROR_MESSAGE);
        printIndent(errorMessage);
    }

    public static void foundItem(Item item, int index) {
        String stringToPrint = index + ". Name: " + item.getName();
        String quantityString = "    Quantity: " + item.getQuantity();
        String priceString = "    Price: " + item.getPriceString();

        stringToPrint += (priceString + quantityString);
        printIndent(stringToPrint);
    }

    public static void noItemFound(String name) {
        String stringToPrint = "So sorry, Your item: " + name + " could not be found.";
        printIndent(stringToPrint);
    }
}
