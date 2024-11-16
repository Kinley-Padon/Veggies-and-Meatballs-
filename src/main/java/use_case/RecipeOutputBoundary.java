package use_case;

import entities.Recipes;

import java.util.List;

/**
 * The output boundary for the Recipe Search Use Case.
 */
public interface RecipeOutputBoundary {
    /**
     * Prepares the success view for the Note related Use Cases.
     * @param recipeContent the output data
     */
    void prepareSuccessView(List<Recipes> recipeContent);
}
