package use_case.recipe_review;

import entities.CommonUser;
/**
 * The Input Boundary for recipe review use cases.
 */

public interface RecipeReviewInputBoundary {

    /**
     * Executes the add recipe review usecase.
     */

    void addReview(CommonUser user, String content);

    void fetchReviews();

}
