package use_case.recipe_review;

import entities.Review;

/**
 * The output boundary for the Recipe search Use Case.
 */

public interface RecipeReviewOutputBoundary {

    void prepareSuccessView();

    void prepareFailureView();


}
