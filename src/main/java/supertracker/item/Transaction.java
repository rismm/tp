package supertracker.item;

import java.time.LocalDate;

public class Transaction extends Item {
    private String type;

    public Transaction(String name, int quantity, double price, LocalDate transactionDate, String type) {
        super(name, quantity, price, transactionDate);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public LocalDate getTransactionDate() {
        return getExpiryDate();
    }

    public String getTotalPriceString() {
        double totalPrice = quantity * price;
        return "$" + String.format("%.2f", totalPrice);
    }
}
