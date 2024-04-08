package supertracker.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import supertracker.TrackerException;
import supertracker.item.Inventory;
import supertracker.parser.Parser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@@author [TimothyLKM]
public class FindCommandTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String FIND_INTRO = "     Here are your found items:" + LINE_SEPARATOR;
    private static final String INDEX = "     1.";
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeAll
    public static void setUp() {
        Inventory.clear();
        String name = "Milk";
        int quantity = 100;
        double price = 5.00;
        LocalDate date = LocalDate.parse("01-01-2113", DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        Command newCommand = new NewCommand(name, quantity, price, date);
        newCommand.execute();
    }
    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }
    @Test
    public void findCommand_validData_correctlyConstructed() throws TrackerException {
        String word = "Milk";

        String userInput = "find n/" + word;
        Command c = Parser.parseCommand(userInput);
        c.execute();

        String expected = FIND_INTRO +
                INDEX + " Name: Milk" + LINE_SEPARATOR +
                "        Quantity: 100" + LINE_SEPARATOR +
                "        Price: $5.00" + LINE_SEPARATOR +
                "        Expiry Date: 01/01/2113" + LINE_SEPARATOR;
        String actual = outContent.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void findCommand_missingParamInput() {
        String userInput = "find";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }

    @Test
    public void findCommand_emptyParamInput() {
        String userInput = "find n/";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }

    @Test
    public void findCommand_itemNotInList() throws TrackerException {
        String userInput = "find n/cake";
        Command c = Parser.parseCommand(userInput);
        c.execute();

        String expected = "     So sorry, Your item: cake could not be found." + LINE_SEPARATOR;
        String actual = outContent.toString();
        assertEquals(expected, actual);
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}
