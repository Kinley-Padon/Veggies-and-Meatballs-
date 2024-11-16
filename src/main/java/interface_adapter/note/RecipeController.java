package interface_adapter.note;

import use_case.RecipeInputBoundary;

public class RecipeController {
    private final RecipeInputBoundary recipeInputBoundary;

    public RecipeController(RecipeInputBoundary recipeInputBoundary) {
        this.recipeInputBoundary = recipeInputBoundary;
    }

    public void searchRecipe(String userInput) {
        recipeInputBoundary.executeSearchRecipe();
    }
}
