package supertracker.command;

import supertracker.storage.ItemStorage;
import supertracker.ui.ErrorMessage;
import supertracker.ui.Ui;
import supertracker.item.Inventory;
import supertracker.item.Item;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Represents a command for creating a new item in the inventory.
 */
public class NewCommand implements Command {
    private String name;
    private int quantity;
    private double price;
    private LocalDate expiryDate;

    /**
     * Constructs a new NewCommand object with the specified name, quantity, price, and expiry date.
     *
     * @param name        Name of the new item
     * @param quantity    Initial quantity of the new item
     * @param price       Price of each unit of the new item
     * @param expiryDate  Expiry date of the new item
     */
    public NewCommand(String name, int quantity, double price, LocalDate expiryDate) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.expiryDate = expiryDate;
    }

    /**
     * Executes the NewCommand
     * <p>
     * This method adds a new item to the inventory with the specified name, quantity, price,
     * and expiry date. It ensures that the inventory does not already contain an item with the same name,
     * and that the quantity and price are non-negative. After adding the new item to the inventory,
     * it informs the user about the success of the command by displaying a success message.
     */
    @Override
    public void execute() {
        assert !Inventory.contains(name);
        assert quantity >= 0;
        assert price >= 0;

        Item item = new Item(name, quantity, price, expiryDate);
        Inventory.put(name, item);
        Ui.newCommandSuccess(item);

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
