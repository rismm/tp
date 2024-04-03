package supertracker.command;

import supertracker.ui.Ui;
import supertracker.item.Inventory;
import supertracker.item.Item;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ListCommand implements Command {
    private static final String QUANTITY_FLAG = "q";
    private static final String PRICE_FLAG = "p";
    private static final String EX_DATE_FLAG = "e";
    private boolean hasQuantity;
    private boolean hasPrice;
    private boolean hasExpiry;
    private String firstParam;
    private String secondParam;
    private String sortBy;
    private boolean isReverse;
    private static final DateTimeFormatter DATE_FORMAT_NULL = DateTimeFormatter.ofPattern("dd-MM-yyyyy");
    private static final LocalDate UNDEFINED_DATE = LocalDate.parse("01-01-99999", DATE_FORMAT_NULL);

    public ListCommand(boolean hasQuantity, boolean hasPrice, boolean hasExpiry,
            String firstParam, String secondParam, String sortBy, boolean isReverse) {

        this.hasQuantity = hasQuantity;
        this.hasPrice = hasPrice;
        this.hasExpiry = hasExpiry;
        this.firstParam = firstParam;
        this.secondParam = secondParam;
        this.sortBy = sortBy;
        this.isReverse = isReverse;
    }

    @Override
    public void execute() {
        assert isValid(firstParam);
        assert isValid(secondParam);
        assert isValid(sortBy);

        int index = 1;
        List<Item> items = Inventory.getItems();
        Ui.listIntro(items.size());

        Comparator<Item> comparator;

        if (!sortBy.equals(EX_DATE_FLAG)) {
            switch (sortBy) {
            case QUANTITY_FLAG:
                comparator = Item.sortByQuantity();
                break;
            case PRICE_FLAG:
                comparator = Item.sortByPrice();
                break;
            default:
                comparator = Item.sortByName();
                break;
            }

            items.sort(comparator);

            if (isReverse) {
                Collections.reverse(items);
            }
        } else {
            items = sortByExpiry(items);
        }

        for (Item item : items) {
            Ui.listItem(item, index, hasQuantity, hasPrice, hasExpiry, firstParam, secondParam);
            index++;
        }
    }
    public List<Item> sortByExpiry(List<Item> items) throws NullPointerException {
        Comparator<Item> nameComparator, expiryComparator;
        List<Item> itemsWithExpiry = new ArrayList<>();
        List<Item> itemsWithoutExpiry = new ArrayList<>();
        nameComparator = Item.sortByName();
        expiryComparator = Item.sortByExpiry();
        for (Item item: items) {
            if(item.getExpiryDate().isEqual(UNDEFINED_DATE)) {
                itemsWithoutExpiry.add(item);
            } else {
                itemsWithExpiry.add(item);
            }
        }

        itemsWithExpiry.sort(expiryComparator);
        itemsWithoutExpiry.sort(nameComparator);
        if (isReverse) {
            Collections.reverse(itemsWithExpiry);
        }

        itemsWithExpiry.addAll(itemsWithoutExpiry);
        return itemsWithExpiry;
    }

    @Override
    public boolean isQuit() {
        return false;
    }

    /**
     * Checks if the provided string is valid.
     *
     * @param s The string to be validated.
     * @return {@code true} if the string is equal to "q" or "p",
     *         or if the string is empty; {@code false} otherwise.
     */
    private boolean isValid(String s) {
        return s.equals(QUANTITY_FLAG) || s.equals(PRICE_FLAG) || s.equals(EX_DATE_FLAG) || s.isEmpty();
    }
}
