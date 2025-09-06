package in.stl.staticdecay;

import java.util.Map;

/**
 * Represents a crafting recipe in the Static Decay game.
 */
public class CraftingRecipe {

    private final Map<String, Integer> ingredients;
    private final Item result;

    /**
     * Constructs a new CraftingRecipe object.
     *
     * @param ingredients The ingredients required for the recipe.
     * @param result      The item produced by the recipe.
     */
    public CraftingRecipe(Map<String, Integer> ingredients, Item result) {
        this.ingredients = ingredients;
        this.result = result;
    }

    /**
     * Gets the ingredients required for the recipe.
     *
     * @return The ingredients required for the recipe.
     */
    public Map<String, Integer> getIngredients() {
        return ingredients;
    }

    /**
     * Gets the item produced by the recipe.
     *
     * @return The item produced by the recipe.
     */
    public Item getResult() {
        return result;
    }
}
