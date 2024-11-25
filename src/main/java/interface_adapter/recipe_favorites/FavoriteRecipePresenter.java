package interface_adapter.recipe_favorites;

import use_case.recipe_favorites.FavoriteRecipeOutputBoundary;
import entities.Recipes;
import java.util.Set;

public class FavoriteRecipePresenter implements FavoriteRecipeOutputBoundary {

    // Implement the method to display a message when a recipe is added to favorites
    @Override
    public void showFavoriteAddedMessage() {
        System.out.println("Recipe has been added to your favorites!");  // Example success message
    }

    // Implement the method to present the list of favorite recipes
    @Override
    public void presentFavoriteRecipes(Set<Recipes> recipes) {
        // Display each recipe in the list
        if (recipes.isEmpty()) {
            System.out.println("You have no favorite recipes yet.");
        } else {
            System.out.println("Your favorite recipes are:");
            for (Recipes recipe : recipes) {
                System.out.println(recipe.getName());  // Display the name of each recipe
            }
        }
    }

    // Implement the method to present an error message
    @Override
    public void presentFavoriteError(String errorMessage) {
        // Show the error message
        System.err.println("Error: " + errorMessage);
    }
}
