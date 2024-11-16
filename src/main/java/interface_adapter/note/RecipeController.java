package interface_adapter.Recipe;

import use_case.RecipeInputBoundary;

public class RecipeController {
    private final RecipeInputBoundary recipeInputBoundary;

    public RecipeController(RecipeInputBoundary recipeInputBoundary) {
        this.recipeInputBoundary = recipeInputBoundary;
    }

    public void searchRecipe(List<Recipes> recipeContent) {
        recipeInputBoundary.executeSearchRecipe(recipeContent);
    }
}
