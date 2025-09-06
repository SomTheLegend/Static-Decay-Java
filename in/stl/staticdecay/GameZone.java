package in.stl.staticdecay;

import java.util.List;
import java.util.Map;

/**
 * Represents a zone in the Static Decay game.
 */
public class GameZone {

    private final String name;
    private final int width;
    private final int height;
    private final char[][] layout;
    private final List<Creature> creatures;
    private final Map<Pair<Integer, Integer>, String> interactables;
    private final boolean[][] visited;

    /**
     * Constructs a new GameZone object.
     *
     * @param name          The name of the zone.
     * @param width         The width of the zone.
     * @param height        The height of the zone.
     * @param layout        The layout of the zone.
     * @param creatures     The creatures in the zone.
     * @param interactables The interactable objects in the zone.
     */
    public GameZone(String name, int width, int height, char[][] layout, List<Creature> creatures,
                    Map<Pair<Integer, Integer>, String> interactables) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.layout = layout;
        this.creatures = creatures;
        this.interactables = interactables;
        this.visited = new boolean[height][width];
    }

    /**
     * Gets the name of the zone.
     *
     * @return The name of the zone.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the width of the zone.
     *
     * @return The width of the zone.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the zone.
     *
     * @return The height of the zone.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the creatures in the zone.
     *
     * @return The creatures in the zone.
     */
    public List<Creature> getCreatures() {
        return creatures;
    }

    /**
     * Gets the interactable objects in the zone.
     *
     * @return The interactable objects in the zone.
     */
    public Map<Pair<Integer, Integer>, String> getInteractables() {
        return interactables;
    }

    /**
     * Gets the tile at the specified coordinates.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return The tile character, or '#' if the coordinates are out of bounds.
     */
    public char getTile(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return layout[y][x];
        }
        return '#';
    }

    /**
     * Checks if the specified coordinates have been visited.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return True if the coordinates have been visited, false otherwise.
     */
    public boolean isVisited(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return visited[y][x];
        }
        return false;
    }

    /**
     * Marks the specified coordinates as visited.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    public void markVisited(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            visited[y][x] = true;
        }
    }

    /**
     * Removes an interactable object from the specified coordinates.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    public void removeInteractable(int x, int y) {
        interactables.remove(new Pair<>(x, y));
        if (layout[y][x] != '.') {
            layout[y][x] = '.';
        }
    }
}
