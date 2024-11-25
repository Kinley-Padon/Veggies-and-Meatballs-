package use_case.recipe_review;

import entities.Review;
import entities.Recipes;
import java.util.List;

public interface RecipeReviewDataAccessInterface {

    /**
     * Saves a review to the storage.
     *
     * @param review The review to save.
     */
    void saveReview(Review review);

    /**
     * Retrieves all reviews for a specific recipe.
     *
     * @param recipe The recipe whose reviews need to be retrieved.
     * @return A list of reviews for the given recipe.
     */
    List<Review> getReviewsByRecipe(Recipes recipe);
}

