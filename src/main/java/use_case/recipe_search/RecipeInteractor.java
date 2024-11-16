package use_case.recipe_search;

/**
 * The "Use Case Interactor" for our use cases.
 */
public abstract class RecipeInteractor implements use_case.RecipeInputBoundary {

    private final RecipeDataAccessInterface recipeDataAccessInterface;
    private final RecipeOutputBoundary recipeOutputBoundary;

    public RecipeInteractor(RecipeDataAccessInterface recipeDataAccessInterface,
                            RecipeOutputBoundary recipeOutputBoundary) {
        this.recipeDataAccessInterface = recipeDataAccessInterface;
        this.recipeOutputBoundary = recipeOutputBoundary;
    }

    /**
     * Executes the search recipe use case.
     */
    @Override
    public void executeSearchRecipe(String userInput) {
        try {

            final String recipeContent = recipeDataAccessInterface.searchRecipe(userInput);
            // If successful, send the recipe content to the output boundary's success view
            recipeOutputBoundary.prepareSuccessView(recipeContent);
        } catch (RecipeDataAccessException ex) {
            RecipeOutputBoundary.prepareFailView(ex.getMessage());
        }
    }
}

