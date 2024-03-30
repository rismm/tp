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

public class ReportCommandTest {
    private static final String INVALID_EX_DATE_FORMAT = "dd-MM-yyyyy";
    private static final DateTimeFormatter VALID_EX_DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final String INVALID_EX_DATE = "01-01-99999";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

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

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void reportCommand_lowStock_correctlyConstructed() throws TrackerException {
        String userInput = "report r/low stock t/20";
        Command c = Parser.parseCommand(userInput);
        c.execute();

        String expected = "     There is 1 items low on stocks!" + LINE_SEPARATOR +
                "     1. Name: orange" + LINE_SEPARATOR +
                "        Current Quantity: 10" + LINE_SEPARATOR;
        String actual = outContent.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void reportCommand_expiry_correctlyConstructed() throws TrackerException {
        String userInput = "report r/expiry";
        Command c = Parser.parseCommand(userInput);
        c.execute();

        LocalDate currDate = LocalDate.now();
        String dateToday = currDate.format(VALID_EX_DATE_FORMAT);
        String dateTwoWeeksAgo = currDate.minusWeeks(2).format(VALID_EX_DATE_FORMAT);

        String expected = "     There are 2 items close to expiry!" + LINE_SEPARATOR +
                "     1. Name: orange" + LINE_SEPARATOR +
                "        Expiry Date: " + dateToday + LINE_SEPARATOR +
                "     2. Name: banana" + LINE_SEPARATOR +
                "        Expiry Date: " + dateTwoWeeksAgo + LINE_SEPARATOR;
        String actual = outContent.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void reportCommand_missingParamInput() {
        String userInput = "report";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }

    @Test
    public void reportCommand_emptyReportParamInput() {
        String userInput = "report r/";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }

    @Test
    public void reportCommand_emptyLowStockThresholdParamInput() {
        String userInput = "report r/low stock";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }

    @Test
    public void reportCommand_notEmptyExpiryThresholdParamInput() {
        String userInput = "report r/expiry t/1";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }
}
