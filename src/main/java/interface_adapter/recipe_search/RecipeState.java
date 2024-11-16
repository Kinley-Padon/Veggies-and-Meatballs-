package interface_adapter.recipe_search;

import entities.Recipes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeState {
    private List<Recipes> recipeContents;
    private String errorMessage;

    public HashMap<String, Integer> getRecipeDetails() {
        HashMap<String, Integer> recipeDetails = null;
        if (recipeContents != null) {
            recipeDetails = new HashMap<>();
            for (Recipes recipe : recipeContents) {
                recipeDetails.put(recipe.getName(), recipe.getID()); // Add recipe name and ID to the map
            }
        }
        return recipeDetails;
    }

    public void setRecipeDetails(List<Recipes> recipeDetails) {
        HashMap<String, Integer> recipeDetail = null;
        if (recipeContents != null) {
            recipeDetail = new HashMap<>();
            for (Recipes recipe : recipeDetails) {
                recipeDetail.put(recipe.getName(), recipe.getID()); // Add recipe name and ID to the map
            }
        }
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}