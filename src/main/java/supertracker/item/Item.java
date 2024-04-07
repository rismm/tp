package supertracker.item;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class Item {
    private static final DateTimeFormatter DATE_FORMAT_PRINT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    protected String name;
    protected int quantity;
    protected double price;
    protected LocalDate expiryDate;

    public Item(String name, int quantity, double price, LocalDate expiryDate) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.expiryDate = expiryDate;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public String getPriceString() {
        return "$" + String.format("%.2f", price);
    }

    public String getExpiryDateString() {
        return expiryDate.format(DATE_FORMAT_PRINT);
    }

    public static Comparator<Item> sortByName() {
        return Comparator.comparing(Item::getName, String.CASE_INSENSITIVE_ORDER);
    }

    public static Comparator<Item> sortByQuantity() {
        return Comparator.comparingInt(Item::getQuantity);
    }

    public static Comparator<Item> sortByPrice() {
        return Comparator.comparingDouble(Item::getPrice);
    }

    public static Comparator<Item> sortByExpiry() {
        return Comparator.comparing(Item::getExpiryDate);
    }
}
