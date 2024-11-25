package use_case.recipe_review;

import entities.Recipes;
import entities.CommonUser;

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
    void getReviewsForRecipe(Recipes recipe);

}
