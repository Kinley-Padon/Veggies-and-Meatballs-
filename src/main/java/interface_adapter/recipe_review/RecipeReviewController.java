package interface_adapter.recipe_review;

import entities.CommonUser;
import use_case.recipe_review.RecipeReviewInputBoundary;

public class RecipeReviewController {
    private RecipeReviewInputBoundary recipeReviewInputBoundary;

    public RecipeReviewController(RecipeReviewInputBoundary recipeReviewInputBoundary) {
        this.recipeReviewInputBoundary = recipeReviewInputBoundary;
    }

    public void addReview(CommonUser user, String content){
        recipeReviewInputBoundary.addReview(user, content);
    }

    public void fetchReviews(){
        recipeReviewInputBoundary.fetchReviews();
    }
}
