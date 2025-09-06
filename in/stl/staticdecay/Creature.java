package in.stl.staticdecay;

/**
 * Represents a creature in the Static Decay game.
 */
public class Creature {

    protected String name;
    protected int hp;
    protected int attack;
    protected int x;
    protected int y;

    /**
     * Constructs a new Creature object.
     *
     * @param name   The name of the creature.
     * @param hp     The hit points of the creature.
     * @param attack The attack power of the creature.
     * @param x      The x-coordinate of the creature.
     * @param y      The y-coordinate of the creature.
     */
    public Creature(String name, int hp, int attack, int x, int y) {
        this.name = name;
        this.hp = hp;
        this.attack = attack;
        this.x = x;
        this.y = y;
    }

    /**
     * Reduces the creature's hit points by the specified amount.
     *
     * @param damage The amount of damage to take.
     */
    public void takeDamage(int damage) {
        this.hp -= damage;
    }

    /**
     * Gets the name of the creature.
     *
     * @return The name of the creature.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the current hit points of the creature.
     *
     * @return The current hit points of the creature.
     */
    public int getHp() {
        return hp;
    }

    /**
     * Gets the attack power of the creature.
     *
     * @return The attack power of the creature.
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Gets the x-coordinate of the creature.
     *
     * @return The x-coordinate of the creature.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the creature.
     *
     * @return The y-coordinate of the creature.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the x-coordinate of the creature.
     *
     * @param x The new x-coordinate.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the y-coordinate of the creature.
     *
     * @param y The new y-coordinate.
     */
    public void setY(int y) {
        this.y = y;
    }

}
