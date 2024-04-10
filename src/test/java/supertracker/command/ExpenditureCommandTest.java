package supertracker.command;

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

public class ExpenditureCommandTest {
    public static final DateTimeFormatter VALID_USER_INPUT_EX_DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final String INVALID_EX_DATE_FORMAT = "dd-MM-yyyyy";
    private static final String INVALID_EX_DATE = "01-01-99999";
    private static final DateTimeFormatter VALID_EX_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final LocalDate currDate = LocalDate.now();
    private static final String currDateFormatted = currDate.format(VALID_EX_DATE_FORMAT);
    private static final LocalDate oneDayAheadDate = currDate.plusDays(1);
    private static final String oneDayAheadFormatted = oneDayAheadDate.format(VALID_EX_DATE_FORMAT);
    private static final String oneDayAheadUserInput = oneDayAheadDate.format(VALID_USER_INPUT_EX_DATE_FORMAT);
    private static final LocalDate twoDaysAgoDate = currDate.minusDays(2);
    private static final String twoDaysAgoDateFormatted = twoDaysAgoDate.format(VALID_EX_DATE_FORMAT);
    private static final String twoDaysAgoUserInput = twoDaysAgoDate.format(VALID_USER_INPUT_EX_DATE_FORMAT);
    private static final LocalDate threeDaysAgoDate = currDate.minusDays(3);
    private static final String threeDayAgoFormatted = threeDaysAgoDate.format(VALID_EX_DATE_FORMAT);
    private static final String threeDayAgoUserInput = threeDaysAgoDate.format(VALID_USER_INPUT_EX_DATE_FORMAT);
    private static final LocalDate twoWeeksAgoDate = currDate.minusWeeks(2);
    private static final String twoWeeksAgoDateFormatted = twoWeeksAgoDate.format(VALID_EX_DATE_FORMAT);
    private static final LocalDate invalidDate = LocalDate.parse
            (INVALID_EX_DATE, DateTimeFormatter.ofPattern(INVALID_EX_DATE_FORMAT));
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    /**
     * Sets up the inventory and executes initial commands before running any test.
     * Clears the inventory, then executes a series of commands to populate it with items.
     */
    @BeforeAll
    public static void setUp() {
        Inventory.clear();

        Command[] commands = {
            new NewCommand("orange", 0, 2.00, invalidDate),
            new NewCommand("apple", 0, 1.00, invalidDate),
            new NewCommand("banana", 0, 3.00, invalidDate),
            new BuyCommand("orange", 20, 1.00, currDate),
            new BuyCommand("apple", 10, 0.50, twoDaysAgoDate),
            new BuyCommand("banana", 5, 1.50, twoWeeksAgoDate),
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
     * Tests the construction of expenditure report for today's transactions.
     * Verifies that the correct output is printed based on executed commands.
     */
    @Test
    public void expenditureCommand_today_correctlyConstructed() throws TrackerException {
        String userInput = "exp type/today";
        Command c = Parser.parseCommand(userInput);
        c.execute();

        Double amount = (double) (20 * 1);
        String amountString = String.format("%.2f", amount);

        String expected = "     Today's expenditure is $" + amountString + LINE_SEPARATOR +
                "     1. Name: orange" + LINE_SEPARATOR +
                "        Quantity: 20" + LINE_SEPARATOR +
                "        Price: $1.00" + LINE_SEPARATOR +
                "        Transaction Date: " + currDateFormatted + LINE_SEPARATOR;
        String actual = outContent.toString();
        assertEquals(expected, actual);
    }

    /**
     * Tests the construction of total expenditure report.
     * Verifies that the correct total expenditure report is printed based on executed commands.
     */
    @Test
    public void expenditureCommand_total_correctlyConstructed() throws TrackerException {
        String userInput = "exp type/total";
        Command c = Parser.parseCommand(userInput);
        c.execute();

        Double amount = 32.5;
        String amountString = String.format("%.2f", amount);

        String expected = "     Total expenditure is $" + amountString + LINE_SEPARATOR +
                "     1. Name: orange" + LINE_SEPARATOR +
                "        Quantity: 20" + LINE_SEPARATOR +
                "        Price: $1.00" + LINE_SEPARATOR +
                "        Transaction Date: " + currDateFormatted + LINE_SEPARATOR +
                "     2. Name: apple" + LINE_SEPARATOR +
                "        Quantity: 10" + LINE_SEPARATOR +
                "        Price: $0.50" + LINE_SEPARATOR +
                "        Transaction Date: " + twoDaysAgoDateFormatted + LINE_SEPARATOR +
                "     3. Name: banana" + LINE_SEPARATOR +
                "        Quantity: 5" + LINE_SEPARATOR +
                "        Price: $1.50" + LINE_SEPARATOR +
                "        Transaction Date: " + twoWeeksAgoDateFormatted + LINE_SEPARATOR;

        String actual = outContent.toString();
        assertEquals(expected, actual);
    }

    /**
     * Tests the construction of expenditure report for a specific day.
     * Verifies that the correct expenditure report for a given day is printed.
     */
    @Test
    public void expenditureCommand_day_correctlyConstructed() throws TrackerException {
        String userInput = "exp type/day from/" + twoDaysAgoUserInput;
        Command c = Parser.parseCommand(userInput);
        c.execute();

        Double amount = (10 * 0.5);
        String amountString = String.format("%.2f", amount);

        String expected = "     expenditure on " + twoDaysAgoDateFormatted + " was $" + amountString + LINE_SEPARATOR +
                "     1. Name: apple" + LINE_SEPARATOR +
                "        Quantity: 10" + LINE_SEPARATOR +
                "        Price: $0.50" + LINE_SEPARATOR +
                "        Transaction Date: " + twoDaysAgoDateFormatted + LINE_SEPARATOR;

        String actual = outContent.toString();
        assertEquals(expected, actual);
    }


    /**
     * Tests the construction of expenditure report for a date range.
     * Verifies that the correct expenditure report for a given date range is printed.
     */
    @Test
    public void expenditureCommand_range_correctlyConstructed() throws TrackerException {
        String userInput = "exp type/range from/" + threeDayAgoUserInput + " to/" + oneDayAheadUserInput;
        Command c = Parser.parseCommand(userInput);
        c.execute();

        Double amount = (20 * 1 + 10 * 0.5);
        String amountString = String.format("%.2f", amount);

        String expected = "     expenditure between " + threeDayAgoFormatted + " and " + oneDayAheadFormatted
                + " was $" + amountString + LINE_SEPARATOR +
                "     1. Name: orange" + LINE_SEPARATOR +
                "        Quantity: 20" + LINE_SEPARATOR +
                "        Price: $1.00" + LINE_SEPARATOR +
                "        Transaction Date: " + currDateFormatted + LINE_SEPARATOR +
                "     2. Name: apple" + LINE_SEPARATOR +
                "        Quantity: 10" + LINE_SEPARATOR +
                "        Price: $0.50" + LINE_SEPARATOR +
                "        Transaction Date: " + twoDaysAgoDateFormatted + LINE_SEPARATOR;

        String actual = outContent.toString();
        assertEquals(expected, actual);
    }

    /**
     * Tests the behavior when the user input for expenditure command is incomplete.
     * Verifies that a TrackerException is thrown when required parameters are missing.
     */
    @Test
    public void expenditureCommand_missingParamInput() {
        String userInput = "exp type/";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }

    /**
     * Tests the behavior when the user input for expenditure command has too many parameters.
     * Verifies that a TrackerException is thrown when unexpected parameters are provided.
     */
    @Test
    public void expenditureCommand_tooManyParamInput() {
        String userInput = "exp type/total from/";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }

    /**
     * Tests the behavior when the user input for expenditure command is missing the flags for day task.
     * Verifies that a TrackerException is thrown when essential flags are absent.
     */
    @Test
    public void expenditureCommand_missingDayFlag() {
        String userInput = "exp type/day";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }

    /**
     * Tests the behavior when the user input for expenditure command is missing the flag for range task.
     * Verifies that a TrackerException is thrown when the range flag is missing.
     */
    @Test
    public void expenditureCommand_missingRangeFlag() {
        String userInput = "exp type/range from/" + threeDayAgoUserInput;
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }
}
