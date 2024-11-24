package use_case.recipe_review;

import entities.CommonUser;
import entities.Review;
import entities.Recipes;

import java.util.stream.Collectors;

public class RecipeReviewInteractor implements RecipeReviewInputBoundary {
    private final Recipes recipe;
    private final RecipeReviewOutputBoundary outputBoundary;

    public RecipeReviewInteractor(Recipes recipe, RecipeReviewOutputBoundary outputBoundary) {
        this.recipe = recipe;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void addReview(CommonUser user, String content){
        Review review = new Review(user, content);
        recipe.addReview(review);
        outputBoundary.onReviewAdded("Review added successfully!");
    }

    @Override
    public void fetchReviews(){
        var reviewList = recipe.getReviews().stream()
                .map(Review::getContent)
                .collect(Collectors.toList());
        outputBoundary.presentReviews(reviewList);
    }

}
