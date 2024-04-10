package supertracker.item;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;

/**
 * Represents an item in the inventory.
 */
public class Item {
    private static final DateTimeFormatter DATE_FORMAT_PRINT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    protected String name;
    protected int quantity;
    protected double price;
    protected LocalDate expiryDate;

    /**
     * Constructs an Item with the specified attributes.
     *
     * @param name       Name of the item.
     * @param quantity   Quantity of the item.
     * @param price      Price of the item.
     * @param expiryDate Expiry date of the item.
     */
    public Item(String name, int quantity, double price, LocalDate expiryDate) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.expiryDate = expiryDate;
    }

    /**
     * Retrieves the name of the item.
     *
     * @return Name of the item.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the quantity of the item.
     *
     * @return Quantity of the item.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Retrieves the price of the item.
     *
     * @return Price of the item.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Retrieves the expiry date of the item.
     *
     * @return Expiry date of the item.
     */
    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    /**
     * Retrieves the price of the item as a formatted string.
     *
     * @return Formatted price of the item.
     */
    public String getPriceString() {
        return "$" + String.format("%.2f", price);
    }

    /**
     * Retrieves the expiry date of the item as a formatted string.
     *
     * @return Formatted expiry date of the item.
     */
    public String getExpiryDateString() {
        return expiryDate.format(DATE_FORMAT_PRINT);
    }

    /**
     * Comparator for sorting items by name.
     *
     * @return Comparator for sorting items by name.
     */
    public static Comparator<Item> sortByName() {
        return Comparator.comparing(Item::getName, String.CASE_INSENSITIVE_ORDER);
    }

    /**
     * Comparator for sorting items by quantity.
     *
     * @return Comparator for sorting items by quantity.
     */
    public static Comparator<Item> sortByQuantity() {
        return Comparator.comparingInt(Item::getQuantity);
    }

    /**
     * Comparator for sorting items by price.
     *
     * @return Comparator for sorting items by price.
     */
    public static Comparator<Item> sortByPrice() {
        return Comparator.comparingDouble(Item::getPrice);
    }

    /**
     * Comparator for sorting items by expiry date.
     *
     * @return Comparator for sorting items by expiry date.
     */
    public static Comparator<Item> sortByDate() {
        return Comparator.comparing(Item::getExpiryDate);
    }
}
