package interface_adapter.recipe_review;

import entities.Review;
import interface_adapter.ViewModel;

import java.util.List;

/**
 * The ViewModel for the Recipe Review View.
 */
public class RecipeReviewViewModel extends ViewModel<RecipeReviewState> {

    /**
     * Constructs a RecipeReviewViewModel with an initial state.
     */
    public RecipeReviewViewModel() {
        super("recipeReview");
        setState(new RecipeReviewState());
    }

    /**
     * Updates the state with the given list of reviews.
     *
     * @param reviews The list of reviews to update the state with.
     */
    public void updateState(List<Review> reviews) {
        RecipeReviewState state = getState();
        state.setReviews(reviews);
        System.out.println("ViewModel: Updating state with reviews: " + reviews); // Debug
        setState(state);
    }

}
