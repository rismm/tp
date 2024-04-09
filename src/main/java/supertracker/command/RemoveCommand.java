package supertracker.command;

import supertracker.item.Inventory;
import supertracker.item.Item;
import supertracker.storage.FileManager;
import supertracker.storage.ItemStorage;
import supertracker.ui.ErrorMessage;
import supertracker.ui.Ui;

import java.io.IOException;

public class RemoveCommand implements Command {
    protected String name;
    protected int quantity;
    protected int quantityRemoved;
    protected Item newItem;

    public RemoveCommand(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

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

    @Override
    public void execute() {
        executeWithoutUi();
        Ui.removeCommandSuccess(newItem, quantityRemoved);
    }

    @Override
    public boolean isQuit() {
        return false;
    }
}
