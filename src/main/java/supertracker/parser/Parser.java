package supertracker.parser;

import supertracker.TrackerException;

import supertracker.command.AddCommand;
import supertracker.command.BuyCommand;
import supertracker.command.ClearCommand;
import supertracker.command.Command;
import supertracker.command.DeleteCommand;
import supertracker.command.ExpenditureCommand;
import supertracker.command.FindCommand;
import supertracker.command.HelpCommand;
import supertracker.command.InvalidCommand;
import supertracker.command.ListCommand;
import supertracker.command.NewCommand;
import supertracker.command.ProfitCommand;
import supertracker.command.QuitCommand;
import supertracker.command.RemoveCommand;
import supertracker.command.RenameCommand;
import supertracker.command.ReportCommand;
import supertracker.command.RevenueCommand;
import supertracker.command.SellCommand;
import supertracker.command.UpdateCommand;


import supertracker.item.Inventory;
import supertracker.item.Item;
import supertracker.ui.ErrorMessage;
import supertracker.ui.Ui;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.time.LocalDate;

public class Parser {
    private static final DateTimeFormatter EX_DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter DATE_FORMAT_NULL = DateTimeFormatter.ofPattern("dd-MM-yyyyy");
    private static final LocalDate UNDEFINED_DATE = LocalDate.parse("01-01-99999", DATE_FORMAT_NULL);
    private static final double ROUNDING_FACTOR = 100.0;
    private static final String QUIT_COMMAND = "quit";
    private static final String NEW_COMMAND = "new";
    private static final String LIST_COMMAND = "list";
    private static final String HELP_COMMAND = "help";
    private static final String UPDATE_COMMAND = "update";
    private static final String DELETE_COMMAND = "delete";
    private static final String ADD_COMMAND = "add";
    private static final String REMOVE_COMMAND = "remove";
    private static final String FIND_COMMAND = "find";
    private static final String REPORT_COMMAND = "report";
    private static final String BUY_COMMAND = "buy";
    private static final String SELL_COMMAND = "sell";
    private static final String CLEAR_COMMAND = "clear";
    private static final String RENAME_COMMAND = "rename";
    private static final String EXPENDITURE_COMMAND = "exp";
    private static final String REVENUE_COMMAND = "rev";
    private static final String PROFIT_COMMAND = "profit";
    private static final String BASE_FLAG = "/";
    private static final String NAME_FLAG = "n";
    private static final String NEW_NAME_FLAG = "r";
    private static final String QUANTITY_FLAG = "q";
    private static final String PRICE_FLAG = "p";
    private static final String EX_DATE_FLAG = "e";
    private static final String BEFORE_DATE_FLAG = "b";
    private static final String SORT_QUANTITY_FLAG = "sq";
    private static final String SORT_PRICE_FLAG = "sp";
    private static final String SORT_EX_DATE_FLAG = "se";
    private static final String REVERSE_FLAG = "r";
    private static final String NAME_GROUP = "name";
    private static final String NEW_NAME_GROUP = "rename";
    private static final String QUANTITY_GROUP = "quantity";
    private static final String PRICE_GROUP = "price";
    private static final String EX_DATE_GROUP = "expiry";
    private static final String BEFORE_DATE_GROUP = "before";
    private static final String SORT_QUANTITY_GROUP = "sortQuantity";
    private static final String SORT_PRICE_GROUP = "sortPrice";
    private static final String SORT_EX_DATE_GROUP = "sortExpiry";
    private static final String REVERSE_GROUP = "reverse";
    private static final String REPORT_TYPE_FLAG = "r";
    private static final String REPORT_TYPE_GROUP = "reportType";
    private static final String THRESHOLD_FLAG = "t";
    private static final String THRESHOLD_GROUP = "threshold";
    private static final String TYPE_FLAG = "type";
    private static final String TYPE_GROUP = "type";
    private static final String TO_FLAG = "to";
    private static final String TO_GROUP = "to";
    private static final String FROM_FLAG = "from";
    private static final String FROM_GROUP = "from";
    private static final String TODAY = "today";
    private static final String TOTAL = "total";
    private static final String DAY = "day";
    private static final String RANGE = "range";

    // Do note that the file delimiter constant needs to follow the separator constant in the FileManager class
    private static final String FILE_DELIMITER = " ,,, ";

    // To be used in getPatternMatcher to split the input into its respective parameter groups
    private static final String NEW_COMMAND_REGEX = NAME_FLAG + BASE_FLAG + "(?<" + NAME_GROUP + ">.*) "
            + QUANTITY_FLAG + BASE_FLAG + "(?<" + QUANTITY_GROUP + ">.*) "
            + PRICE_FLAG + BASE_FLAG + "(?<" + PRICE_GROUP + ">.*) "
            + "(?<" + EX_DATE_GROUP + ">(?:" + EX_DATE_FLAG + BASE_FLAG + ".*)?) ";
    private static final String UPDATE_COMMAND_REGEX = NAME_FLAG + BASE_FLAG + "(?<" + NAME_GROUP + ">.*) "
            + "(?<" + QUANTITY_GROUP + ">(?:" + QUANTITY_FLAG + BASE_FLAG + ".*)?) "
            + "(?<" + PRICE_GROUP + ">(?:" + PRICE_FLAG + BASE_FLAG + ".*)?) "
            + "(?<" + EX_DATE_GROUP + ">(?:" + EX_DATE_FLAG + BASE_FLAG + ".*)?) ";
    private static final String LIST_COMMAND_REGEX = "(?<" + QUANTITY_GROUP + ">(?:" + QUANTITY_FLAG + BASE_FLAG
            + ".*)?) (?<" + PRICE_GROUP + ">(?:" + PRICE_FLAG + BASE_FLAG + ".*)?) "
            + "(?<" + EX_DATE_GROUP + ">(?:" + EX_DATE_FLAG + BASE_FLAG + ".*)?) "
            + "(?<" + SORT_QUANTITY_GROUP + ">(?:" + SORT_QUANTITY_FLAG + BASE_FLAG + ".*)?) "
            + "(?<" + SORT_PRICE_GROUP + ">(?:" + SORT_PRICE_FLAG + BASE_FLAG + ".*)?) "
            + "(?<" + SORT_EX_DATE_GROUP + ">(?:" + SORT_EX_DATE_FLAG + BASE_FLAG + ".*)?) "
            + "(?<" + REVERSE_GROUP + ">(?:" + REVERSE_FLAG + BASE_FLAG + ".*)?) ";
    private static final String DELETE_COMMAND_REGEX = NAME_FLAG + BASE_FLAG + "(?<" + NAME_GROUP + ">.*) ";
    private static final String ADD_COMMAND_REGEX = NAME_FLAG + BASE_FLAG + "(?<" + NAME_GROUP + ">.*) "
            + QUANTITY_FLAG + BASE_FLAG + "(?<" + QUANTITY_GROUP + ">.*) ";
    private static final String REMOVE_COMMAND_REGEX = NAME_FLAG + BASE_FLAG + "(?<" + NAME_GROUP + ">.*) "
            + QUANTITY_FLAG + BASE_FLAG + "(?<" + QUANTITY_GROUP + ">.*) ";
    private static final String FIND_COMMAND_REGEX = NAME_FLAG + BASE_FLAG + "(?<" + NAME_GROUP + ">.*) ";
    private static final String REPORT_COMMAND_REGEX = REPORT_TYPE_FLAG + BASE_FLAG + "(?<" + REPORT_TYPE_GROUP +
            ">.*) " + "(?<" + THRESHOLD_GROUP + ">(?:" + THRESHOLD_FLAG + BASE_FLAG + ".*)?) ";
    private static final String BUY_COMMAND_REGEX = NAME_FLAG + BASE_FLAG + "(?<" + NAME_GROUP + ">.*) "
            + QUANTITY_FLAG + BASE_FLAG + "(?<" + QUANTITY_GROUP + ">.*) "
            + PRICE_FLAG + BASE_FLAG + "(?<" + PRICE_GROUP + ">.*) ";
    private static final String SELL_COMMAND_REGEX = NAME_FLAG + BASE_FLAG + "(?<" + NAME_GROUP + ">.*) "
            + QUANTITY_FLAG + BASE_FLAG + "(?<" + QUANTITY_GROUP + ">.*) ";
    private static final String CLEAR_COMMAND_REGEX = "(?<" + BEFORE_DATE_GROUP + ">(?:"
            + BEFORE_DATE_FLAG + BASE_FLAG + ".*)?) ";
    private static final String EXP_COMMAND_REGEX = TYPE_FLAG + BASE_FLAG + "(?<" + TYPE_GROUP + ">.*) " +
            "(?<" + FROM_GROUP + ">(?:" + FROM_FLAG + BASE_FLAG + ".*)?) " +
            "(?<" + TO_GROUP + ">(?:" + TO_FLAG + BASE_FLAG + ".*)?) ";
    private static final String REV_COMMAND_REGEX = TYPE_FLAG + BASE_FLAG + "(?<" + TYPE_GROUP + ">.*) " +
            "(?<" + TO_GROUP + ">(?:" + TO_FLAG + BASE_FLAG + ".*)?) " +
            "(?<" + FROM_GROUP + ">(?:" + FROM_FLAG + BASE_FLAG + ".*)?) ";
    private static final String PROFIT_COMMAND_REGEX = TYPE_FLAG + BASE_FLAG + "(?<" + TYPE_GROUP + ">.*) " +
            "(?<" + TO_GROUP + ">(?:" + TO_FLAG + BASE_FLAG + ".*)?) " +
            "(?<" + FROM_GROUP + ">(?:" + FROM_FLAG + BASE_FLAG + ".*)?) ";
    private static final String RENAME_COMMAND_REGEX = NAME_FLAG + BASE_FLAG + "(?<" + NAME_GROUP + ">.*) " +
            "(?<" + NEW_NAME_GROUP + ">(?:" + NEW_NAME_FLAG + BASE_FLAG + ".*)?) ";

    /**
     * Returns the command word specified in the user input string
     *
     * @param input a String of the user's input
     * @return a String of the first word in the user input
     */
    private static String getCommandWord(String input) {
        if (!input.contains(" ")) {
            return input;
        }
        return input.substring(0, input.indexOf(" "));
    }

    /**
     * Returns the string of parameters right after the first word separated by white space in the user's input
     *
     * @param input a String of the user's input
     * @return a String of the parameters in the user input
     */
    private static String getParameters(String input) {
        if (!input.contains(" ")) {
            return "";
        }
        return input.substring(input.indexOf(" ")).trim();
    }

    /**
     * Parses a Command accordingly from the user input string
     *
     * @param input a String of the user's input
     * @return a Command to execute
     */
    public static Command parseCommand(String input) throws TrackerException {
        String commandWord = getCommandWord(input);
        String params = getParameters(input);

        Command command;
        switch (commandWord) {
        case QUIT_COMMAND:
            command = new QuitCommand();
            break;
        case HELP_COMMAND:
            command = new HelpCommand();
            break;
        case NEW_COMMAND:
            command = parseNewCommand(params);
            break;
        case LIST_COMMAND:
            command = parseListCommand(params);
            break;
        case UPDATE_COMMAND:
            command = parseUpdateCommand(params);
            break;
        case DELETE_COMMAND:
            command = parseDeleteCommand(params);
            break;
        case ADD_COMMAND:
            command = parseAddCommand(params);
            break;
        case REMOVE_COMMAND:
            command = parseRemoveCommand(params);
            break;
        case FIND_COMMAND:
            command = parseFindCommand(params);
            break;
        case REPORT_COMMAND:
            command = parseReportCommand(params);
            break;
        case BUY_COMMAND:
            command = parseBuyCommand(params);
            break;
        case SELL_COMMAND:
            command = parseSellCommand(params);
            break;
        case CLEAR_COMMAND:
            command = parseClearCommand(params);
            break;
        case RENAME_COMMAND:
            command = parseRenameCommand(params);
            break;
        case EXPENDITURE_COMMAND:
            command = parseExpenditureCommand(params);
            break;
        case REVENUE_COMMAND:
            command = parseRevenueCommand(params);
            break;
        case PROFIT_COMMAND:
            command = parseProfitCommand(params);
            break;
        default:
            command = new InvalidCommand();
            break;
        }
        return command;
    }

    /**
     * Returns a String in the format of a regex expression pattern for parsing of command inputs. The format depends
     * on the order of the flags in the input paramFlags String array. The inputParams "q/28 n/name n/nine" with the
     * paramFlags {n, q}, for example, will return a String "n/name q/28 " accordingly.
     *
     * @param inputParams a String of the input parameters
     * @param paramFlags a String array with the specified flags to split the input parameters
     * @return a String of the input parameters in the format of a regex expression specified by the input flags
     */
    private static String makeStringPattern(String inputParams, String[] paramFlags) {
        // Build the regex to split the inputParam String
        StringBuilder flagBuilder = new StringBuilder();
        for (String flag : paramFlags) {
            flagBuilder.append(flag);
            flagBuilder.append("|");
        }
        flagBuilder.deleteCharAt(flagBuilder.length() - 1);
        String flags = flagBuilder.toString();

        String[] params = inputParams.split("(?= (" + flags + ")" + BASE_FLAG + ")");
        StringBuilder stringPattern = new StringBuilder();

        for (String paramFlag : paramFlags) {
            for (String p : params) {
                if (p.trim().startsWith(paramFlag + BASE_FLAG)) {
                    stringPattern.append(p.trim());
                    break;
                }
            }
            stringPattern.append(" ");
        }

        return stringPattern.toString();
    }

    /**
     * Creates a relevant pattern string from the user's input parameters and matches the string to a regular
     * expression, returning a new Matcher object.
     *
     * @param regex the regular expression for any specific command input
     * @param input a String of the user's input parameters
     * @param paramFlags a String array with the specified flags to split the input parameters
     * @return a Matcher object that will check for a match between the user's input parameters and the relevant regex
     */
    private static Matcher getPatternMatcher(String regex, String input, String[] paramFlags) {
        Pattern p = Pattern.compile(regex);
        String commandPattern = makeStringPattern(input, paramFlags);
        assert commandPattern.length() >= paramFlags.length;
        return p.matcher(commandPattern);
    }

    private static double roundTo2Dp(double unroundedValue) {
        return Math.round(unroundedValue * ROUNDING_FACTOR) / ROUNDING_FACTOR;
    }

    private static void validateNonNegativeQuantity(String quantityString, int quantity) throws TrackerException {
        if (!quantityString.isEmpty() && quantity < 0) {
            throw new TrackerException(ErrorMessage.QUANTITY_TOO_SMALL);
        }
    }

    private static void validateNonNegativePrice(String priceString, double price) throws TrackerException {
        if (!priceString.isEmpty() && price < 0) {
            throw new TrackerException(ErrorMessage.PRICE_TOO_SMALL);
        }
    }

    private static void validateContainsOnlyDigits(String string) throws TrackerException {
        String regex = "\\d+";
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(string).matches()) {
            throw new TrackerException(ErrorMessage.QUANTITY_NOT_INTEGER);
        }
    }

    private static void validateNotTooLarge(String string) throws TrackerException {
        String maxIntString = String.valueOf(Integer.MAX_VALUE);
        if (string.length() > 10 || (string.length() == 10 && string.compareTo(maxIntString) > 0)) {
            throw new TrackerException(ErrorMessage.QUANTITY_TOO_LARGE);
        }
    }

    private static int parseQuantity(String quantityString) throws TrackerException {
        int quantity = -1;
        try {
            if (!quantityString.isEmpty()) {
                validateContainsOnlyDigits(quantityString);
                validateNotTooLarge(quantityString);
                quantity = Integer.parseInt(quantityString);
            }
            return quantity;
        } catch (NumberFormatException e) {
            throw new TrackerException(ErrorMessage.INVALID_QUANTITY_FORMAT);
        }
    }

    private static double parsePrice(String priceString) throws TrackerException {
        double price = -1;
        try {
            if (!priceString.isEmpty()) {
                price = roundTo2Dp(Double.parseDouble(priceString));
            }
            if (price > Integer.MAX_VALUE) {
                throw new TrackerException(ErrorMessage.PRICE_TOO_LARGE);
            }
            return price;
        } catch (NumberFormatException e) {
            throw new TrackerException(ErrorMessage.INVALID_PRICE_FORMAT);
        }
    }

    //@@author vimalapugazhan
    /**
     * Checks for invalid dates inputted even if they follow the correct format (e.g. 31-02-2024) by
     * comparing the datesString to the string derived from the parsed date.
     *
     * @param date DateString that has been parsed.
     * @param dateString Date that the user has inputted as a string.
     * @throws TrackerException If the input date is invalid.
     */
    private static void validateDate(LocalDate date, String dateString) throws TrackerException {
        if (!date.format(EX_DATE_FORMAT).equals(dateString)) {
            throw new TrackerException(ErrorMessage.INVALID_DATE);
        }
    }
    //@@author

    //@@author vimalapugazhan
    /**
     * Parses the date inputted to a LocalDate type.
     *
     * @param dateString Dates inputted as string to be parsed into LocalDate type.
     * @return date Parsed valid LocalDate dates.
     * @throws TrackerException If dateString is inputted in the wrong format.
     */
    private static LocalDate parseDate(String dateString) throws TrackerException {
        LocalDate date = UNDEFINED_DATE;
        try {
            if (!dateString.isEmpty()) {
                date = LocalDate.parse(dateString, EX_DATE_FORMAT);
                validateDate(date, dateString);
            }
            return date;
        } catch (DateTimeParseException e) {
            throw new TrackerException(ErrorMessage.INVALID_DATE_FORMAT);
        }
    }
    //@@author

    //@@author vimalapugazhan
    /**
     * Parses the inputted string into a valid date if inputted string contains a new date or
     * sets the new expiry date as undefined if the user inputs nil (to remove the expiry date from the item)
     *
     * @param dateString Dates inputted as string to be parsed into LocalDate type.
     * @return expiryDate The parsed LocalDate that is used in the updateCommand.
     * @throws TrackerException If DateTimeParseException when the date is the wrong format
     * @throws TrackerException If NumberFormatException when date does not consist of integers.
     */
    private static LocalDate parseExpiryDateUpdate(String dateString) throws TrackerException {
        LocalDate expiryDate = LocalDate.parse("1-1-1", DateTimeFormatter.ofPattern("y-M-d"));

        try {
            if (!dateString.isEmpty()) {
                if (dateString.equals("nil")) {
                    expiryDate = UNDEFINED_DATE;
                } else {
                    expiryDate = LocalDate.parse(dateString, EX_DATE_FORMAT);
                    validateDate(expiryDate, dateString);
                }
            }
        } catch (NumberFormatException e) {
            throw new TrackerException(ErrorMessage.INVALID_NUMBER_FORMAT);
        } catch (DateTimeParseException e) {
            throw new TrackerException(ErrorMessage.INVALID_DATE_FORMAT);
        }
        return expiryDate;
    }
    //@@author

    private static void validateItemExistsInInventory(String name, String errorMessage) throws TrackerException {
        if (!Inventory.contains(name)) {
            throw new TrackerException(name + errorMessage);
        }
    }

    private static String validateItemNotInInventory(String name) throws TrackerException {
        String itemName = name;
        if (name.contains(FILE_DELIMITER)) {
            itemName = name.replace(FILE_DELIMITER, "");
            Ui.printItemNameLimitation(name, FILE_DELIMITER, itemName);
        }

        if (Inventory.contains(itemName)) {
            throw new TrackerException(itemName + ErrorMessage.ITEM_IN_LIST_NEW);
        }
        return itemName;
    }

    private static void validateNonEmptyParamsUpdate(String name, String quantityString, String priceString,
            String expiryString)
            throws TrackerException {
        if (name.isEmpty() || (quantityString.isEmpty() && priceString.isEmpty() && expiryString.isEmpty())) {
            throw new TrackerException(ErrorMessage.EMPTY_PARAM_INPUT);
        }
    }

    private static void validateNonEmptyParam(String string) throws TrackerException {
        if (string.isEmpty()) {
            throw new TrackerException(ErrorMessage.EMPTY_PARAM_INPUT);
        }
    }

    private static ArrayList<Integer> getParamPositions(String input, boolean hasQuantity, boolean hasPrice,
            boolean hasExpiry, String quantityFlag, String priceFlag, String expiryFlag) {
        ArrayList<Integer> paramPositions =  new ArrayList<>();
        // to check if p, q, e appears first and second

        int quantityPosition;
        int pricePosition;
        int expiryPosition;

        if (hasQuantity) {
            quantityPosition = input.indexOf(quantityFlag + BASE_FLAG);
            paramPositions.add(quantityPosition);
        }
        if (hasPrice) {
            pricePosition = input.indexOf(priceFlag + BASE_FLAG);
            paramPositions.add(pricePosition);
        }
        if (hasExpiry) {
            expiryPosition = input.indexOf(expiryFlag + BASE_FLAG);
            paramPositions.add(expiryPosition);
        }

        Collections.sort(paramPositions);
        assert paramPositions.size() <= 3;

        return paramPositions;
    }

    private static String extractParam(String input, ArrayList<Integer> paramOrder, int index, boolean isSort) {
        try {
            int paramPos = paramOrder.get(index);
            if (isSort) {
                return input.substring(paramPos + 1, paramPos + 2);
            }
            return input.substring(paramPos, paramPos + 1);
        } catch (IndexOutOfBoundsException | NullPointerException ignored) {
            return "";
        }
    }

    //@@ author dtaywd
    /**
     * Validates the input parameters for the report command to ensure they are not empty.
     *
     * @param reportType      The type of report to generate ("low stock" or "expiry").
     * @param thresholdString The threshold value as a string (for "low stock" report).
     * @throws TrackerException If the input parameters are empty or invalid.
     */
    private static void validateNonEmptyParamsReport(String reportType, String thresholdString)
            throws TrackerException {
        if (reportType.isEmpty() || (reportType.equals("low stock") && thresholdString.isEmpty())) {
            throw new TrackerException(ErrorMessage.EMPTY_PARAM_INPUT);
        }
    }

    /**
     * Validates the format of the report command parameters.
     *
     * @param reportType      The type of report to generate ("low stock" or "expiry").
     * @param thresholdString The threshold value as a string (for "low stock" report).
     * @throws TrackerException If the report format is invalid for the specified report type.
     */
    private static void validateReportFormat(String reportType, String thresholdString) throws TrackerException {
        if (reportType.equals("expiry") && !thresholdString.isEmpty()) {
            throw new TrackerException(ErrorMessage.INVALID_EXPIRY_REPORT_FORMAT);
        }
    }

    /**
     * Validates the report type to ensure it is either "expiry" or "low stock".
     *
     * @param reportType The type of report to generate ("expiry" or "low stock").
     * @throws TrackerException If the report type is invalid.
     */
    private static void validateReportType(String reportType) throws TrackerException {
        if (!reportType.equals("expiry") && !reportType.equals("low stock")) {
            throw new TrackerException(ErrorMessage.INVALID_REPORT_TYPE);
        }
    }
    //@@author

    private static void validateNoIntegerOverflow(String name, int quantityToAdd) throws TrackerException {
        Item item = Inventory.get(name);
        int currentQuantity = item.getQuantity();
        int maximumPossibleAddQuantity = Integer.MAX_VALUE - currentQuantity;
        if (quantityToAdd > maximumPossibleAddQuantity) {
            throw new TrackerException(ErrorMessage.INTEGER_OVERFLOW);
        }
    }

    //@@author vimalapugazhan
    /**
     * Checks for invalid inputs for each taskType by
     * ensuring the params for each type is present and the params are valid.
     *
     * @param taskType          The task type (e.g., "today", "total", "day", "range").
     * @param hasStart          Whether a start date flag is present.
     * @param hasEnd            Whether an end date flag is present.
     * @param command           Revenue, Expenditure or Profit command that requires checking of format.
     * @param hasStartParam     The string inputted after the start date flag is not empty.
     * @param hasEndParam       The string inputted after the end date flag is not empty.
     * @throws TrackerException If the methods called in this method throws TrackerException.
     */
    private static void validateRevExpProfitFormat(
        String taskType,
        boolean hasStart,
        boolean hasEnd,
        String command,
        boolean hasStartParam,
        boolean hasEndParam
    ) throws TrackerException {
        switch (taskType) {
        case TODAY:
            todayErrorFormat(hasStart, hasEnd, command);
            break;
        case TOTAL:
            totalErrorFormat(hasStart, hasEnd, command);
            break;
        case DAY:
            dayErrorFormat(hasStart, hasEnd, command, hasStartParam);
            break;
        case RANGE:
            rangeErrorFormat(hasStart, hasEnd, command, hasStartParam, hasEndParam);
            break;
        default:
            handleInvalidFormat(command);
            break;
        }
    }
    //@@author

    //@@author dtaywd
    /**
     * Throws a TrackerException if the specified command has invalid format for "today" task.
     *
     * @param hasStart Whether a start date flag is present.
     * @param hasEnd   Whether an end date flag is present.
     * @param command  The type of command (e.g., EXPENDITURE_COMMAND or REVENUE_COMMAND).
     * @throws TrackerException If the command format is invalid for the "today" task.
     */
    private static void todayErrorFormat(boolean hasStart, boolean hasEnd, String command) throws TrackerException {
        if (hasStart || hasEnd) {
            switch (command) {
            case EXPENDITURE_COMMAND:
                throw new TrackerException(ErrorMessage.INVALID_EXP_TODAY_FORMAT);
            case REVENUE_COMMAND:
                throw new TrackerException(ErrorMessage.INVALID_REV_TODAY_FORMAT);
            case PROFIT_COMMAND:
                throw new TrackerException(ErrorMessage.INVALID_PROFIT_TODAY_FORMAT);
            default:

            }
        }
    }

    /**
     * Throws a TrackerException if the specified command has invalid format for "total" task.
     *
     * @param hasStart Whether a start date flag is present.
     * @param hasEnd   Whether an end date flag is present.
     * @param command  The type of command (e.g., EXPENDITURE_COMMAND or REVENUE_COMMAND).
     * @throws TrackerException If the command format is invalid for the "total" task.
     */
    private static void totalErrorFormat(boolean hasStart, boolean hasEnd, String command) throws TrackerException {
        if (hasStart || hasEnd) {
            switch (command) {
            case EXPENDITURE_COMMAND:
                throw new TrackerException(ErrorMessage.INVALID_EXP_TOTAL_FORMAT);
            case REVENUE_COMMAND:
                throw new TrackerException(ErrorMessage.INVALID_REV_TOTAL_FORMAT);
            case PROFIT_COMMAND:
                throw new TrackerException(ErrorMessage.INVALID_PROFIT_TOTAL_FORMAT);
            default:

            }
        }
    }

    /**
     * Throws a TrackerException if the specified command has invalid format for "day" task.
     *
     * @param hasStart      Whether a start date flag is present.
     * @param hasEnd        Whether an end date flag is present.
     * @param command       The type of command (e.g., EXPENDITURE_COMMAND or REVENUE_COMMAND).
     * @param hasStartParam Whether the start date parameter is provided and valid.
     * @throws TrackerException If the command format is invalid for the "day" task.
     */
    private static void dayErrorFormat(
            boolean hasStart,
            boolean hasEnd,
            String command,
            boolean hasStartParam)
            throws TrackerException {
        if (!hasStart || hasEnd) {
            switch (command) {
            case EXPENDITURE_COMMAND:
                throw new TrackerException(ErrorMessage.INVALID_EXP_DAY_FORMAT);
            case REVENUE_COMMAND:
                throw new TrackerException(ErrorMessage.INVALID_REV_DAY_FORMAT);
            case PROFIT_COMMAND:
                throw new TrackerException(ErrorMessage.INVALID_PROFIT_DAY_FORMAT);
            default:

            }
        } else if (!hasStartParam) {
            throw new TrackerException(ErrorMessage.EMPTY_PARAM_INPUT);
        }
    }

    /**
     * Throws a TrackerException if the specified command has invalid format for "range" task.
     *
     * @param hasStart      Whether a start date flag is present.
     * @param hasEnd        Whether an end date flag is present.
     * @param command       The type of command (e.g., EXPENDITURE_COMMAND or REVENUE_COMMAND).
     * @param hasStartParam Whether the start date parameter is provided and valid.
     * @param hasEndParam   Whether the end date parameter is provided and valid.
     * @throws TrackerException If the command format is invalid for the "range" task.
     */
    private static void rangeErrorFormat(
            boolean hasStart,
            boolean hasEnd,
            String command,
            boolean hasStartParam,
            boolean hasEndParam)
            throws TrackerException {
        if (!hasStart || !hasEnd) {
            switch (command) {
            case EXPENDITURE_COMMAND:
                throw new TrackerException(ErrorMessage.INVALID_EXP_RANGE_FORMAT);
            case REVENUE_COMMAND:
                throw new TrackerException(ErrorMessage.INVALID_REV_RANGE_FORMAT);
            case PROFIT_COMMAND:
                throw new TrackerException(ErrorMessage.INVALID_PROFIT_RANGE_FORMAT);
            default:

            }
        } else if (!hasStartParam || !hasEndParam) {
            throw new TrackerException(ErrorMessage.EMPTY_PARAM_INPUT);
        }
    }

    /**
     * Throws a TrackerException for handling an invalid command format.
     *
     * @param command The type of command (e.g., EXPENDITURE_COMMAND or REVENUE_COMMAND).
     * @throws TrackerException If the command format is invalid.
     */
    private static void handleInvalidFormat(String command) throws TrackerException {
        switch (command) {
        case EXPENDITURE_COMMAND:
            throw new TrackerException(ErrorMessage.INVALID_EXP_FORMAT);
        case REVENUE_COMMAND:
            throw new TrackerException(ErrorMessage.INVALID_REV_FORMAT);
        case PROFIT_COMMAND:
            throw new TrackerException(ErrorMessage.INVALID_PROFIT_FORMAT);
        default:

        }
    }
    //@@author

    private static Command parseNewCommand(String input) throws TrackerException {
        String[] flags = {NAME_FLAG, QUANTITY_FLAG, PRICE_FLAG, EX_DATE_FLAG};
        Matcher matcher = getPatternMatcher(NEW_COMMAND_REGEX, input, flags);

        if (!matcher.matches()) {
            throw new TrackerException(ErrorMessage.INVALID_NEW_ITEM_FORMAT);
        }

        String nameInput = matcher.group(NAME_GROUP).trim();
        String quantityString = matcher.group(QUANTITY_GROUP).trim();
        String priceString = matcher.group(PRICE_GROUP).trim();
        String dateString = matcher.group(EX_DATE_GROUP).trim().replace(EX_DATE_FLAG + BASE_FLAG, "");

        validateNonEmptyParam(nameInput);
        validateNonEmptyParam(quantityString);
        validateNonEmptyParam(priceString);
        String name = validateItemNotInInventory(nameInput);

        int quantity = parseQuantity(quantityString);
        double price = parsePrice(priceString);
        LocalDate expiryDate = parseDate(dateString);

        validateNonNegativeQuantity(quantityString, quantity);
        validateNonNegativePrice(priceString, price);

        return new NewCommand(name, quantity, price, expiryDate);
    }

    //@@author dtaywd
    /**
     * Parses the input string to create an UpdateCommand based on the specified format.
     *
     * @param input The input string containing the update command.
     * @return An UpdateCommand object parsed from the input string.
     * @throws TrackerException If there is an error parsing or validating the update command.
     */
    private static Command parseUpdateCommand(String input) throws TrackerException {
        String[] flags = {NAME_FLAG, QUANTITY_FLAG, PRICE_FLAG, EX_DATE_FLAG};
        Matcher matcher = getPatternMatcher(UPDATE_COMMAND_REGEX, input, flags);

        if (!matcher.matches()) {
            throw new TrackerException(ErrorMessage.INVALID_UPDATE_FORMAT);
        }

        String name = matcher.group(NAME_GROUP).trim();
        String quantityString = matcher.group(QUANTITY_GROUP).replace(QUANTITY_FLAG + BASE_FLAG, "").trim();
        String priceString = matcher.group(PRICE_GROUP).replace(PRICE_FLAG + BASE_FLAG, "").trim();
        String dateString = matcher.group(EX_DATE_GROUP).replace(EX_DATE_FLAG + BASE_FLAG, "").trim();

        validateNonEmptyParamsUpdate(name, quantityString, priceString, dateString);
        validateItemExistsInInventory(name, ErrorMessage.ITEM_NOT_IN_LIST_UPDATE);

        int quantity = parseQuantity(quantityString);
        double price = parsePrice(priceString);
        LocalDate expiryDate = parseExpiryDateUpdate(dateString);

        validateNonNegativeQuantity(quantityString, quantity);
        validateNonNegativePrice(priceString, price);

        return new UpdateCommand(name, quantity, price, expiryDate);
    }
    //@@author

    private static Command parseRenameCommand(String input) throws TrackerException {
        String[] flags = {NAME_FLAG, NEW_NAME_FLAG};
        Matcher matcher = getPatternMatcher(RENAME_COMMAND_REGEX, input, flags);

        if (!matcher.matches()) {
            throw new TrackerException(ErrorMessage.INVALID_RENAME_FORMAT);
        }

        String name = matcher.group(NAME_GROUP).trim();
        validateNonEmptyParam(name);
        String newName = matcher.group(NEW_NAME_GROUP).replace(NEW_NAME_FLAG + BASE_FLAG, "").trim();
        validateNonEmptyParam(newName);
        validateItemExistsInInventory(name, ErrorMessage.ITEM_NOT_IN_LIST_RENAME);

        return new RenameCommand(name, newName);
    }

    private static Command parseListCommand(String input) throws TrackerException {
        String[] flags = {
            QUANTITY_FLAG,
            PRICE_FLAG,
            EX_DATE_FLAG,
            SORT_QUANTITY_FLAG,
            SORT_PRICE_FLAG,
            SORT_EX_DATE_FLAG,
            REVERSE_FLAG
        };

        Matcher matcher = getPatternMatcher(LIST_COMMAND_REGEX, input, flags);

        if (!matcher.matches()) {
            throw new TrackerException(ErrorMessage.INVALID_LIST_FORMAT);
        }

        boolean hasQuantity = !matcher.group(QUANTITY_GROUP).isEmpty();
        boolean hasPrice = !matcher.group(PRICE_GROUP).isEmpty();
        boolean hasExpiry = !matcher.group(EX_DATE_GROUP).isEmpty();
        boolean hasSortQuantity = !matcher.group(SORT_QUANTITY_GROUP).isEmpty();
        boolean hasSortPrice = !matcher.group(SORT_PRICE_GROUP).isEmpty();
        boolean hasSortExpiry = !matcher.group(SORT_EX_DATE_GROUP).isEmpty();
        boolean isReverse = !matcher.group(REVERSE_GROUP).isEmpty();

        ArrayList<Integer> paramOrder = getParamPositions(input, hasQuantity, hasPrice, hasExpiry,
                QUANTITY_FLAG, PRICE_FLAG, EX_DATE_FLAG);
        String firstParam = extractParam(input, paramOrder, 0, false);
        String secondParam = extractParam(input, paramOrder, 1, false);
        String thirdParam = extractParam(input, paramOrder, 2, false);

        ArrayList<Integer> sortParamOrder = getParamPositions(input, hasSortQuantity, hasSortPrice, hasSortExpiry,
                SORT_QUANTITY_FLAG, SORT_PRICE_FLAG, SORT_EX_DATE_FLAG);
        String firstSortParam = extractParam(input, sortParamOrder, 0, true);
        String secondSortParam = extractParam(input, sortParamOrder, 1, true);
        String thirdSortParam = extractParam(input, sortParamOrder, 2, true);

        return new ListCommand(firstParam, secondParam, thirdParam,
                firstSortParam, secondSortParam, thirdSortParam, isReverse);
    }

    //@@author vimalapugazhan
    /**
     * Parse input to extract the name of item being deleted to return new DeleteCommand.
     *
     * @param input User inputted string containing the delete command.
     * @return DeleteCommand object containing the name of item being deleted.
     * @throws TrackerException If item does not exist in the list.
     */
    private static Command parseDeleteCommand(String input) throws TrackerException {
        String[] flags = {NAME_FLAG};
        Matcher matcher = getPatternMatcher(DELETE_COMMAND_REGEX, input, flags);

        if (!matcher.matches()) {
            throw new TrackerException(ErrorMessage.INVALID_DELETE_FORMAT);
        }

        String name = matcher.group(NAME_GROUP).trim();

        validateNonEmptyParam(name);
        validateItemExistsInInventory(name, ErrorMessage.ITEM_NOT_IN_LIST_DELETE);

        return new DeleteCommand(name);
    }
    //@@author

    private static Command parseAddCommand(String input) throws TrackerException {
        String[] flags = {NAME_FLAG, QUANTITY_FLAG};
        Matcher matcher = getPatternMatcher(ADD_COMMAND_REGEX, input, flags);

        if (!matcher.matches()) {
            throw new TrackerException(ErrorMessage.INVALID_ADD_FORMAT);
        }

        String name = matcher.group(NAME_GROUP).trim();
        String quantityString = matcher.group(QUANTITY_GROUP).trim();

        validateNonEmptyParam(name);
        validateNonEmptyParam(quantityString);
        validateItemExistsInInventory(name, ErrorMessage.ITEM_NOT_IN_LIST_ADD);

        int quantity = parseQuantity(quantityString);
        validateNonNegativeQuantity(quantityString, quantity);
        validateNoIntegerOverflow(name, quantity);

        return new AddCommand(name,quantity);
    }

    private static Command parseRemoveCommand(String input) throws TrackerException {
        String[] flags = {NAME_FLAG, QUANTITY_FLAG};
        Matcher matcher = getPatternMatcher(REMOVE_COMMAND_REGEX, input, flags);

        if (!matcher.matches()) {
            throw new TrackerException(ErrorMessage.INVALID_REMOVE_FORMAT);
        }

        String name = matcher.group(NAME_GROUP).trim();
        String quantityString = matcher.group(QUANTITY_GROUP).trim();

        validateNonEmptyParam(name);
        validateNonEmptyParam(quantityString);
        validateItemExistsInInventory(name, ErrorMessage.ITEM_NOT_IN_LIST_REMOVE);

        int quantity = parseQuantity(quantityString);
        validateNonNegativeQuantity(quantityString, quantity);

        return new RemoveCommand(name, quantity);
    }

    //@@author TimothyLKM
    private static Command parseFindCommand(String input) throws TrackerException {
        String[] flags = {NAME_FLAG};
        Matcher matcher = getPatternMatcher(FIND_COMMAND_REGEX, input, flags);

        if (!matcher.matches()) {
            throw new TrackerException(ErrorMessage.INVALID_FIND_FORMAT);
        }

        String name = matcher.group(NAME_GROUP).trim();

        validateNonEmptyParam(name);

        return new FindCommand(name);
    }
    //@@author

    /**
     * Parses the input string to create a ReportCommand based on the specified format.
     *
     * @param input The input string containing the report command.
     * @return A ReportCommand object parsed from the input string.
     * @throws TrackerException If there is an error parsing or validating the report command.
     */
    private static Command parseReportCommand(String input) throws TrackerException {
        String[] flags = {REPORT_TYPE_FLAG, THRESHOLD_FLAG};
        Matcher matcher = getPatternMatcher(REPORT_COMMAND_REGEX, input, flags);

        if (!matcher.matches()) {
            throw new TrackerException(ErrorMessage.INVALID_REPORT_FORMAT);
        }

        String reportType = matcher.group(REPORT_TYPE_GROUP).trim();
        String thresholdString = matcher.group(THRESHOLD_GROUP).
                replace(THRESHOLD_FLAG + BASE_FLAG, "").trim();

        validateNonEmptyParamsReport(reportType, thresholdString);
        validateReportFormat(reportType, thresholdString);
        validateReportType(reportType);

        int threshold = -1;
        if (reportType.equals("low stock")){
            threshold = parseQuantity(thresholdString);
            validateNonNegativeQuantity(thresholdString, threshold);
        }
        return new ReportCommand(reportType, threshold);
    }

    private static Command parseBuyCommand(String input) throws TrackerException {
        String[] flags = {NAME_FLAG, QUANTITY_FLAG, PRICE_FLAG};
        Matcher matcher = getPatternMatcher(BUY_COMMAND_REGEX, input, flags);

        if (!matcher.matches()) {
            throw new TrackerException(ErrorMessage.INVALID_BUY_FORMAT);
        }

        String name = matcher.group(NAME_GROUP).trim();
        String quantityString = matcher.group(QUANTITY_GROUP).trim();
        String priceString = matcher.group(PRICE_GROUP).trim();

        validateNonEmptyParam(name);
        validateNonEmptyParam(quantityString);
        validateNonEmptyParam(priceString);
        validateItemExistsInInventory(name, ErrorMessage.ITEM_NOT_IN_LIST_BUY);

        int quantity = parseQuantity(quantityString);
        double price = parsePrice(priceString);

        validateNonNegativeQuantity(quantityString, quantity);
        validateNonNegativePrice(priceString, price);
        validateNoIntegerOverflow(name, quantity);

        LocalDate currentDate = LocalDate.now();

        return new BuyCommand(name, quantity, price, currentDate);
    }

    private static Command parseSellCommand(String input) throws TrackerException {
        String[] flags = {NAME_FLAG, QUANTITY_FLAG};
        Matcher matcher = getPatternMatcher(SELL_COMMAND_REGEX, input, flags);

        if (!matcher.matches()) {
            throw new TrackerException(ErrorMessage.INVALID_SELL_FORMAT);
        }

        String name = matcher.group(NAME_GROUP).trim();
        String quantityString = matcher.group(QUANTITY_GROUP).trim();

        validateNonEmptyParam(name);
        validateNonEmptyParam(quantityString);
        validateItemExistsInInventory(name, ErrorMessage.ITEM_NOT_IN_LIST_SELL);

        int quantity = parseQuantity(quantityString);
        validateNonNegativeQuantity(quantityString, quantity);

        LocalDate currentDate = LocalDate.now();

        return new SellCommand(name, quantity, currentDate);
    }

    private static Command parseClearCommand(String input) throws TrackerException {
        String[] flags = {BEFORE_DATE_FLAG};
        Matcher matcher = getPatternMatcher(CLEAR_COMMAND_REGEX, input, flags);

        if (!matcher.matches()) {
            throw new TrackerException(ErrorMessage.INVALID_CLEAR_FORMAT);
        }

        String dateString = matcher.group(BEFORE_DATE_GROUP).replace(BEFORE_DATE_FLAG + BASE_FLAG, "").trim();
        LocalDate beforeDate = parseDate(dateString);

        if (beforeDate.isEqual(UNDEFINED_DATE)) {
            beforeDate = LocalDate.now();
        }

        return new ClearCommand(beforeDate);
    }

    //@@author dtaywd
    /**
     * Parses the input string to create an ExpenditureCommand based on the specified format.
     *
     * @param input The input string containing the expenditure command.
     * @return An ExpenditureCommand object parsed from the input string.
     * @throws TrackerException If there is an error parsing or validating the expenditure command.
     */
    private static Command parseExpenditureCommand(String input) throws TrackerException {
        String[] flags = {TYPE_FLAG, FROM_FLAG, TO_FLAG};
        Matcher matcher = getPatternMatcher(EXP_COMMAND_REGEX, input, flags);

        if (!matcher.matches()) {
            throw new TrackerException(ErrorMessage.INVALID_EXP_FORMAT);
        }

        boolean hasFrom = !matcher.group(FROM_GROUP).isEmpty();
        boolean hasTo = !matcher.group(TO_GROUP).isEmpty();

        String type = matcher.group(TYPE_GROUP).trim();
        String fromString = matcher.group(FROM_GROUP).replace(FROM_FLAG + BASE_FLAG, "").trim();
        String toString = matcher.group(TO_GROUP).replace(TO_FLAG + BASE_FLAG, "").trim();

        boolean hasStartParam = !fromString.isEmpty();
        boolean hasEndParam = !toString.isEmpty();

        validateRevExpProfitFormat(type, hasFrom, hasTo, EXPENDITURE_COMMAND, hasStartParam, hasEndParam);
        LocalDate to = parseDate(toString);
        LocalDate from = parseDate(fromString);

        return new ExpenditureCommand(type, from, to);
    }
    //@@author

    //@@author vimalapugazhan
    /**
     * Parses the input string to create a RevenueCommand based on the specified format.
     *
     * @param input The input string containing the revenue command.
     * @return RevenueCommand object parsed from the input string.
     * @throws TrackerException If there is an error parsing or validating the revenue command.
     */
    private static Command parseRevenueCommand(String input) throws TrackerException {
        String[] flags = {TYPE_FLAG, TO_FLAG, FROM_FLAG};
        Matcher matcher = getPatternMatcher(REV_COMMAND_REGEX, input, flags);

        if (!matcher.matches()) {
            throw new TrackerException(ErrorMessage.INVALID_REV_FORMAT);
        }

        boolean hasStart = !matcher.group(FROM_GROUP).isEmpty();
        boolean hasEnd = !matcher.group(TO_GROUP).isEmpty();

        String taskType = matcher.group(TYPE_GROUP).trim();
        String startDateString = matcher.group(FROM_GROUP).replace(FROM_GROUP + BASE_FLAG, "").trim();
        String endDateString = matcher.group(TO_GROUP).replace(TO_GROUP + BASE_FLAG, "").trim();

        boolean hasStartParam = !startDateString.isEmpty();
        boolean hasEndParam = !endDateString.isEmpty();

        validateRevExpProfitFormat(taskType, hasStart, hasEnd, REVENUE_COMMAND, hasStartParam, hasEndParam);
        LocalDate startDate = parseDate(startDateString);
        LocalDate endDate = parseDate(endDateString);

        return new RevenueCommand(taskType, startDate, endDate);
    }
    //@@author

    //@@author vimalapugazhan
    /**
     * Parses the input string to create a ProfitCommand based on the specified format.
     *
     * @param input The input string containing the profit command.
     * @return ProfitCommand object parsed from the input string.
     * @throws TrackerException If there is an error parsing or validating the profit command.
     */
    private static Command parseProfitCommand(String input) throws TrackerException {
        String[] flags = {TYPE_FLAG, TO_FLAG, FROM_FLAG};
        Matcher matcher = getPatternMatcher(PROFIT_COMMAND_REGEX, input, flags);

        if (!matcher.matches()) {
            throw new TrackerException(ErrorMessage.INVALID_REV_FORMAT);
        }

        boolean hasStart = !matcher.group(FROM_GROUP).isEmpty();
        boolean hasEnd = !matcher.group(TO_GROUP).isEmpty();

        String taskType = matcher.group(TYPE_GROUP).trim();
        String startDateString = matcher.group(FROM_GROUP).replace(FROM_GROUP + BASE_FLAG, "").trim();
        String endDateString = matcher.group(TO_GROUP).replace(TO_GROUP + BASE_FLAG, "").trim();

        boolean hasStartParam = !startDateString.isEmpty();
        boolean hasEndParam = !endDateString.isEmpty();

        validateRevExpProfitFormat(taskType, hasStart, hasEnd, PROFIT_COMMAND, hasStartParam, hasEndParam);
        LocalDate startDate = parseDate(startDateString);
        LocalDate endDate = parseDate(endDateString);

        return new ProfitCommand(taskType, startDate, endDate);
    }
    //@@author
}
