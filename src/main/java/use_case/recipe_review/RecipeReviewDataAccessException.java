package use_case.recipe_review;

/**
 * Exception thrown when there is an error with accessing data.
 */
public class RecipeReviewDataAccessException extends Exception {
    public RecipeReviewDataAccessException(String message) {
        super(message);
    }
}
