package use_case.recipe_review;

import entities.Review;

import java.util.List;

/**
 * The output boundary for the Recipe Review Use Case.
 */
public interface RecipeReviewOutputBoundary {

    /**
     * Prepares the success view when the use case is executed successfully.
     */
    void prepareSuccessView(List<Review> review);

    /**
     * Prepares the failure view when the use case execution fails.
     *
     * @param errorMessage The error message to display.
     */
    void prepareFailureView(String errorMessage);
}
