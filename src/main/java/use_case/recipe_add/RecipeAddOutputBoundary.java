package use_case.recipe_add;

import entities.Recipes;

public interface RecipeAddOutputBoundary {
    void displayAddedRecipe(Recipes recipe);

    void displayError(String errorMessage);
}

