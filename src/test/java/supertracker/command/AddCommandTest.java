package supertracker.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import supertracker.TrackerException;
import supertracker.item.Inventory;
import supertracker.item.Item;
import supertracker.parser.Parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddCommandTest {
    @BeforeEach
    public void setUp() {
        Inventory.clear();

        String name = "Milk";
        int quantity = 100;
        double price = 5.00;
        LocalDate date = LocalDate.parse("01-01-2113", DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        Command newCommand = new NewCommand(name, quantity, price, date);
        newCommand.execute();
    }

    @Test
    public void addCommand_validData_correctlyConstructed(){
        String name = "Milk";
        int quantity = 100;

        int quantityToAdd = 50;
        int newQuantity = quantity + quantityToAdd;

        Command addCommand = new AddCommand(name, quantityToAdd);
        addCommand.execute();

        assertTrue(Inventory.contains(name));
        Item item = Inventory.get(name);
        assertNotNull(item);
        assertEquals(name, item.getName());
        assertEquals(newQuantity, item.getQuantity());
    }

    @Test
    public void addCommand_missingParamInput() {
        String userInput = "add n/Milk";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }

    @Test
    public void addCommand_emptyParamInput() {
        String userInput = "add n/Milk q/";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }

    @Test
    public void addCommand_itemNotInList() {
        String userInput = "add n/Cake q/100";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }

    @Test
    public void addCommand_quantityNotPositive() {
        String userInput = "add n/Milk q/0";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }

    @Test
    public void addCommand_integerOverflow() {
        String userInput = "add n/Milk q/2147483647";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }
}
