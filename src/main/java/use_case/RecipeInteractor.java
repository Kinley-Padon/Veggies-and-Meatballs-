package use_case;

import main.java.entities.User;

/**
 * The "Use Case Interactor" for our use cases.
 */
public abstract class RecipeInteractor implements main.java.use_case.RecipeInputBoundary {

    private final RecipeDataAccessInterface recipeDataAccessInterface;
    private final RecipeOutputBoundary recipeOutputBoundary;

    public RecipeInteractor(RecipeDataAccessInterface recipeDataAccessInterface,
                            RecipeOutputBoundary recipeOutputBoundary) {
        this.RecipeDataAccessInterface = recipeDataAccessInterface;
        this.RecipeOutputBoundary = recipeOutputBoundary;
    }

    /**
     * Executes the search recipe use case.
     */
    @Override
    public void executeSearchRecipe(User user, String userInput) {
        try {

            final String recipeContent = recipeDataAccessInterface.SearchRecipe(user, userInput);
            // If successful, send the recipe content to the output boundary's success view
            recipeOutputBoundary.prepareSuccessView(recipeContent);
        } catch (RecipeDataAccessException ex) {
            RecipeOutputBoundary.prepareFailView(ex.getMessage());
        }
    }
}

