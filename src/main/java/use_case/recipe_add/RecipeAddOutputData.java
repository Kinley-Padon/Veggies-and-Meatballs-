package use_case.recipe_add;

import entities.Ingredient;
import java.util.List;

/**
 * This class represents the data returned after successfully adding a recipe.
 */
public class RecipeAddOutputData {

    private final String recipeName;
    private final String description;
    private final List<Ingredient> ingredients;
    private final String imageUrl;

    // Constructor
    public RecipeAddOutputData(String recipeName, String description, List<Ingredient> ingredients, String imageUrl) {
        this.recipeName = recipeName;
        this.description = description;
        this.ingredients = ingredients;
        this.imageUrl = imageUrl;
    }

    // Getters
    public String getRecipeName() {
        return recipeName;
    }

    public String getDescription() {
        return description;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
