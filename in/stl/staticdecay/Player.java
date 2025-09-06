package in.stl.staticdecay;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the player in the Static Decay game.
 */
public class Player {

    private int hp = 100;
    private int hunger = 100;
    private int sanity = 100;
    private int x;
    private int y;
    private final Map<Item, Integer> inventory = new HashMap<>();
    private Weapon equippedWeapon = null;

    /**
     * Constructs a new Player object with the specified coordinates.
     *
     * @param x The player's starting x-coordinate.
     * @param y The player's starting y-coordinate.
     */
    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the player's current hit points.
     *
     * @return The player's current hit points.
     */
    public int getHp() {
        return hp;
    }

    /**
     * Gets the player's current hunger level.
     *
     * @return The player's current hunger level.
     */
    public int getHunger() {
        return hunger;
    }

    /**
     * Gets the player's current sanity level.
     *
     * @return The player's current sanity level.
     */
    public int getSanity() {
        return sanity;
    }

    /**
     * Gets the player's current x-coordinate.
     *
     * @return The player's current x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the player's current y-coordinate.
     *
     * @return The player's current y-coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the player's inventory.
     *
     * @return The player's inventory.
     */
    public Map<Item, Integer> getInventory() {
        return inventory;
    }

    /**
     * Gets the player's equipped weapon.
     *
     * @return The player's equipped weapon.
     */
    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    /**
     * Sets the player's x-coordinate.
     *
     * @param x The new x-coordinate.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the player's y-coordinate.
     *
     * @param y The new y-coordinate.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Sets the player's equipped weapon.
     *
     * @param equippedWeapon The new equipped weapon.
     */
    public void setEquippedWeapon(Weapon equippedWeapon) {
        this.equippedWeapon = equippedWeapon;
    }

    /**
     * Reduces the player's hit points by the specified amount.
     *
     * @param damage The amount of damage to take.
     */
    public void takeDamage(int damage) {
        this.hp -= damage;
        if (hp < 0) hp = 0;
    }

    /**
     * Increases the player's hit points by the specified amount.
     *
     * @param amount The amount of health to restore.
     */
    public void heal(int amount) {
        this.hp += amount;
        if (hp > 100) hp = 100;
    }

    /**
     * Reduces the player's hunger level by the specified amount.
     *
     * @param amount The amount of hunger to lose.
     */
    public void loseHunger(int amount) {
        this.hunger -= amount;
        if (hunger < 0) {
            System.out.println("You are starving! You lose 5 HP.");
            takeDamage(5);
            this.hunger = 0;
        }
    }

    /**
     * Increases the player's hunger level by the specified amount.
     *
     * @param amount The amount of hunger to restore.
     */
    public void eat(int amount) {
        this.hunger += amount;
        if (hunger > 100) hunger = 100;
    }

    /**
     * Reduces the player's sanity level by the specified amount.
     *
     * @param amount The amount of sanity to lose.
     */
    public void loseSanity(int amount) {
        this.sanity -= amount;
        if (sanity < 0) sanity = 0;
    }

    /**
     * Increases the player's sanity level by the specified amount.
     *
     * @param amount The amount of sanity to gain.
     */
    public void gainSanity(int amount) {
        sanity += amount;
        if (sanity > 100) sanity = 100;
    }

    /**
     * Adds an item to the player's inventory.
     *
     * @param item  The item to add.
     * @param count The number of items to add.
     */
    public void addItem(Item item, int count) {
        inventory.put(item, inventory.getOrDefault(item, 0) + count);
    }

    /**
     * Removes an item from the player's inventory.
     *
     * @param item  The item to remove.
     * @param count The number of items to remove.
     * @return True if the item was successfully removed, false otherwise.
     */
    public boolean removeItem(Item item, int count) {
        int currentCount = inventory.getOrDefault(item, 0);
        if (currentCount >= count) {
            inventory.put(item, currentCount - count);
            if (inventory.get(item) == 0) {
                inventory.remove(item);
            }
            return true;
        }
        return false;
    }

    /**
     * Checks if the player has a specific item in their inventory.
     *
     * @param itemName The name of the item to check for.
     * @return True if the player has the item, false otherwise.
     */
    public boolean hasItem(String itemName) {
        return inventory.keySet().stream().anyMatch(item -> item.getName().equalsIgnoreCase(itemName));
    }
}
