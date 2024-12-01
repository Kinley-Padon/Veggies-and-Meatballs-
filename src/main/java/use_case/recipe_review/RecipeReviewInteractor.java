package use_case.recipe_review;

import data_access.DBUserDataAccessObject;
import data_access.FileReviewDataAccessObject;
import entities.CommonUser;
import entities.Recipes;
import entities.Review;
import entities.User;

import java.util.List;

/**
 * Interactor for the Recipe Review use case.
 */
public class RecipeReviewInteractor implements RecipeReviewInputBoundary {

    private final RecipeReviewDataAccessInterface reviewDao; // Data access for reviews
    private final RecipeReviewOutputBoundary outputBoundary; // Output boundary for success/failure views

    public RecipeReviewInteractor(RecipeReviewDataAccessInterface reviewDao,
                                  RecipeReviewOutputBoundary outputBoundary) {
        this.reviewDao = reviewDao;
        this.outputBoundary = outputBoundary;
    }

    /**
     * Adds a review for the given recipe.
     *
     * @param user    The user submitting the review.
     * @param recipe  The recipe to review.
     * @param content The content of the review.
     */
    @Override
    public void addReview(CommonUser user, Recipes recipe, String content) {
        try {
            if (user == null || recipe == null || content == null || content.isBlank()) {
                throw new IllegalArgumentException("Invalid input: User, recipe, and content must not be null or " +
                        "empty.");
            }

            Review review = new Review(user, content, recipe.getName());
            reviewDao.saveReview(review);

            List<Review> reviews = reviewDao.getReviewsByRecipe(recipe);

            outputBoundary.prepareSuccessView(reviews);
        } catch (Exception e) {
            outputBoundary.prepareFailureView(e.getMessage());
        }
    }

    /**
     * Retrieves all reviews for a specific recipe.
     *
     * @param recipe The recipe for which reviews are to be fetched.
     */
    @Override
    public List<Review> getReviewsForRecipe(Recipes recipe) {
        try {
            if (recipe == null) {
                throw new IllegalArgumentException("Recipe cannot be null.");
            }

            List<Review> reviews = reviewDao.getReviewsByRecipe(recipe);

            if (reviews.isEmpty()) {
                outputBoundary.prepareFailureView("No reviews available for this recipe.");
            } else {
                outputBoundary.prepareSuccessView(reviews);
            }

            return reviews;

        } catch (Exception e) {
            outputBoundary.prepareFailureView(e.getMessage());
        }
        return List.of();
    }

}
