package interface_adapter.recipe_search;

import entities.Recipes;

import java.util.List;

public class RecipeState {
    private List<Recipes> listOfRecipes;
    private String errorMessage;

    public List<Recipes> getRecipeDetails() {
        return listOfRecipes;
    }

    public void setRecipeDetails(final List<Recipes> listOfRecipes) {
        this.listOfRecipes = listOfRecipes;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}