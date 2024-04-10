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

/**
 * JUnit test class for testing the behavior of the ReportCommand class.
 */
public class ReportCommandTest {
    private static final String INVALID_EX_DATE_FORMAT = "dd-MM-yyyyy";
    private static final DateTimeFormatter VALID_EX_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String INVALID_EX_DATE = "01-01-99999";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    /**
     * Sets up the inventory and executes initial commands before running any test.
     * Initializes inventory items with various expiration dates and quantities.
     */
    @BeforeAll
    public static void setUp() {
        Inventory.clear();

        LocalDate currDate = LocalDate.now();
        LocalDate notExpiredDate = currDate.plusWeeks(2);
        LocalDate expiredDate = currDate.minusWeeks(2);
        LocalDate invalidDate = LocalDate.parse(INVALID_EX_DATE, DateTimeFormatter.ofPattern(INVALID_EX_DATE_FORMAT));

        Command[] commands = {
            new NewCommand("orange", 10, 2.00, currDate),
            new NewCommand("apple", 20, 1.00, notExpiredDate),
            new NewCommand("banana", 30, 3.00, expiredDate),
            new NewCommand("honey", 40, 10, invalidDate)
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
     * Tests the construction of a low stock report based on a specified threshold.
     * Verifies that the correct output is printed when executing the corresponding command.
     */
    @Test
    public void reportCommand_lowStock_correctlyConstructed() throws TrackerException {
        String userInput = "report r/low stock t/20";
        Command c = Parser.parseCommand(userInput);
        c.execute();

        String expected = "     There is 1 items low on stocks!" + LINE_SEPARATOR +
                "     1. Name: orange" + LINE_SEPARATOR +
                "        Quantity: 10" + LINE_SEPARATOR;
        String actual = outContent.toString();
        assertEquals(expected, actual);
    }

    /**
     * Tests the construction of an expiry report.
     * Verifies that the correct expiry report is printed when executing the corresponding command.
     */
    @Test
    public void reportCommand_expiry_correctlyConstructed() throws TrackerException {
        String userInput = "report r/expiry";
        Command c = Parser.parseCommand(userInput);
        c.execute();

        LocalDate currDate = LocalDate.now();
        String dateToday = currDate.format(VALID_EX_DATE_FORMAT);
        String dateTwoWeeksAgo = currDate.minusWeeks(2).format(VALID_EX_DATE_FORMAT);

        String expected = "     There is 1 item close to expiry!" + LINE_SEPARATOR +
                "     1. Name: orange" + LINE_SEPARATOR +
                "        Expiry Date: " + dateToday + LINE_SEPARATOR +
                "     There is 1 item that is expired!" + LINE_SEPARATOR +
                "     1. Name: banana" + LINE_SEPARATOR +
                "        Expiry Date: " + dateTwoWeeksAgo + LINE_SEPARATOR;
        String actual = outContent.toString();
        assertEquals(expected, actual);
    }

    /**
     * Tests the behavior when the user input for report command is incomplete.
     * Verifies that a TrackerException is thrown when required parameters are missing.
     */
    @Test
    public void reportCommand_missingParamInput() {
        String userInput = "report";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }

    /**
     * Tests the behavior when the user input for report command has an empty report parameter.
     * Verifies that a TrackerException is thrown when the report parameter is empty.
     */
    @Test
    public void reportCommand_emptyReportParamInput() {
        String userInput = "report r/";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }

    /**
     * Tests the behavior when the user input for report command lacks threshold parameter when report type
     * is low stock.
     * Verifies that a TrackerException is thrown when the threshold parameter is missing.
     */
    @Test
    public void reportCommand_emptyLowStockThresholdParamInput() {
        String userInput = "report r/low stock";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }

    /**
     * Tests the behavior when the user input for report command has an invalid threshold when report type
     * is low stock.
     * Verifies that a TrackerException is thrown when the threshold parameter is missing.
     */
    @Test
    public void reportCommand_invalidLowStockThresholdParamInput() {
        String userInput = "report r/low stock, t/-2";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }

    /**
     * Tests the behavior when the user input for report command has a threshold parameter when report type is expiry.
     * Verifies that a TrackerException is thrown when the threshold parameter is invalid.
     */
    @Test
    public void reportCommand_notEmptyExpiryThresholdParamInput() {
        String userInput = "report r/expiry t/1";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }
}
