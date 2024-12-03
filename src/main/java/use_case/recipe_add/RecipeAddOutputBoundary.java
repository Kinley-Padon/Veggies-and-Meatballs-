package use_case.recipe_add;

import entities.Recipes;

public interface RecipeAddOutputBoundary {
    void displayAddedRecipe(RecipeAddOutputData outputData);

    void displayError(String errorMessage);
}

