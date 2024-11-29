package interface_adapter.recipe_review;

import interface_adapter.ViewModel;

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
}
