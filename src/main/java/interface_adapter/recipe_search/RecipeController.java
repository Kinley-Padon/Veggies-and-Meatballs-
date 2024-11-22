package interface_adapter.recipe_search;

import use_case.recipe_search.RecipeInputBoundary;

public class RecipeController {
    private final RecipeInputBoundary recipeInputBoundary;

    public RecipeController(RecipeInputBoundary recipeInputBoundary) {
        this.recipeInputBoundary = recipeInputBoundary;
    }

    public void searchRecipe(String recipeName) {
        recipeInputBoundary.executeSearchRecipe(recipeName);
    }

    public void searchRecipe(String recipeName, String allergen) {
        recipeInputBoundary.executeSearchRecipe(recipeName, allergen);
    }
}
