package supertracker.parser;

import supertracker.TrackerException;

import supertracker.command.AddCommand;
import supertracker.command.BuyCommand;
import supertracker.command.Command;
import supertracker.command.DeleteCommand;
import supertracker.command.FindCommand;
import supertracker.command.InvalidCommand;
import supertracker.command.ListCommand;
import supertracker.command.NewCommand;
import supertracker.command.QuitCommand;
import supertracker.command.RemoveCommand;
import supertracker.command.ReportCommand;
import supertracker.command.RevenueCommand;
import supertracker.command.SellCommand;
import supertracker.command.UpdateCommand;
import supertracker.command.ExpenditureCommand;

import supertracker.item.Inventory;
import supertracker.item.Item;
import supertracker.item.TransactionList;
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
    private static final String QUIT_COMMAND = "quit";
    private static final String NEW_COMMAND = "new";
    private static final String LIST_COMMAND = "list";
    private static final String UPDATE_COMMAND = "update";
    private static final String DELETE_COMMAND = "delete";
    private static final String ADD_COMMAND = "add";
    private static final String REMOVE_COMMAND = "remove";
    private static final String FIND_COMMAND = "find";
    private static final String REPORT_COMMAND = "report";
    private static final String BUY_COMMAND = "buy";
    private static final String SELL_COMMAND = "sell";
    private static final String EXPENDITURE_COMMAND = "exp";
    private static final String REVENUE_COMMAND = "rev";
    private static final double ROUNDING_FACTOR = 100.0;
    private static final String BASE_FLAG = "/";
    private static final String NAME_FLAG = "n";
    private static final String QUANTITY_FLAG = "q";
    private static final String PRICE_FLAG = "p";
    private static final String EX_DATE_FLAG = "e";
    private static final String NAME_GROUP = "name";
    private static final String QUANTITY_GROUP = "quantity";
    private static final String PRICE_GROUP = "price";
    private static final String EX_DATE_GROUP = "expiry";
    private static final DateTimeFormatter DATE_FORMAT_NULL = DateTimeFormatter.ofPattern("dd-MM-yyyyy");
    private static final LocalDate UNDEFINED_DATE = LocalDate.parse("01-01-99999", DATE_FORMAT_NULL);
    private static final DateTimeFormatter EX_DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final String SORT_QUANTITY_FLAG = "sq";
    private static final String SORT_PRICE_FLAG = "sp";
    private static final String SORT_EX_DATE_FLAG = "se";
    private static final String REVERSE_FLAG = "r";
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

    //To be used in getPatternMatcher to split the input into its respective parameter groups
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
    private static final String EXP_COMMAND_REGEX = TYPE_FLAG + BASE_FLAG + "(?<" + TYPE_GROUP + ">.*) " +
            "(?<" + FROM_GROUP + ">(?:" + FROM_FLAG + BASE_FLAG + ".*)?) " +
            "(?<" + TO_GROUP + ">(?:" + TO_FLAG + BASE_FLAG + ".*)?) ";
    private static final String REV_COMMAND_REGEX = TYPE_FLAG + BASE_FLAG + "(?<" + TYPE_GROUP + ">.*) " +
            "(?<" + TO_GROUP + ">(?:" + TO_FLAG + BASE_FLAG + ".*)?) " +
            "(?<" + FROM_GROUP + ">(?:" + FROM_FLAG + BASE_FLAG + ".*)?) ";

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
        case REVENUE_COMMAND:
            command = parseRevenueCommand(params);
            break;
        case EXPENDITURE_COMMAND:
            command = parseExpenditureCommand(params);
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
    private static void validateDate(LocalDate expiryDate, String dateString) throws TrackerException {
        if (!expiryDate.format(EX_DATE_FORMAT).equals(dateString)) {
            throw new TrackerException(ErrorMessage.INVALID_DATE);
        }
    }
    //@@author

    //@@author vimalapugazhan
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

    private static void validateNonEmptyParamsReport(String reportType, String thresholdString)
            throws TrackerException {
        if (reportType.isEmpty() || (reportType.equals("low stock") && thresholdString.isEmpty())) {
            throw new TrackerException(ErrorMessage.EMPTY_PARAM_INPUT);
        }
    }

    private static void validateReportFormat(String reportType, String thresholdString) throws TrackerException {
        if (reportType.equals("expiry") && !thresholdString.isEmpty()) {
            throw new TrackerException(ErrorMessage.INVALID_EXPIRY_REPORT_FORMAT);
        }
    }

    private static void validateReportType(String reportType) throws TrackerException {
        if (!reportType.equals("expiry") && !reportType.equals("low stock")) {
            throw new TrackerException(ErrorMessage.INVALID_REPORT_TYPE);
        }
    }

    private static void validateNoIntegerOverflow(String name, int quantityToAdd) throws TrackerException {
        Item item = Inventory.get(name);
        int currentQuantity = item.getQuantity();
        int maximumPossibleAddQuantity = Integer.MAX_VALUE - currentQuantity;
        if (quantityToAdd > maximumPossibleAddQuantity) {
            throw new TrackerException(ErrorMessage.INTEGER_OVERFLOW);
        }
    }

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

        //@@author vimalapugazhan
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
    //@@author

    //@@author vimalapugazhan
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

    //@@author dtaywd
    private static Command parseExpenditureCommand(String input) throws TrackerException {
        String[] flags = {TYPE_FLAG, FROM_FLAG, TO_FLAG};
        Matcher matcher = getPatternMatcher(EXP_COMMAND_REGEX, input, flags);

        if (!matcher.matches()) {
            throw new TrackerException(ErrorMessage.INVALID_EXP_FORMAT);
        }

        boolean hasStart = !matcher.group(FROM_GROUP).isEmpty();
        boolean hasEnd = !matcher.group(TO_GROUP).isEmpty();

        String type = matcher.group(TYPE_GROUP).trim();
        String fromString = matcher.group(FROM_GROUP).replace(FROM_FLAG + BASE_FLAG, "").trim();
        String toString = matcher.group(TO_GROUP).replace(TO_FLAG + BASE_FLAG, "").trim();

        validateRevExpFormat(type, hasStart, hasEnd);
        LocalDate to = parseDate(toString);
        LocalDate from = parseDate(fromString);
        validateNonNegativeForExpRev(type, from, to, "b");

        return new ExpenditureCommand(type, from, to);
    }
    //@@author

    //@@author vimalapugazhan
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

        validateRevExpFormat(taskType, hasStart, hasEnd);
        LocalDate startDate = parseDate(startDateString);
        LocalDate endDate = parseDate(endDateString);
        validateNonNegativeForExpRev(taskType, startDate, endDate, "s");

        return new RevenueCommand(taskType, startDate, endDate);
    }
    //@@author

    //@@author vimalapugazhan
    private static void validateRevExpFormat(String taskType, boolean hasStart, boolean hasEnd)
            throws TrackerException {
        switch (taskType) {
        case TODAY:
            if (hasStart || hasEnd) {
                throw new TrackerException(ErrorMessage.INVALID_REV_TODAY_FORMAT);
            }
            break;
        case TOTAL:
            if (hasStart || hasEnd) {
                throw new TrackerException(ErrorMessage.INVALID_REV_TOTAL_FORMAT);
            }
            break;
        case DAY:
            if (!hasStart || hasEnd) {
                throw new TrackerException(ErrorMessage.INVALID_REV_DAY_FORMAT);
            }
            break;
        case RANGE:
            if (!hasStart || !hasEnd) {
                throw new TrackerException(ErrorMessage.INVALID_REV_RANGE_FORMAT);
            }
            break;
        default: assert taskType.isEmpty();
            break;
        }
    }
    //@@author

    private static void validateNonNegativeForExpRev(String taskType, LocalDate startDate,
                                                          LocalDate endDate, String flag) throws TrackerException {
        double expenditure = 0;
        switch (taskType) {
        case "today":
            LocalDate currDate = LocalDate.now();
            expenditure = TransactionList.calculateDay(currDate, flag);
            break;
        case "total":
            expenditure = TransactionList.calculateTotal(flag);
            break;
        case "day":
            expenditure = TransactionList.calculateDay(startDate, flag);
            break;
        case "range":
            expenditure = TransactionList.calculateRange(startDate, endDate, flag);
            break;
        default:
            assert taskType.isEmpty();
        }

        if (expenditure < 0) {
            throw new TrackerException(ErrorMessage.DOUBLE_OVERFLOW_REV_EXP);
        }
    }
}
