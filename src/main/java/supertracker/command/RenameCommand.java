package supertracker.command;

import supertracker.item.Inventory;
import supertracker.item.Item;
import supertracker.storage.FileManager;
import supertracker.storage.ItemStorage;
import supertracker.ui.ErrorMessage;
import supertracker.ui.Ui;

import java.io.IOException;
import java.time.LocalDate;

// @@ author TimothyLKM
public class RenameCommand implements Command {
    private String name;
    private String newName;

    public RenameCommand(String name, String newName) {
        this.name = name;
        this.newName = newName;
    }


    @Override
    public void execute() {
        assert Inventory.contains(name);

        Item oldItem = Inventory.get(name);
        int quantity = oldItem.getQuantity();
        double price = oldItem.getPrice();
        LocalDate expiryDate = oldItem.getExpiryDate();

        Item newItem = new Item(newName, quantity, price, expiryDate);
        Inventory.delete(name);

        Inventory.put(newName, newItem);
        Ui.renameCommandSuccess(newItem, name);

        try {
            ItemStorage.saveData();
        } catch (IOException e) {
            Ui.printError(ErrorMessage.FILE_SAVE_ERROR);
        }
    }

    @Override
    public boolean isQuit() {
        return false;
    }
}
