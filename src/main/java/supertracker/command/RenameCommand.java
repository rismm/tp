package supertracker.command;

import supertracker.item.Inventory;
import supertracker.item.Item;
import supertracker.storage.ItemStorage;
import supertracker.ui.ErrorMessage;
import supertracker.ui.Ui;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Represents a command that rename an existing item in the inventory.
 */
// @@author TimothyLKM
public class RenameCommand implements Command {
    private String name;
    private String newName;

    public RenameCommand(String name, String newName) {
        this.name = name;
        this.newName = newName;
    }

    /**
     * Executes the Rename command to create a new item with the new name and transfers over
     * the price, quantity and expiry date of the item.
     * Deletes the old item.
     */
    @Override
    public void execute() {
        assert Inventory.contains(name);

        Item oldItem = Inventory.get(name);
        String oldName = oldItem.getName();
        int quantity = oldItem.getQuantity();
        double price = oldItem.getPrice();
        LocalDate expiryDate = oldItem.getExpiryDate();

        Item newItem = new Item(newName, quantity, price, expiryDate);
        Inventory.delete(name);

        Inventory.put(newName, newItem);
        Ui.renameCommandSuccess(newItem, oldName);

        try {
            ItemStorage.saveData();
        } catch (IOException e) {
            Ui.printError(ErrorMessage.FILE_SAVE_ERROR);
        }
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
