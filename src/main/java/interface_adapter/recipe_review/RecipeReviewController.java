package interface_adapter.recipe_review;

import entities.CommonUser;
import entities.Recipes;
import entities.Review;
import use_case.recipe_review.RecipeReviewInputBoundary;

import java.util.List;

public class RecipeReviewController {
    private final RecipeReviewInputBoundary recipeReviewInputBoundary;
    private final RecipeReviewViewModel recipeReviewViewModel;

    public RecipeReviewController(RecipeReviewInputBoundary recipeReviewInputBoundary,
                                  RecipeReviewViewModel recipeReviewViewModel) {
        this.recipeReviewInputBoundary = recipeReviewInputBoundary;
        this.recipeReviewViewModel = recipeReviewViewModel;
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
        loadReviewsForRecipe(recipe);
    }

    public void loadReviewsForRecipe(Recipes recipe) {
        System.out.println("Controller: Loading reviews for recipe: " + recipe.getName());
        List<Review> reviews = recipeReviewInputBoundary.getReviewsForRecipe(recipe);
        recipeReviewViewModel.updateState(reviews);
    }

}
