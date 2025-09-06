package in.stl.staticdecay;

/**
 * Represents a Shambler creature in the Static Decay game.
 */
public class Shambler extends Creature {

    /**
     * Constructs a new Shambler object.
     *
     * @param x The x-coordinate of the Shambler.
     * @param y The y-coordinate of the Shambler.
     */
    public Shambler(int x, int y) {
        super("Shambler", 30, 10, x, y);
    }
}
