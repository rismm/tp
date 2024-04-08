package supertracker.command;

import supertracker.ui.Ui;
import supertracker.item.Inventory;
import supertracker.item.Item;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListCommand implements Command {
    private static final String QUANTITY_FLAG = "q";
    private static final String PRICE_FLAG = "p";
    private static final String EX_DATE_FLAG = "e";
    private static final String ALPHABET = "";
    private String firstParam;
    private String secondParam;
    private String thirdParam;
    private String firstSortParam;
    private String secondSortParam;
    private String thirdSortParam;
    private boolean isReverse;

    public ListCommand(
        String firstParam,
        String secondParam,
        String thirdParam,
        String firstSortParam,
        String secondSortParam,
        String thirdSortParam,
        boolean isReverse
    ) {
        this.firstParam = firstParam;
        this.secondParam = secondParam;
        this.thirdParam = thirdParam;
        this.firstSortParam = firstSortParam;
        this.secondSortParam = secondSortParam;
        this.thirdSortParam = thirdSortParam;
        this.isReverse = isReverse;
    }

    @Override
    public void execute() {
        assert isValid(firstParam);
        assert isValid(secondParam);
        assert isValid(thirdParam);
        assert isValid(firstSortParam);
        assert isValid(secondSortParam);
        assert isValid(thirdSortParam);

        List<Item> items = Inventory.getItems();
        Ui.listIntro(items.size());

        sortBy(ALPHABET, items);
        sortBy(thirdSortParam, items);
        sortBy(secondSortParam, items);
        sortBy(firstSortParam, items);

        if (isReverse) {
            Collections.reverse(items);
        }

        int index = 1;
        for (Item item : items) {
            Ui.listItem(item, index, firstParam, secondParam, thirdParam);
            index++;
        }
    }

    private void sortBy(String sortParam, List<Item> items) {
        Comparator<Item> comparator;

        switch (sortParam) {
        case QUANTITY_FLAG:
            comparator = Item.sortByQuantity();
            break;
        case PRICE_FLAG:
            comparator = Item.sortByPrice();
            break;
        case EX_DATE_FLAG:
            comparator = Item.sortByDate();
            break;
        default:
            comparator = Item.sortByName();
            break;
        }

        items.sort(comparator);
    }

    @Override
    public boolean isQuit() {
        return false;
    }

    /**
     * Checks if the provided string is valid.
     *
     * @param s The string to be validated.
     * @return {@code true} if the string is equal to "q" or "p" or "e",
     *         or if the string is empty; {@code false} otherwise.
     */
    private boolean isValid(String s) {
        return s.equals(QUANTITY_FLAG) || s.equals(PRICE_FLAG) || s.equals(EX_DATE_FLAG) || s.isEmpty();
    }
}
