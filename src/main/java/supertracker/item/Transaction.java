package supertracker.item;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents a transaction involving the buying or selling of an item.
 */
public class Transaction extends Item {
    private static final String BUY_FLAG = "b";
    private static final String SELL_FLAG = "s";
    private String type;

    /**
     * Constructs a Transaction with the specified attributes.
     *
     * @param name            Name of the item involved in the transaction.
     * @param quantity        Quantity of the item involved in the transaction.
     * @param price           Price of the item involved in the transaction.
     * @param transactionDate Date of the transaction.
     * @param type            Type of the transaction (buy/sell).
     */
    public Transaction(String name, int quantity, double price, LocalDate transactionDate, String type) {
        super(name, quantity, price, transactionDate);
        this.type = type;
        assert type.equals(BUY_FLAG) || type.equals(SELL_FLAG);
    }

    /**
     * Retrieves the type of the transaction.
     *
     * @return Type of the transaction (buy/sell).
     */
    public String getType() {
        return type;
    }

    /**
     * Retrieves the date of the transaction.
     *
     * @return Date of the transaction.
     */
    public LocalDate getTransactionDate() {
        return getExpiryDate();
    }

    /**
     * Calculates the total price of the transaction.
     *
     * @return Total price of the transaction.
     */
    public BigDecimal getTotalPrice() {
        BigDecimal bigQuantity = new BigDecimal(quantity);
        BigDecimal bigPrice = new BigDecimal(price);
        return bigQuantity.multiply(bigPrice);
    }

    /**
     * Retrieves the total price of the transaction as a formatted string.
     *
     * @return Formatted total price of the transaction.
     */
    public String getTotalPriceString() {
        BigDecimal totalPrice = getTotalPrice();
        return "$" + String.format("%.2f", totalPrice);
    }
}
