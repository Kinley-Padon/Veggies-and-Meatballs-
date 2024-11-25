package interface_adapter.recipe_review;

import entities.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * The State for managing recipe reviews.
 */
public class RecipeReviewState {
    private List<Review> reviews = new ArrayList<>();
    private String successMessage;
    private String errorMessage;

    /**
     * Gets the list of reviews.
     *
     * @return The list of reviews.
     */
    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * Sets the list of reviews.
     *
     * @param review The reviews to set.
     */
    public void setReviews(List<Review> review) {
        this.reviews = review;
    }

    /**
     * Gets the success message.
     *
     * @return The success message.
     */
    public String getSuccessMessage() {
        return successMessage;
    }

    /**
     * Sets the success message.
     *
     * @param successMessage The success message to set.
     */
    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    /**
     * Gets the error message.
     *
     * @return The error message.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the error message.
     *
     * @param errorMessage The error message to set.
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
