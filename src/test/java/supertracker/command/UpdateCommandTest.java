package supertracker.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import supertracker.parser.Parser;
import supertracker.TrackerException;
import supertracker.item.Inventory;
import supertracker.item.Item;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * JUnit test class for testing the behavior of the UpdateCommand class.
 */
public class UpdateCommandTest {
    /**
     * Clears the inventory before each test method execution.
     */
    @BeforeEach
    public void setUp() {
        Inventory.clear();
    }

    /**
     * Tests the execution of UpdateCommand with valid input data.
     * Verifies that an item is updated correctly with new quantity, price, and expiry date.
     */
    @Test
    public void updateCommand_validData_correctlyConstructed(){
        String name = "Milk";
        int quantity = 100;
        double price = 5.00;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse("22-08-2013", dateFormat);

        int newQuantity = 200;
        double newPrice = 3.00;
        LocalDate newExpiryDate = LocalDate.parse("05-12-2113", dateFormat);

        Command newCommand = new NewCommand(name, quantity, price, date);
        newCommand.execute();
        Command updateCommand = new UpdateCommand(name, newQuantity, newPrice, newExpiryDate);
        updateCommand.execute();

        assertTrue(Inventory.contains(name));
        Item item = Inventory.get(name);
        assertNotNull(item);
        assertEquals(name, item.getName());
        assertEquals(newQuantity, item.getQuantity());
        assertEquals(newPrice, item.getPrice());
        assertEquals(newExpiryDate, item.getExpiryDate());
    }

    /**
     * Tests the behavior of UpdateCommand with invalid input data.
     * Verifies that a TrackerException is thrown when updating with invalid parameters.
     */
    @Test
    public void updateCommand_invalidInput() {
        String name = "Milk";
        int quantity = 100;
        double price = 5.00;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse("22-08-2013", dateFormat);

        Command newCommand = new NewCommand(name, quantity, price, date);
        newCommand.execute();

        String userInput = "update n/Milk p/-1";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }

    /**
     * Tests the behavior of UpdateCommand when input parameters are empty.
     * Verifies that a TrackerException is thrown due to missing update parameters.
     */
    @Test
    public void updateCommand_emptyParamInput() {
        String name = "Milk";
        int quantity = 100;
        double price = 5.00;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse("22-08-2013", dateFormat);

        Command newCommand = new NewCommand(name, quantity, price, date);
        newCommand.execute();

        String userInput = "update n/Milk p/";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }

    /**
     * Tests the behavior of UpdateCommand when updating an item that does not exist in the inventory.
     * Verifies that a TrackerException is thrown when attempting to update a non-existing item.
     */
    @Test
    public void updateCommand_itemNotInList() {
        String name = "Milk";
        int quantity = 100;
        double price = 5.00;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse("22-08-2013", dateFormat);

        Command newCommand = new NewCommand(name, quantity, price, date);
        newCommand.execute();

        String userInput = "update n/apple q/20 p/3";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }

    /**
     * Tests the behavior of UpdateCommand when providing an invalid expiry date format.
     * Verifies that a TrackerException is thrown due to an invalid date format.
     */
    @Test
    public void updateCommand_invalidDateInput() {
        String name = "Milk";
        int quantity = 100;
        double price = 5.00;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse("22-08-2013", dateFormat);

        Command newCommand = new NewCommand(name, quantity, price, date);
        newCommand.execute();

        String userInput = "update n/Milk p/3 e/17/23/13";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }
}
