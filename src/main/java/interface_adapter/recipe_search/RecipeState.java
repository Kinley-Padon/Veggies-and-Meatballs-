package interface_adapter.recipe_search;

import entities.Recipes;

import java.util.List;

public class RecipeState {
    private List<Recipes> recipeContents;
    private String errorMessage;

    public String getRecipeDetails() {
        return recipeContents;
    }

    public void setRecipeDetails(String recipeDetails) {
        this.recipeContents; = recipeDetails;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}