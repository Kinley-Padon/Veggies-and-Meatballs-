package use_case.recipe_search;
import java.util.List;
import entities.Recipes;

/**
 * The "Use Case Interactor" for our use cases.
 */
public class RecipeInteractor implements RecipeInputBoundary {

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
    public void executeSearchRecipe(List<Recipes> recipes) {
        try {
            final List<Recipes> recipeContent = recipeDataAccessInterface.searchRecipe(userInput);
            // If successful, send the recipe content to the output boundary's success view
            recipeOutputBoundary.prepareSuccessView(recipeContent);
        } catch (RecipeDataAccessException ex) {
            RecipeOutputBoundary.prepareFailView(ex.getMessage());
        }
    }
}

