package supertracker.command;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import supertracker.TrackerException;
import supertracker.item.Inventory;
import supertracker.item.TransactionList;
import supertracker.parser.Parser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RevenueCommandTest {
    public static final DateTimeFormatter VALID_USER_INPUT_EX_DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final String INVALID_EX_DATE_FORMAT = "dd-MM-yyyyy";
    private static final String INVALID_EX_DATE = "01-01-99999";
    private static final DateTimeFormatter VALID_EX_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final LocalDate CURR_DATE = LocalDate.now();
    private static final String CURR_DATE_FORMATTED = CURR_DATE.format(VALID_EX_DATE_FORMAT);
    private static final LocalDate CURR_PLUS_ONE = CURR_DATE.plusDays(1);
    private static final String CURR_PLUS_ONE_FORMATTED = CURR_PLUS_ONE.format(VALID_EX_DATE_FORMAT);
    private static final String CURR_PLUS_ONE_INPUT = CURR_PLUS_ONE.format(VALID_USER_INPUT_EX_DATE_FORMAT);
    private static final LocalDate CURR_MINUS_TWO = CURR_DATE.minusDays(2);
    private static final String CURR_MINUS_TWO_FORMATTED = CURR_MINUS_TWO.format(VALID_EX_DATE_FORMAT);
    private static final String CURR_MINUS_TWO_INPUT = CURR_MINUS_TWO.format(VALID_USER_INPUT_EX_DATE_FORMAT);
    private static final LocalDate CURR_MINUS_THREE = CURR_DATE.minusDays(3);
    private static final String CURR_MINUS_THREE_FORMATTED = CURR_MINUS_THREE.format(VALID_EX_DATE_FORMAT);
    private static final String CURR_MINUS_THREE_INPUT = CURR_MINUS_THREE.format(VALID_USER_INPUT_EX_DATE_FORMAT);
    private static final LocalDate CURR_MINUS_TWO_WEEK = CURR_DATE.minusWeeks(2);
    private static final String CURR_MINUS_TWO_WEEK_FORMATTED = CURR_MINUS_TWO_WEEK.format(VALID_EX_DATE_FORMAT);
    private static final LocalDate INVALID_DATE = LocalDate.parse
            (INVALID_EX_DATE, DateTimeFormatter.ofPattern(INVALID_EX_DATE_FORMAT));
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    /**
     * Sets up the inventory and executes initial commands before running any test.
     * Clears the inventory, then executes a series of commands to populate it with items.
     */
    @BeforeAll
    public static void setUp() {
        Inventory.clear();
        TransactionList.clear();

        Command[] commands = {
            new NewCommand("orange", 20, 2.00, INVALID_DATE),
            new NewCommand("apple", 10, 1.00, INVALID_DATE),
            new NewCommand("banana", 5, 3.00, INVALID_DATE),
            new SellCommand("orange", 20, CURR_DATE),
            new SellCommand("apple", 10, CURR_MINUS_TWO),
            new SellCommand("banana", 5, CURR_MINUS_TWO_WEEK),
        };
        for (Command c : commands) {
            c.execute();
        }
    }

    /**
     * Redirects system output to a PrintStream for capturing output during tests.
     */
    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    /**
     * Tests the construction of revenue report for today's transactions.
     * Verifies that the correct output is printed based on executed commands.
     */
    @Test
    public void revenueCommand_today_correctlyConstructed() throws TrackerException {
        String userInput = "rev type/today";
        Command c = Parser.parseCommand(userInput);
        c.execute();

        Double amount = (double) (20 * 2);
        String amountString = String.format("%.2f", amount);

        String expected = "     Today's revenue is $" + amountString + LINE_SEPARATOR +
                "     1. Name: orange" + LINE_SEPARATOR +
                "        Quantity: 20" + LINE_SEPARATOR +
                "        Price: $2.00" + LINE_SEPARATOR +
                "        Transaction Date: " + CURR_DATE_FORMATTED + LINE_SEPARATOR;
        String actual = outContent.toString();
        assertEquals(expected, actual);
    }

    /**
     * Tests the construction of total revenue report.
     * Verifies that the correct total revenue report is printed based on executed commands.
     */
    @Test
    public void revenueCommand_total_correctlyConstructed() throws TrackerException {
        String userInput = "rev type/total";
        Command c = Parser.parseCommand(userInput);
        c.execute();

        Double amount = (double) (20 * 2 + 10 * 1 + 5 * 3);
        String amountString = String.format("%.2f", amount);

        String expected = "     Total revenue is $" + amountString + LINE_SEPARATOR +
                "     1. Name: orange" + LINE_SEPARATOR +
                "        Quantity: 20" + LINE_SEPARATOR +
                "        Price: $2.00" + LINE_SEPARATOR +
                "        Transaction Date: " + CURR_DATE_FORMATTED + LINE_SEPARATOR +
                "     2. Name: apple" + LINE_SEPARATOR +
                "        Quantity: 10" + LINE_SEPARATOR +
                "        Price: $1.00" + LINE_SEPARATOR +
                "        Transaction Date: " + CURR_MINUS_TWO_FORMATTED + LINE_SEPARATOR +
                "     3. Name: banana" + LINE_SEPARATOR +
                "        Quantity: 5" + LINE_SEPARATOR +
                "        Price: $3.00" + LINE_SEPARATOR +
                "        Transaction Date: " + CURR_MINUS_TWO_WEEK_FORMATTED + LINE_SEPARATOR;

        String actual = outContent.toString();
        assertEquals(expected, actual);
    }

    /**
     * Tests the construction of revenue report for a specific day.
     * Verifies that the correct revenue report for a given day is printed.
     */
    @Test
    public void revenueCommand_day_correctlyConstructed() throws TrackerException {
        String userInput = "rev type/day from/" + CURR_MINUS_TWO_INPUT;
        Command c = Parser.parseCommand(userInput);
        c.execute();

        Double amount = (double) (10 * 1);
        String amountString = String.format("%.2f", amount);

        String expected = "     revenue on " + CURR_MINUS_TWO_FORMATTED + " was $" + amountString + LINE_SEPARATOR +
                "     1. Name: apple" + LINE_SEPARATOR +
                "        Quantity: 10" + LINE_SEPARATOR +
                "        Price: $1.00" + LINE_SEPARATOR +
                "        Transaction Date: " + CURR_MINUS_TWO_FORMATTED + LINE_SEPARATOR;

        String actual = outContent.toString();
        assertEquals(expected, actual);
    }

    /**
     * Tests the construction of revenue report for a date range.
     * Verifies that the correct revenue report for a given date range is printed.
     */
    @Test
    public void revenueCommand_range_correctlyConstructed() throws TrackerException {
        String userInput = "rev type/range from/" + CURR_MINUS_THREE_INPUT + " to/" + CURR_PLUS_ONE_INPUT;
        Command c = Parser.parseCommand(userInput);
        c.execute();

        Double amount = (double) (20 * 2 + 10 * 1);
        String amountString = String.format("%.2f", amount);

        String expected = "     revenue between " + CURR_MINUS_THREE_FORMATTED + " and " + CURR_PLUS_ONE_FORMATTED
                + " was $" + amountString + LINE_SEPARATOR +
                "     1. Name: orange" + LINE_SEPARATOR +
                "        Quantity: 20" + LINE_SEPARATOR +
                "        Price: $2.00" + LINE_SEPARATOR +
                "        Transaction Date: " + CURR_DATE_FORMATTED + LINE_SEPARATOR +
                "     2. Name: apple" + LINE_SEPARATOR +
                "        Quantity: 10" + LINE_SEPARATOR +
                "        Price: $1.00" + LINE_SEPARATOR +
                "        Transaction Date: " + CURR_MINUS_TWO_FORMATTED + LINE_SEPARATOR;

        String actual = outContent.toString();
        assertEquals(expected, actual);
    }

    /**
     * Tests the behavior when the user input for revenue command is incomplete.
     * Verifies that a TrackerException is thrown when required parameters are missing.
     */
    @Test
    public void revenueCommand_missingParamInput() {
        String userInput = "rev type/";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }

    /**
     * Tests the behavior when the user input for revenue command has too many parameters.
     * Verifies that a TrackerException is thrown when unexpected parameters are provided.
     */
    @Test
    public void revenueCommand_tooManyParamInput() {
        String userInput = "rev type/total from/";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }

    /**
     * Tests the behavior when the user input for revenue command is missing the flags for day task.
     * Verifies that a TrackerException is thrown when essential flags are absent.
     */
    @Test
    public void revenueCommand_missingDayFlag() {
        String userInput = "rev type/day";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }

    /**
     * Tests the behavior when the user input for revenue command is missing the flag for range task.
     * Verifies that a TrackerException is thrown when the range flag is missing.
     */
    @Test
    public void revenueCommand_missingRangeFlag() {
        String userInput = "rev type/range from/" + CURR_MINUS_THREE_INPUT;
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }
}
