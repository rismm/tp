package supertracker.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Represents an inventory of items.
 */
public class Inventory {
    // HashMap to store items with their names as keys
    private static HashMap<String, Item> itemMap = new HashMap<>();

    /**
     * Checks if the inventory contains an item with the specified name.
     *
     * @param name Name of the item to check.
     * @return {@code true} if the inventory contains the item; {@code false} otherwise.
     */
    public static boolean contains(String name) {
        return itemMap.containsKey(name.toLowerCase());
    }

    /**
     * Retrieves the item with the specified name from the inventory.
     *
     * @param name Name of the item to retrieve.
     * @return Item with the specified name, or {@code null} if not found.
     */
    public static Item get(String name) {
        return itemMap.get(name.toLowerCase());
    }

    /**
     * Adds an item to the inventory.
     *
     * @param name Name of the item to add.
     * @param item Item to add to the inventory.
     */
    public static void put(String name, Item item) {
        itemMap.put(name.toLowerCase(), item);
    }

    /**
     * Deletes an item from the inventory.
     *
     * @param name Name of the item to delete.
     */
    public static void delete(String name) {
        itemMap.remove(name.toLowerCase());
    }

    /**
     * Clears all items from the inventory.
     */
    public static void clear() {
        itemMap.clear();
    }

    /**
     * Retrieves a list of all items in the inventory.
     *
     * @return List containing all items in the inventory.
     */
    public static List<Item> getItems() {
        Collection<Item> items = itemMap.values();
        return new ArrayList<>(items);
    }
}
