package use_case.recipe_search;

/**
 * The Input Boundary for recipe use cases.
 */
public interface RecipeInputBoundary {

    /**
     * Executes the search recipe usecase.
     */
    void executeSearchRecipe(String userInput);

    /**
     * Executes the search recipe with allergen use case.
     *
     * @param recipeName The name of the recipe
     * @param allergen the name of the allergen to exclude from search
     */
    void executeSearchRecipe(String recipeName, String allergen);
}