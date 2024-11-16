package interface_adapter.recipe_search;

import entities.Recipes;
import use_case.recipe_search.RecipeOutputBoundary;

import java.util.List;

public class RecipePresenter implements RecipeOutputBoundary {

    @Override
    public void prepareSuccessView(List<Recipes> recipeContents) {
        System.out.println("Recipe found: " + recipeContents);
    }

    @Override
    public void prepareFailView(String errorMessage) {
        System.out.println("Failed to find recipe: " + errorMessage);
    }
}
