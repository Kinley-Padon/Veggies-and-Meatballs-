package interface_adapter.recipe_search;

import use_case.recipe_search.RecipeInputBoundary;
import entities.Recipes;
import java.util.List;

public class RecipeController {
    private final RecipeInputBoundary recipeInputBoundary;

    public RecipeController(RecipeInputBoundary recipeInputBoundary) {
        this.recipeInputBoundary = recipeInputBoundary;
    }

    public void searchRecipe(List<Recipes> recipeContent) {
        recipeInputBoundary.executeSearchRecipe(recipeContent);
    }
}
