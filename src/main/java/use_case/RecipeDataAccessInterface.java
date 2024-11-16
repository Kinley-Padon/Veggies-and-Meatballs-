package use_case;

import java.util.List;

/**
 * Interface for the Recipe Explorer. Consists of methods for
 * loading recipes and saving recipes.
 */
public interface RecipeDataAccessInterface {
    /**
     * Access recipe from the API with the given keyword.
     * @param userInput inputs from the user to access recipe information
     * @return contents/id of the recipe
     * @throws RecipeDataAccessException if the recipe cannot be accessed
     */

    List searchRecipe(String userInput) throws RecipeDataAccessException;

}