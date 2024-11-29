package interface_adapter.recipe_add;

import entities.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the state for adding a recipe.
 */
public class RecipeAddState {
    private String recipeName;
    private String description;
    private List<Ingredient> ingredients;
    private String imageUrl;
    private String errorMessage;
    private int recipeID;

    public RecipeAddState() {
        this.recipeName = "";
        this.description = "";
        this.ingredients = new ArrayList<>();
        this.imageUrl = "";
        this.errorMessage = "";
        this.recipeID = 0;

    }

    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}