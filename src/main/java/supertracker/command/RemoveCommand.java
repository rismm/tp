package supertracker.command;

import supertracker.item.Inventory;
import supertracker.item.Item;
import supertracker.storage.ItemStorage;
import supertracker.ui.ErrorMessage;
import supertracker.ui.Ui;

import java.io.IOException;

/**
 * Represents a command for decreasing the quantity of an item.
 */
public class RemoveCommand implements Command {
    protected String name;
    protected int quantity;
    protected int quantityRemoved;
    protected Item newItem;

    /**
     * Constructs a new RemoveCommand object with the specified name and quantity.
     *
     * @param name     Name of the item to be removed
     * @param quantity Quantity of the item to be removed
     */
    public RemoveCommand(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    /**
     * Executes the RemoveCommand without displaying any user interface.
     * <p>
     * This method removes the specified quantity of an item from the inventory. It ensures
     * that the inventory contains the specified item and that the quantity is non-negative.
     * If the quantity to be removed exceeds the quantity of the item in the inventory,
     * the item's quantity is set to zero. After updating the inventory, the changes are saved
     * to persistent storage. If an IOException occurs while saving data, an error message is printed.
     *
     * @throws AssertionError If the inventory does not contain the specified item
     *                        or if the quantity is negative
     */
    protected void executeWithoutUi() {
        assert Inventory.contains(name);
        assert quantity >= 0;

        Item oldItem = Inventory.get(name);
        int newQuantity = oldItem.getQuantity() - quantity;
        newQuantity = Math.max(newQuantity, 0);
        quantityRemoved = oldItem.getQuantity() - newQuantity;
        newItem = new Item(oldItem.getName(), newQuantity, oldItem.getPrice(), oldItem.getExpiryDate());
        Inventory.put(name, newItem);

        try {
            ItemStorage.saveData();
        } catch (IOException e) {
            Ui.printError(ErrorMessage.FILE_SAVE_ERROR);
        }
    }

    /**
     * Executes the RemoveCommand, including user interface interactions.
     * <p>
     * This method executes the RemoveCommand by first removing the specified quantity
     * of an item from the inventory using the {@code executeWithoutUi()} method.
     * After that, it informs the user about the success of the command by
     * displaying a success message containing the details of the removed item and quantity.
     */
    @Override
    public void execute() {
        executeWithoutUi();
        Ui.removeCommandSuccess(newItem, quantityRemoved);
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
