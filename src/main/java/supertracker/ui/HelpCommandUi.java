package supertracker.ui;

//@@author TimothyLKM
public class HelpCommandUi extends Ui {
    private static final String HELP_SUCCESS_MESSAGE_FIRST_LINE =
            "Hello! These are the list of commands that I can help you with:";
    private static final String HELP_SUCCESS_MESSAGE_SECOND_LINE =
            "** Any other invalid input will bring you back to the main console";
    private static final String TO_SHOW_PARAMETERS = " to show parameters";
    private static final String[] HELP_LIST_OF_COMMANDS = {
        "Create a new item: type 'new'",
        "Delete an item: type 'delete'",
        "Change quantity of an item: type 'change'",
        "Update an item: type 'update'",
        "Find an item: type 'find'",
        "Rename an item: type 'rename'",
        "List all items: type 'list'",
        "Print a report: type 'report'",
        "Print expenditure: type 'exp'",
        "Print revenue: type 'rev'",
        "Print profit: type 'profit'",
        "Buy or sell items: type 'transaction'",
        "Clear transactions: type 'clear'"
    };
    private static final String HELP_CLOSING_MESSAGE_FIRST_LINE =
            "** Refer to UserGuide for further explanation";
    private static final String HELP_CLOSING_MESSAGE_SECOND_LINE =
            "** DO NOTE that you have been returned to the main console";
    private static final String HELP_NEW_PARAMETERS =
            "A new item command should look like this: new n/NAME q/QUANTITY p/PRICE [e/EXPIRY_DATE]";
    private static final String HELP_FIND_PARAMETERS =
            "A find command should look like this: find n/NAME";
    private static final String HELP_DELETE_PARAMETERS =
            "A delete command should look like this: delete n/NAME";
    private static final String HELP_UPDATE_PARAMETERS =
            "An update command should look like this: " +
            "update n/NAME [q/NEW_QUANTITY] [p/NEW_PRICE] [e/NEW_EXPIRY_DATE]";
    private static final String HELP_ADD_QUANTITY_PARAMETERS =
            "A add command should look like this: add n/NAME q/QUANTITY";
    private static final String HELP_REMOVE_QUANTITY_PARAMETERS =
            "A remove command should look like this: remove n/NAME q/QUANTITY";
    private static final String HELP_RENAME_PARAMETERS =
            "A rename command should look like this: rename n/NAME r/NEW_NAME";
    private static final String HELP_LOW_STOCK_REPORT_PARAMETERS =
            "A report command for low stock should look like this: report r/low stock t/THRESHOLD_VALUE";
    private static final String HELP_EXPIRY_REPORT_PARAMETERS =
            "A report command for expiry date should look like this: report r/expiry";
    private static final String HELP_LIST_PARAMETERS =
            "A list command should look like this: list [q/] [p/] [e/] [sq/] [sp/] [se/] [r/]";
    private static final String HELP_BUY_TRANSACTION_PARAMETERS =
            "A buy command should look like this: buy n/NAME q/QUANTITY p/PRICE";
    private static final String HELP_SELL_TRANSACTION_PARAMETERS =
            "A sell command should look like this: sell n/NAME q/QUANTITY";
    private static final String HELP_EXP_PARAMETERS =
            "A expenditure command should look like this: exp type/EXPENDITURE_TYPE [from/START_DATE] [to/END_DATE]";
    private static final String HELP_REV_PARAMETERS =
            "A revenue command should look like this: rev type/REVENUE_TYPE [from/START_DATE] [to/END_DATE]";
    private static final String HELP_PROFIT_PARAMETERS =
            "A profit command should look like this: profit type/REVENUE_TYPE [from/START_DATE] [to/END_DATE]";
    private static final String HELP_CLEAR_PARAMETERS =
            "A clear command should look like this: clear [b/BEFORE_DATE]";
    private static final String INVALID_HELP_COMMAND_MESSAGE_FIRST_LINE =
            "You have input an invalid command.";
    private static final String INVALID_HELP_COMMAND_MESSAGE_SECOND_LINE =
            "You are now back in the main console.";

    private static void printHelpListOfCommands() {
        int index = 1;
        for (String command : HELP_LIST_OF_COMMANDS) {
            printIndent(index + ". " + command + TO_SHOW_PARAMETERS);
            index++;
        }
    }

    public static void helpCommandSuccess() {
        printIndent(HELP_SUCCESS_MESSAGE_FIRST_LINE);
        printHelpListOfCommands();
        printIndent(HELP_SUCCESS_MESSAGE_SECOND_LINE);
        printLine();
    }

    public static void printNewCommandParams() {
        printIndent(HELP_NEW_PARAMETERS);
    }

    public static void printDeleteCommandParams() {
        printIndent(HELP_DELETE_PARAMETERS);
    }

    public static void printChangeCommandParams() {
        printIndent(HELP_ADD_QUANTITY_PARAMETERS);
        printIndent(HELP_REMOVE_QUANTITY_PARAMETERS);
    }

    public static void printUpdateCommandParams() {
        printIndent(HELP_UPDATE_PARAMETERS);
    }

    public static void printFindCommandParams() {
        printIndent(HELP_FIND_PARAMETERS);
    }

    public static void printRenameCommandParams() {
        printIndent(HELP_RENAME_PARAMETERS);
    }

    public static void printListCommandParams() {
        printIndent(HELP_LIST_PARAMETERS);
    }

    public static void printReportCommandParams() {
        printIndent(HELP_LOW_STOCK_REPORT_PARAMETERS);
        printIndent(HELP_EXPIRY_REPORT_PARAMETERS);
    }

    public static void printTransactionCommandParams() {
        printIndent(HELP_BUY_TRANSACTION_PARAMETERS);
        printIndent(HELP_SELL_TRANSACTION_PARAMETERS);
    }

    public static void printExpenditureCommandParams() {
        printIndent(HELP_EXP_PARAMETERS);
    }

    public static void printRevenueCommandParams() {
        printIndent(HELP_REV_PARAMETERS);
    }

    public static void printProfitCommandParams() {
        printIndent(HELP_PROFIT_PARAMETERS);
    }

    public static void printClearCommandParams() {
        printIndent(HELP_CLEAR_PARAMETERS);
    }

    public static void printInvalidHelpMessage() {
        printIndent(INVALID_HELP_COMMAND_MESSAGE_FIRST_LINE);
        printIndent(INVALID_HELP_COMMAND_MESSAGE_SECOND_LINE);
    }

    public static void helpClosingMessage() {
        printIndent(HELP_CLOSING_MESSAGE_FIRST_LINE);
        printIndent(HELP_CLOSING_MESSAGE_SECOND_LINE);
    }
}
