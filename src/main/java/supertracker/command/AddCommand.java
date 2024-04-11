package supertracker.command;

import supertracker.item.Inventory;
import supertracker.item.Item;
import supertracker.storage.ItemStorage;
import supertracker.ui.ErrorMessage;
import supertracker.ui.Ui;

import java.io.IOException;

/**
 * Represents a command for increasing the quantity of an item.
 */
public class AddCommand implements Command {
    protected String name;
    protected int quantity;
    protected Item newItem;

    /**
     * Constructs a new AddCommand object with the specified name and quantity.
     *
     * @param name     Name of the item to be added
     * @param quantity Quantity of the item to be added
     */
    public AddCommand(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    /**
     * Executes the AddCommand without displaying any user interface.
     * <p>
     * This method adds the specified quantity of an item to the inventory. It ensures
     * that the inventory contains the specified item and that the quantity is non-negative.
     * If the item already exists in the inventory, its quantity is updated accordingly.
     * After updating the inventory, the changes are saved to persistent storage.
     * If an IOException occurs while saving data, an error message is printed.
     *
     * @throws AssertionError If the inventory does not contain the specified item
     *                        or if the quantity is negative
     */
    protected void executeWithoutUi() {
        assert Inventory.contains(name);
        assert quantity >= 0;

        Item oldItem = Inventory.get(name);
        int newQuantity = oldItem.getQuantity() + quantity;
        newItem = new Item(oldItem.getName(), newQuantity, oldItem.getPrice(), oldItem.getExpiryDate());
        Inventory.put(name, newItem);

        try {
            ItemStorage.saveData();
        } catch (IOException e) {
            Ui.printError(ErrorMessage.FILE_SAVE_ERROR);
        }
    }

    /**
     * Executes the AddCommand, including user interface interactions.
     * <p>
     * This method executes the AddCommand by first adding the specified quantity
     * of an item to the inventory using the {@code executeWithoutUi()} method.
     * After that, it informs the user about the success of the command by
     * displaying a success message containing the details of the added item and quantity.
     */
    @Override
    public void execute() {
        executeWithoutUi();
        Ui.addCommandSuccess(newItem, quantity);
    }

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
