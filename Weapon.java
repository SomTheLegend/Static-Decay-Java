package in.stl.staticdecay;

/**
 * Represents a weapon item in the Static Decay game.
 */
public class Weapon extends Item {

    private final int damage;

    /**
     * Constructs a new Weapon object.
     *
     * @param name        The name of the weapon.
     * @param description The description of the weapon.
     * @param damage      The damage the weapon deals.
     */
    public Weapon(String name, String description, int damage) {
        super(name, description);
        this.damage = damage;
    }

    /**
     * Gets the damage inflicted by the weapon.
     *
     * @return The damage inflicted by the weapon.
     */
    public int getDamage() {
        return damage;
    }

}
