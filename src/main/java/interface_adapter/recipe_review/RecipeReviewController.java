package interface_adapter.recipe_review;

import entities.CommonUser;
import entities.Recipes;
import use_case.recipe_review.RecipeReviewInputBoundary;

public class RecipeReviewController {
    private final RecipeReviewInputBoundary recipeReviewInputBoundary;

    public RecipeReviewController(RecipeReviewInputBoundary recipeReviewInputBoundary) {
        this.recipeReviewInputBoundary = recipeReviewInputBoundary;
    }

    /**
     * Handles the request to add a review.
     *
     * @param user     The user submitting the review.
     * @param recipe   The recipe for which the review is to be added.
     * @param content  The content of the review.
     */
    public void addReview(CommonUser user, Recipes recipe, String content) {
        if (user == null || recipe == null || content == null || content.isBlank()) {
            throw new IllegalArgumentException("Invalid input: User, recipe, and content must not be null or empty.");
        }
        recipeReviewInputBoundary.addReview(user, recipe, content);
    }

}
