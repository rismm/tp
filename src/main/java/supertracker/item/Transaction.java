package supertracker.item;

import java.math.BigDecimal;
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

    public BigDecimal getTotalPrice() {
        BigDecimal bigQuantity = new BigDecimal(quantity);
        BigDecimal bigPrice = new BigDecimal(price);
        return bigQuantity.multiply(bigPrice);
    }

    public String getTotalPriceString() {
        BigDecimal totalPrice = getTotalPrice();
        return "$" + String.format("%.2f", totalPrice);
    }
}
