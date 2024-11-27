package use_case.recipe_favorites;

import entities.Recipes;
import java.util.Set;

public interface FavoriteRecipeOutputBoundary {
    void presentFavoriteRecipes(Set<Recipes> recipes);   // Display list of favorite recipes
    void presentFavoriteError(String errorMessage);      // Show error message
    void showFavoriteAddedMessage();                      // Notify that a recipe was added to favorites
}
