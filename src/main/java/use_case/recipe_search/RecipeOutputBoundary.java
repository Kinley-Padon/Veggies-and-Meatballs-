package use_case.recipe_search;

import entities.Recipes;

import java.util.List;

/**
 * The output boundary for the Recipe search Use Case.
 */

public interface RecipeOutputBoundary {

    /**
     * Prepares the success view for the Search related Use Cases.
     * @param recipeContents the output data
     */
    void prepareSuccessView(List<Recipes> recipeContents);

    /**
     * Prepares the failure view for the Search related Use Cases.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

}