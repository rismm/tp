package supertracker.command;

import supertracker.item.Inventory;
import supertracker.item.Item;
import supertracker.ui.Ui;

import java.util.List;

/**
 * Represents a command to find an existing item in the inventory.
 */
//@@author TimothyLKM
public class FindCommand implements Command {
    private String name;

    /**
     * Constructs a FindCommand with the specified item details.
     *
     * @param name The name of the item to search for.
     */
    public FindCommand(String name) {
        this.name = name;
    }

    /**
     * Executes the Find command to show the various description of the item.
     */
    @Override
    public void execute() {
        int index = 1;
        boolean isFound = false;
        List<Item> items = Inventory.getItems();

        for (Item item : items) {
            if (item.getName().toLowerCase().contains(name.toLowerCase())) {
                Ui.printFoundItem(item, index);
                index++;
                isFound = true;
            }
        }
        if (!isFound) {
            Ui.printNoItemFound(name);
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
