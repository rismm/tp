package supertracker.command;

import supertracker.storage.ItemStorage;
import supertracker.ui.ErrorMessage;
import supertracker.ui.Ui;
import supertracker.item.Inventory;
import supertracker.item.Item;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a command to update an existing item in the inventory with new quantity, price, and expiry date.
 */
public class UpdateCommand implements Command {
    private String name;
    private int newQuantity;
    private double newPrice;
    private LocalDate newExpiryDate;

    /**
     * Constructs an UpdateCommand with the specified item details.
     *
     * @param name          The name of the item to update.
     * @param newQuantity   The new quantity for the item.
     * @param newPrice      The new price for the item.
     * @param newExpiryDate The new expiry date for the item.
     */
    public UpdateCommand(String name, int newQuantity, double newPrice, LocalDate newExpiryDate) {
        this.name = name;
        this.newQuantity = newQuantity;
        this.newPrice = newPrice;
        this.newExpiryDate = newExpiryDate;
    }

    /**
     * Executes the update command to modify an existing item in the inventory.
     * Updates the specified item with new quantity, price, and expiry date.
     */
    //@@author dtaywd
    @Override
    public void execute() {
        assert Inventory.contains(name);

        Item oldItem = Inventory.get(name);
        if (newQuantity == -1) {
            newQuantity = oldItem.getQuantity();
        }
        if (newPrice == -1) {
            newPrice = oldItem.getPrice();
        }

        LocalDate invalidDate = LocalDate.parse("1-1-1", DateTimeFormatter.ofPattern("y-M-d"));
        if (newExpiryDate.isEqual(invalidDate)) {
            newExpiryDate = oldItem.getExpiryDate();
        }

        assert newQuantity >= 0;
        assert newPrice >= 0;
        assert !newExpiryDate.isEqual(invalidDate);

        Item newItem = new Item(oldItem.getName(), newQuantity, newPrice, newExpiryDate);
        Inventory.put(name, newItem);
        Ui.updateCommandSuccess(newItem);

        try {
            ItemStorage.saveData();
        } catch (IOException e) {
            Ui.printError(ErrorMessage.FILE_SAVE_ERROR);
        }
    }
    //@@author

    /**
     * Indicates whether executing this command should result in quitting the application.
     *
     * @return Always returns false, as executing this command does not trigger application quit.
     */
    @Override
    public boolean isQuit() {
        return false;
    }
}
