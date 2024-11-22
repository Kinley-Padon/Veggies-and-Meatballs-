package interface_adapter.recipe_favorites;

import entities.Recipes;
import use_case.recipe_favorites.FavoriteRecipeInputBoundary;

public class FavoriteRecipeController {
    private final FavoriteRecipeInputBoundary inputBoundary;

    public FavoriteRecipeController(FavoriteRecipeInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void addRecipeToFavorites(Recipes recipe) {
        inputBoundary.addRecipeToFavorites(recipe);
    }

    public void removeRecipeFromFavorites(Recipes recipe) {
        inputBoundary.removeRecipeFromFavorites(recipe);
    }

    public void showFavoriteRecipes() {
        inputBoundary.getFavoriteRecipes();
    }
}