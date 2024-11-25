package interface_adapter.recipe_review;

import entities.Review;
import use_case.recipe_review.RecipeReviewOutputBoundary;

import java.util.List;

/**
 * Presenter for handling recipe review output.
 */
public class RecipeReviewPresenter implements RecipeReviewOutputBoundary {

    private final RecipeReviewViewModel recipeReviewViewModel;

    public RecipeReviewPresenter(RecipeReviewViewModel viewModel) {
        this.recipeReviewViewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(List<Review> review) {
        final RecipeReviewState recipeReviewState = recipeReviewViewModel.getState();
        recipeReviewState.setSuccessMessage("Review added successfully!");
        recipeReviewState.setReviews(review);
    }

    @Override
    public void prepareFailureView(String errorMessage) {
        recipeReviewViewModel.getState().setErrorMessage(errorMessage);
    }
}
