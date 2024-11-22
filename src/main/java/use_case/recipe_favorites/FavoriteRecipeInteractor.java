package use_case.recipe_favorites;

import entities.Recipes;
import java.util.Set;

public class FavoriteRecipeInteractor implements FavoriteRecipeInputBoundary {
    private final FavoriteRecipeDataAccessInterface dataAccess;
    private final FavoriteRecipeOutputBoundary outputBoundary;

    public FavoriteRecipeInteractor(FavoriteRecipeDataAccessInterface dataAccess,
                                    FavoriteRecipeOutputBoundary outputBoundary) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void addRecipeToFavorites(Recipes recipe) {
        try {
            dataAccess.addFavoriteRecipe(recipe);
            outputBoundary.presentFavoriteRecipes(dataAccess.getFavoriteRecipes());
        } catch (Exception e) {
            outputBoundary.presentFavoriteError("Failed to add recipe to favorites: " + e.getMessage());
        }
    }

    @Override
    public void removeRecipeFromFavorites(Recipes recipe) {
        try {
            dataAccess.removeFavoriteRecipe(recipe);
            outputBoundary.presentFavoriteRecipes(dataAccess.getFavoriteRecipes());
        } catch (Exception e) {
            outputBoundary.presentFavoriteError("Failed to remove recipe from favorites: " + e.getMessage());
        }
    }

    @Override
    public Set<Recipes> getFavoriteRecipes() {
        return dataAccess.getFavoriteRecipes();
    }
}
