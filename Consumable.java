package in.stl.staticdecay;

import java.util.function.Function;

/**
 * Represents a consumable item in the Static Decay game.
 */
public class Consumable extends Item {

    private final Function<Player, String> effect;

    /**
     * Constructs a new Consumable object.
     *
     * @param name        The name of the consumable.
     * @param description The description of the consumable.
     * @param effect      The effect to apply when the consumable is used.
     */
    public Consumable(String name, String description, Function<Player, String> effect) {
        super(name, description);
        this.effect = effect;
    }

    /**
     * Applies the consumable's effect to the player.
     *
     * @param player The player to apply the effect to.
     * @return A string describing the effect.
     */
    public String applyEffect(Player player) {
        return effect.apply(player);
    }
}
