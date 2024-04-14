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

public class SellCommandTest {
    private static final String SELL_FLAG = "s";

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
    public void sellCommand_validData_correctlyConstructed(){
        String name = "Milk";
        double price = 5.00;

        int quantityToSell = 50;
        LocalDate sellDate = LocalDate.parse("12-04-2024", DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        Command sellCommand = new SellCommand(name, quantityToSell, sellDate);
        sellCommand.execute();

        assertEquals(1, TransactionList.size());
        Transaction transaction = TransactionList.get(0);
        assertNotNull(transaction);
        assertEquals(name, transaction.getName());
        assertEquals(quantityToSell, transaction.getQuantity());
        assertEquals(price, transaction.getPrice());
        assertEquals(sellDate, transaction.getTransactionDate());
        assertEquals(SELL_FLAG, transaction.getType());
    }

    @Test
    public void sellCommand_missingParamInput() {
        String userInput = "sell n/Milk";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }

    @Test
    public void sellCommand_emptyParamInput() {
        String userInput = "sell n/Milk q/";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }

    @Test
    public void sellCommand_itemNotInList() {
        String userInput = "sell n/Cake /50";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }

    @Test
    public void sellCommand_quantityNotPositive() {
        String userInput = "sell n/Milk q/0";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }

    @Test
    public void sellCommand_nothingSold() {
        String name = "Cake";
        int quantity = 0;
        double price = 5.00;
        LocalDate date = LocalDate.parse("01-01-2113", DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        Command newCommand = new NewCommand(name, quantity, price, date);
        newCommand.execute();

        int quantityToSell = 50;
        LocalDate sellDate = LocalDate.parse("12-04-2024", DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        Command sellCommand = new SellCommand(name, quantityToSell, sellDate);
        sellCommand.execute();
        assertEquals(0, TransactionList.size());
    }
}
