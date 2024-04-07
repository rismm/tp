package supertracker.item;

import java.time.LocalDate;

public class Transaction extends Item {
    private String type;
    LocalDate transactionDate;

    //@@ author dtaywd
    public Transaction(String name, int quantity, double price, LocalDate expiryDate, String type,
                       LocalDate transactionDate) {
        super(name, quantity, price, expiryDate);
        this.type = type;
        this.transactionDate = transactionDate;
    }

    public String getType() {
        return type;
    }

    public LocalDate getTransactionDate() { return transactionDate; }
}
