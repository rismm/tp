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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * JUnit test class for testing the behavior of the RenameCommand class.
 */
public class RenameCommandTest {
    /**
     * Clears the inventory before each test method execution.
     */
    @BeforeEach
    public void setUp() {
        Inventory.clear();
    }

    /**
     * Tests the execution of Rename Command with valid input data.
     * Verifies that an item is renamed correctly with no change in quantity, price and expiry date.
     */
    @Test
    public void renameCommand_validData_correctlyConstructed(){
        String name = "Milk";
        String newName = "Fresh Milk";

        int quantity = 100;
        double price = 5.00;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse("22-08-2013", dateFormat);

        Command newCommand = new NewCommand(name, quantity, price, date);
        newCommand.execute();
        Command renameCommand = new RenameCommand(name, newName);
        renameCommand.execute();

        assertTrue(Inventory.contains(newName));
        assertFalse(Inventory.contains(name));
        Item item = Inventory.get(newName);
        assertNotNull(item);
        assertEquals(newName, item.getName());
        assertEquals(quantity, item.getQuantity());
        assertEquals(price, item.getPrice());
        assertEquals(date, item.getExpiryDate());
    }

    /**
     * Tests the behavior of RenameCommand when input parameters are empty.
     * Verifies that a TrackerException is thrown due to missing rename parameters.
     */
    @Test
    public void renameCommand_emptyParamInput() {
        String name = "Milk";
        int quantity = 100;
        double price = 5.00;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse("22-08-2013", dateFormat);

        Command newCommand = new NewCommand(name, quantity, price, date);
        newCommand.execute();

        String userInput = "rename n/ r/";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }

    /**
     * Tests the behavior of RenameCommand when the new name input already exists in the inventory.
     * Verifies that a TrackerException is thrown due to new name being used.
     */
    @Test
    public void renameCommand_usedNewNameInput() {
        String nameA = "Milk";
        String nameB = "Coconut";
        int quantity = 100;
        double price = 5.00;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse("22-08-2013", dateFormat);

        Command newCommandA = new NewCommand(nameA, quantity, price, date);
        newCommandA.execute();
        Command newCommandB = new NewCommand(nameB, quantity, price, date);
        newCommandB.execute();

        String userInput = "rename n/Milk r/Coconut";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }
}
