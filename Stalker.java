package in.stl.staticdecay;

/**
 * Represents a Stalker creature in the Static Decay game.
 */
public class Stalker extends Creature {

    /**
     * Constructs a new Stalker object.
     *
     * @param x The x-coordinate of the Stalker.
     * @param y The y-coordinate of the Stalker.
     */
    public Stalker(int x, int y) {
        super("Stalker", 50, 15, x, y);
    }
}
