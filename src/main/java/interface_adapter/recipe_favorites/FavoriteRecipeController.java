package interface_adapter.recipe_favorites;

import use_case.recipe_favorites.FavoriteRecipeInputBoundary;
import entities.Recipes;

public class FavoriteRecipeController {
    private final FavoriteRecipeInputBoundary interactor;

    public FavoriteRecipeController(FavoriteRecipeInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void addRecipeToFavorites(Recipes recipe) {
        interactor.addToFavorites(recipe);  // Calls the method from the input boundary
    }

    public void viewFavoriteRecipes() {
        interactor.viewFavorites();  // Calls the method from the input boundary
    }
}
