package use_case.recipe_favorites;
import entities.Recipes;
import java.util.Set;

/**
 * Interface for managing favorite recipes data.
 */
public interface FavoriteRecipeDataAccessInterface {

    /**
     * Adds a recipe to the favorites list.
     *
     * @param recipe the recipe to be added.
     */
    void addFavoriteRecipe(Recipes recipe);

    /**
     * Removes a recipe from the favorites list.
     *
     * @param recipe the recipe to be removed.
     */
    void removeFavoriteRecipe(Recipes recipe);

    /**
     * Retrieves the list of favorite recipes.
     *
     * @return a set of favorite recipes.
     */
    Set<Recipes> getFavoriteRecipes();
}