package use_case.recipe_add;

import entities.Ingredient;
import java.util.List;

/**
 * This class represents the data returned after successfully adding a recipe.
 */
public class RecipeAddOutputData {
    private final int id;

    private final String recipeName;
    private final String description;
    private final List<Ingredient> ingredients;

    public RecipeAddOutputData(int recipeID, String recipeName, List<Ingredient> ingredients, String description) {
        this.recipeName = recipeName;
        this.description = description;
        this.ingredients = ingredients;
        this.id = recipeID;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getDescription() {
        return description;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public int getId() {
        return id;
    }
}
