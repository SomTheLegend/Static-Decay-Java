package in.stl.staticdecay;

/**
 * Represents an Anomaly creature in the Static Decay game.
 */
public class Anomaly extends Creature {

    /**
     * Constructs a new Anomaly object.
     *
     * @param x The x-coordinate of the Anomaly.
     * @param y The y-coordinate of the Anomaly.
     */
    public Anomaly(int x, int y) {
        super("Anomaly", 200, 0, x, y);
    }
}
