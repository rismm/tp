package supertracker.command;
import supertracker.ui.HelpCommandUi;
import java.util.Scanner;
public class HelpCommand implements Command{
    private static final String NEW_COMMAND = "new";
    private static final String LIST_COMMAND = "list";
    private static final String UPDATE_COMMAND = "update";
    private static final String DELETE_COMMAND = "delete";
    private static final String TRANSACTION_COMMAND = "transaction";
    private static final String FIND_COMMAND = "find";
    private static final String REPORT_COMMAND = "report";
    private static final String CHANGE_QUANTITY_COMMAND = "change";
    private static final String RENAME_COMMAND = "rename";
    private static final String EXPENDITURE_COMMAND = "exp";
    private static final String REVENUE_COMMAND = "rev";
    public HelpCommand(){
    }

    private static String getHelpCommandReply(String input) {
        if (!input.contains(" ")) {
            return input;
        }
        return input.substring(0, input.indexOf(" "));
    }

    @Override
    public void execute() {
        HelpCommandUi.helpCommandSuccess();
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        String helpCommandWord = getHelpCommandReply(input);

        switch (helpCommandWord) {
        case NEW_COMMAND:
            HelpCommandUi.printNewCommandParams();
            break;
        case DELETE_COMMAND:
            HelpCommandUi.printDeleteCommandParams();
            break;
        case CHANGE_QUANTITY_COMMAND:
            HelpCommandUi.printChangeCommandParams();
            break;
        case UPDATE_COMMAND:
            HelpCommandUi.printUpdateCommandParams();
            break;
        case FIND_COMMAND:
            HelpCommandUi.printFindCommandParams();
            break;
        case RENAME_COMMAND:
            HelpCommandUi.printRenameCommandParams();
            break;
        case LIST_COMMAND:
            HelpCommandUi.printListCommandParams();
            break;
        case REPORT_COMMAND:
            HelpCommandUi.printReportCommandParams();
            break;
        case TRANSACTION_COMMAND:
            HelpCommandUi.printTransactionCommandParams();
            break;
        case EXPENDITURE_COMMAND:
            HelpCommandUi.printExpenditureCommandParams();
            break;
        case REVENUE_COMMAND:
            HelpCommandUi.printRevenueCommandParams();
            break;
        default:
            HelpCommandUi.printInvalidHelpMessage();
            break;
        }
    }

    @Override
    public boolean isQuit() {
        return false;
    }
}
