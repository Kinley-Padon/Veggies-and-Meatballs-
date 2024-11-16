package interface_adapter.recipe_search;

import entities.Recipes;
import use_case.recipe_search.RecipeOutputBoundary;

import java.util.List;

public class RecipePresenter implements RecipeOutputBoundary {

    private final RecipeViewModel recipeViewModel;

    public RecipePresenter(RecipeViewModel recipeViewModel) {
        this.recipeViewModel = recipeViewModel;
    }
    @Override
    public void prepareSuccessView(List<Recipes> recipeContents) {
        recipeViewModel.getState().setRecipeDetails(recipeContents);
        recipeViewModel.getState().setErrorMessage(null);
        recipeViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        recipeViewModel.getState().setErrorMessage(errorMessage);
        recipeViewModel.firePropertyChanged();
    }
}
