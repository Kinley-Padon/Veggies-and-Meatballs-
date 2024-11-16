package use_case.recipe_search;
/**
 * The output boundary for the Recipe search Use Case.
 */

public interface RecipeOutputBoundary {

    /**
     * Prepares the success view for the Search related Use Cases.
     * @param message the output data
     */
    void prepareSuccessView(String message);

    /**
     * Prepares the failure view for the Search related Use Cases.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

}