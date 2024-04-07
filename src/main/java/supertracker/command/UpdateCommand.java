package supertracker.command;

import supertracker.storage.FileManager;
import supertracker.ui.ErrorMessage;
import supertracker.ui.Ui;
import supertracker.item.Inventory;
import supertracker.item.Item;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UpdateCommand implements Command {
    private String name;
    private int newQuantity;
    private double newPrice;
    private LocalDate newExpiryDate;

    public UpdateCommand(String name, int newQuantity, double newPrice, LocalDate newExpiryDate) {
        this.name = name;
        this.newQuantity = newQuantity;
        this.newPrice = newPrice;
        this.newExpiryDate = newExpiryDate;
    }

    // @@ author dtaywd
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
            FileManager.saveData();
        } catch (IOException e) {
            Ui.printError(ErrorMessage.FILE_SAVE_ERROR);
        }
    }

    @Override
    public boolean isQuit() {
        return false;
    }
}
