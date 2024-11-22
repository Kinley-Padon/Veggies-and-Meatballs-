package interface_adapter.recipe_favorites;

import entities.Recipes;
import use_case.recipe_favorites.FavoriteRecipeOutputBoundary;

import java.util.Set;

public class FavoriteRecipePresenter implements FavoriteRecipeOutputBoundary {
    private final FavoriteRecipeViewModel viewModel;

    public FavoriteRecipePresenter(FavoriteRecipeViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentFavoriteRecipes(Set<Recipes> recipes) {
        viewModel.setFavoriteRecipes(recipes);
        viewModel.firePropertyChanged();
    }

    @Override
    public void presentFavoriteError(String errorMessage) {
        viewModel.setErrorMessage(errorMessage);
        viewModel.firePropertyChanged();
    }
}