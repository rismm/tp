package supertracker.command;

import supertracker.ui.Ui;
import supertracker.item.Inventory;
import supertracker.item.Item;

import java.util.Collections;
import java.util.List;

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
    private boolean reverse;

    public ListCommand(boolean hasQuantity, boolean hasPrice, boolean hasExpiry,
            String firstParam, String secondParam, String sortBy, boolean reverse) {
        this.hasQuantity = hasQuantity;
        this.hasPrice = hasPrice;
        this.hasExpiry = hasExpiry;
        this.firstParam = firstParam;
        this.secondParam = secondParam;
        this.sortBy = sortBy;
        this.reverse = reverse;
    }

    @Override
    public void execute() {
        assert isValid(firstParam);
        assert isValid(secondParam);
        assert isValid(sortBy);

        int index = 1;
        List<Item> items = Inventory.getItems();
        Ui.listIntro(items.size());

        switch (sortBy) {
        case QUANTITY_FLAG:
            items.sort(Item.sortByQuantity());
            break;
        case PRICE_FLAG:
            items.sort(Item.sortByPrice());
            break;
        case EX_DATE_FLAG:
            items.sort(Item.sortByExpiry());
            break;
        default:
            items.sort(Item.sortByName());
            break;
        }

        if (reverse) {
            Collections.reverse(items);
        }

        for (Item item : items) {
            Ui.listItem(item, index, hasQuantity, hasPrice, hasExpiry, firstParam, secondParam);
            index++;
        }
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
