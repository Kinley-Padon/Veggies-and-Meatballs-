package use_case.recipe_favorites;

import entities.Recipes;
import use_case.recipe_favorites.FavoriteRecipeInputBoundary;
import use_case.recipe_favorites.FavoriteRecipeOutputBoundary;
import data_access.FavoriteRecipeDataAccessObject;

public class FavoriteRecipeInteractor implements FavoriteRecipeInputBoundary {
    private final FavoriteRecipeDataAccessObject favoriteRecipeDAO;
    private final FavoriteRecipeOutputBoundary outputBoundary;

    public FavoriteRecipeInteractor(FavoriteRecipeDataAccessObject favoriteRecipeDAO, FavoriteRecipeOutputBoundary outputBoundary) {
        this.favoriteRecipeDAO = favoriteRecipeDAO;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void addToFavorites(Recipes recipe) {
        favoriteRecipeDAO.addToFavorites(recipe);  // Add recipe to the favorites in DAO
        outputBoundary.showFavoriteAddedMessage();  // Call the method in the presenter to notify success
    }

    @Override
    public void viewFavorites() {
        outputBoundary.presentFavoriteRecipes(favoriteRecipeDAO.getFavoriteRecipes());  // Show favorite recipes
    }
}
