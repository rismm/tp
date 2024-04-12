package supertracker.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import supertracker.TrackerException;
import supertracker.item.Inventory;
import supertracker.item.Transaction;
import supertracker.item.TransactionList;
import supertracker.parser.Parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BuyCommandTest {
    private static final String BUY_FLAG = "b";

    @BeforeEach
    public void setUp() {
        Inventory.clear();
        TransactionList.clear();

        String name = "Milk";
        int quantity = 100;
        double price = 5.00;
        LocalDate date = LocalDate.parse("01-01-2113", DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        Command newCommand = new NewCommand(name, quantity, price, date);
        newCommand.execute();
    }

    @Test
    public void buyCommand_validData_correctlyConstructed(){
        String name = "Milk";

        int quantityToBuy = 50;
        double priceToBuy = 3.00;
        LocalDate buyDate = LocalDate.parse("12-04-2024", DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        Command buyCommand = new BuyCommand(name, quantityToBuy, priceToBuy, buyDate);
        buyCommand.execute();

        assertEquals(1, TransactionList.size());
        Transaction transaction = TransactionList.get(0);
        assertNotNull(transaction);
        assertEquals(name, transaction.getName());
        assertEquals(quantityToBuy, transaction.getQuantity());
        assertEquals(priceToBuy, transaction.getPrice());
        assertEquals(buyDate, transaction.getTransactionDate());
        assertEquals(BUY_FLAG, transaction.getType());
    }

    @Test
    public void buyCommand_missingParamInput() {
        String userInput = "buy n/Milk q/50";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }

    @Test
    public void buyCommand_emptyParamInput() {
        String userInput = "buy n/Milk q/50 p/";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }

    @Test
    public void buyCommand_itemNotInList() {
        String userInput = "buy n/Cake q/100 p/3.00";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }

    @Test
    public void buyCommand_quantityNotPositive() {
        String userInput = "buy n/Milk q/0 p/3.00";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }

    @Test
    public void buyCommand_integerOverflow() {
        String userInput = "buy n/Milk q/2147483647 p/3.00";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }
}
