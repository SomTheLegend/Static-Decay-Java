package in.stl.staticdecay;

/**
 * Represents a Whisperer creature in the Static Decay game.
 */
public class Whisperer extends Creature {

    /**
     * Constructs a new Whisperer object.
     *
     * @param x The x-coordinate of the Whisperer.
     * @param y The y-coordinate of the Whisperer.
     */
    public Whisperer(int x, int y) {
        super("Whisperer", 20, 5, x, y);
    }

}
