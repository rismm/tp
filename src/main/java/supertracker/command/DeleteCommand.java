package supertracker.command;

import supertracker.item.Inventory;
import supertracker.ui.Ui;

public class DeleteCommand implements Command {

    String itemName;

    public DeleteCommand(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public void execute() {
        Inventory.delete(itemName);
        Ui.deleteSuccess(itemName);
    }

    @Override
    public boolean isQuit() {
        return false;
    }
}
