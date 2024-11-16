package use_case;

import main.java.entities.User;

/**
 * Interface for the Recipe Explorer. Consists of methods for
 * loading recipes and saving recipes.
 */
public interface RecipeDataAccessInterface {
    /**
     * Access recipe from the API with the given keyword.
     * @param user the user information
     * @param userInput inputs from the user to access recipe information
     * @return contents/id of the recipe
     * @throws RecipeDataAccessException if the recipe cannot be accessed
     */

    String SearchRecipe(User user, String userInput) throws RecipeDataAccessException;

}