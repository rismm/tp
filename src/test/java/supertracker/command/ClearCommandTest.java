package supertracker.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import supertracker.TrackerException;
import supertracker.item.Transaction;
import supertracker.item.TransactionList;
import supertracker.parser.Parser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClearCommandTest {
    private static final String BUY_FLAG = "b";
    private static final String SELL_FLAG = "s";
    private final InputStream sysInBackup = System.in;
    private ByteArrayInputStream in;

    @BeforeEach
    public void setUp() {
        TransactionList.clear();
        Transaction[] transactions = {
            new Transaction("Apple", 1, 1.00, LocalDate.parse("2024-01-01"), BUY_FLAG),
            new Transaction("Banana", 2, 2.00, LocalDate.parse("2023-01-01"), SELL_FLAG),
            new Transaction("Cake", 3, 3.00, LocalDate.parse("2022-01-01"), BUY_FLAG),
            new Transaction("Egg", 4, 4.00, LocalDate.parse("2021-01-01"), SELL_FLAG),
            new Transaction("Milk", 5, 5.00, LocalDate.parse("2020-01-01"), BUY_FLAG),
        };
        for (Transaction transaction : transactions) {
            TransactionList.add(transaction);
        }
        System.setIn(sysInBackup);
    }

    @Test
    public void clearCommand_validData_correctlyConstructed(){
        String input = "Y";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        LocalDate beforeDate = LocalDate.parse("2024-01-01");

        Command clearCommand = new ClearCommand(beforeDate);
        clearCommand.execute();

        assertEquals(1, TransactionList.size());
        assertEquals("Apple", TransactionList.get(0).getName());
    }

    @Test
    public void clearCommand_validData_clearAll(){
        String input = "y";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        LocalDate beforeDate = LocalDate.parse("2024-02-02");

        Command clearCommand = new ClearCommand(beforeDate);
        clearCommand.execute();

        assertEquals(0, TransactionList.size());
    }

    @Test
    public void clearCommand_validData_clearNone(){
        String input = "y";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        LocalDate beforeDate = LocalDate.parse("1999-01-01");

        Command clearCommand = new ClearCommand(beforeDate);
        clearCommand.execute();

        assertEquals(5, TransactionList.size());
        assertEquals("Milk", TransactionList.get(4).getName());
    }

    @Test
    public void clearCommand_inValidConfirmation(){
        String input = "  y  ";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        LocalDate beforeDate = LocalDate.parse("2024-01-01");

        Command clearCommand = new ClearCommand(beforeDate);
        clearCommand.execute();

        assertEquals(5, TransactionList.size());
        assertEquals("Milk", TransactionList.get(4).getName());
    }

    @Test
    public void clearCommand_invalidDate(){
        String userInput = "clear b/29-02-2023";
        assertThrows(TrackerException.class, () -> Parser.parseCommand(userInput));
    }

    @AfterEach void tearDown() {
        System.setIn(sysInBackup);
    }
}
