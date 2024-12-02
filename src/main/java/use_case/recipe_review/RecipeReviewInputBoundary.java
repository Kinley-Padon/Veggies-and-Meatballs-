package use_case.recipe_review;

import entities.Recipes;
import entities.CommonUser;
import entities.Review;

import java.util.List;

/**
 * The Input Boundary for recipe review use cases.
 */
public interface RecipeReviewInputBoundary {

    /**
     * Executes the add recipe review use case.
     *
     * @param user    The user submitting the review.
     * @param recipe  The recipe to review.
     * @param content The content of the review.
     */
    void addReview(CommonUser user, Recipes recipe, String content);

    /**
     * Fetches all reviews for a given recipe.
     *
     * @param recipe The recipe for which reviews are to be fetched.
     */
    List<Review> getReviewsForRecipe(Recipes recipe);

}
