package supertracker.ui;

public class HelpCommandUi extends Ui {
    private static final String INDENT_SPACES = "     ";
    private static final String HELP_MESSAGE = "Hello! These are the list of commands that i can help you with:";
    private static final String HELP_LIST_OF_COMMANDS =
            "1. Create a new item: type 'new' to show parameters\n" +
            INDENT_SPACES + "2. Delete an item: type 'delete' to show parameters\n" +
            INDENT_SPACES + "3. Change quantity of an item: type 'change' to show parameters\n" +
            INDENT_SPACES + "4. Update an item: type 'update' to show parameters\n" +
            INDENT_SPACES + "5. Find an item: type 'find' to show parameters\n" +
            INDENT_SPACES + "6. Rename an item: type 'rename' to show parameters\n" +
            INDENT_SPACES + "7. List all items: type 'list' to show parameters\n" +
            INDENT_SPACES + "8. Print a report: type 'report' to show parameters\n" +
            INDENT_SPACES + "9. Print an expenditure: type 'exp' to show parameters\n" +
            INDENT_SPACES + "10. Print a revenue: type 'rev' to show parameters\n" +
            INDENT_SPACES + "11. Buying or selling items: type 'transaction' to show parameters\n" +
            INDENT_SPACES + "**Any other invalid input will bring you back to the main console";
    private static final String HELP_CLOSING_MESSAGE =
            INDENT_SPACES + "**Refer to UserGuide for further explanation\n" +
            INDENT_SPACES + "**DO NOTE that You have also returned to the main console";
    private static final String HELP_NEW_PARAMETERS =
            "A new item command should look like this: new n/NAME q/QUANTITY p/PRICE [e/EXPIRY_DATE]\n" +
            HELP_CLOSING_MESSAGE;
    private static final String HELP_FIND_PARAMETERS =
            "A find command should look like this: find n/NAME\n" + HELP_CLOSING_MESSAGE;
    private static final String HELP_DELETE_PARAMETERS =
            "A delete command should look like this: delete n/NAME\n" + HELP_CLOSING_MESSAGE;
    private static final String HELP_UPDATE_PARAMETERS =
            "An update command should look like this: " +
            "update n/NAME [q/NEW_QUANTITY] [p/NEW_PRICE] [e/NEW_EXPIRY_DATE]\n" +
            HELP_CLOSING_MESSAGE;
    private static final String HELP_QUANTITY_PARAMETERS =
            "A increase quantity command should look like this: add n/NAME q/QUANTITY\n" +
            INDENT_SPACES + "A decrease quantity command should look like this: remove n/NAME q/QUANTITY\n" +
            HELP_CLOSING_MESSAGE;
    private static final String HELP_RENAME_PARAMETERS =
            "A rename command should look like this: rename n/NAME r/NEW_NAME\n" + HELP_CLOSING_MESSAGE;
    private static final String HELP_REPORT_PARAMETERS =
            "A report command for low stock should look like this: report r/REPORT_TYPE t/THRESHOLD_VALUE\n" +
            INDENT_SPACES + "A report command for expiry date should look like this: "+
            "report r/REPORT_TYPE [t/THRESHOLD_VALUE]\n" +
            HELP_CLOSING_MESSAGE;
    private static final String HELP_LIST_PARAMETERS =
            "A list command should look like this: list [q/] [p/] [e/] [sq/] [sp/] [se/] [r/]\n" + HELP_CLOSING_MESSAGE;
    private static final String HELP_TRANSACTION_PARAMETERS =
            "A buy command should look like this: buy n/NAME q/QUANTITY p/PRICE\n" +
            INDENT_SPACES + "A sell command should look like this: sell n/NAME q/QUANTITY\n" +
            HELP_CLOSING_MESSAGE;
    private static final String HELP_EXP_PARAMETERS =
            "A expenditure command should look like this: exp type/EXPENDITURE_TYPE [from/START_DATE] [to/END_DATE]\n" +
            HELP_CLOSING_MESSAGE;
    private static final String HELP_REV_PARAMETERS =
            "A revenue command should look like this: rev type/REVENUE_TYPE [from/START_DATE] [to/END_DATE]\n" +
            HELP_CLOSING_MESSAGE;
    private static final String INVALID_HELP_COMMAND_MESSAGE =
            "You have input an invalid command.\n" + INDENT_SPACES + "You are now back in the main console.";

    public static void helpCommandSuccess() {
        printIndent(HELP_MESSAGE);
        printIndent(HELP_LIST_OF_COMMANDS);
        printLine();
    }

    public static void printNewCommandParams() {
        printLine();
        printIndent(HELP_NEW_PARAMETERS);
    }

    public static void printDeleteCommandParams() {
        printLine();
        printIndent(HELP_DELETE_PARAMETERS);
    }

    public static void printChangeCommandParams() {
        printLine();
        printIndent(HELP_QUANTITY_PARAMETERS);
    }

    public static void printUpdateCommandParams() {
        printLine();
        printIndent(HELP_UPDATE_PARAMETERS);
    }

    public static void printFindCommandParams() {
        printLine();
        printIndent(HELP_FIND_PARAMETERS);
    }

    public static void printRenameCommandParams() {
        printLine();
        printIndent(HELP_RENAME_PARAMETERS);
    }

    public static void printListCommandParams() {
        printLine();
        printIndent(HELP_LIST_PARAMETERS);
    }

    public static void printReportCommandParams() {
        printLine();
        printIndent(HELP_REPORT_PARAMETERS);
    }

    public static void printTransactionCommandParams() {
        printLine();
        printIndent(HELP_TRANSACTION_PARAMETERS);
    }

    public static void printExpenditureCommandParams() {
        printLine();
        printIndent(HELP_EXP_PARAMETERS);
    }

    public static void printRevenueCommandParams() {
        printLine();
        printIndent(HELP_REV_PARAMETERS);
    }

    public static void printInvalidHelpMessage() {
        printLine();
        printIndent(INVALID_HELP_COMMAND_MESSAGE);
    }

}
