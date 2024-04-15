package supertracker.command;

import supertracker.item.Inventory;
import supertracker.storage.ItemStorage;
import supertracker.ui.ErrorMessage;
import supertracker.ui.Ui;

import java.io.IOException;

/**
 * Represents a command to delete an item from the Inventory.
 */
public class DeleteCommand implements Command {
    private String name;

    /**
     * Constructs a DeleteCommand with the specified name of item.
     *
     * @param name Name of item as well as its key in the HashMap Inventory.
     */
    public DeleteCommand(String name) {
        this.name = name;
    }

    /**
     * Checks if the item exists in the inventory and calls delete() method from inventory to remove the item.
     * Display the success message.
     */
    @Override
    public void execute() {
        assert Inventory.contains(name);

        Inventory.delete(name);
        Ui.deleteCommandSuccess(name);

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
