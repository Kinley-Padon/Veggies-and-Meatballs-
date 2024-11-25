package use_case.recipe_favorites;

import entities.Recipes;

public interface FavoriteRecipeInputBoundary {
    void addToFavorites(Recipes recipe);   // Method to add a recipe to favorites
    void viewFavorites();                  // Method to view all favorite recipes
}
