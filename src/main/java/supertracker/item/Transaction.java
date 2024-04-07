package supertracker.item;

import java.time.LocalDate;

public class Transaction extends Item {
    private String type;

    public Transaction(String name, int quantity, double price, LocalDate expiryDate, String type) {
        super(name, quantity, price, expiryDate);
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
