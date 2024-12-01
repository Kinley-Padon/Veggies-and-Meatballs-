package java.usecase.recipe_review;

import entities.CommonUser;
import entities.Recipes;
import entities.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import use_case.recipe_review.RecipeReviewDataAccessInterface;
import use_case.recipe_review.RecipeReviewInteractor;
import use_case.recipe_review.RecipeReviewOutputBoundary;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

class RecipeReviewInteractorTest {

    @Mock
    private RecipeReviewDataAccessInterface reviewDao; // Mock of the data access interface

    @Mock
    private RecipeReviewOutputBoundary outputBoundary; // Mock of the output boundary interface

    private RecipeReviewInteractor reviewInteractor; // The interactor we are testing
    private CommonUser user; // The user submitting reviews
    private Recipes recipe; // The recipe being reviewed

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks before each test
        reviewInteractor = new RecipeReviewInteractor(reviewDao, outputBoundary); // Instantiate the interactor with mocks

        // Setup common test data (user and recipe)
        user = new CommonUser("user1", "password123");
        recipe = new Recipes(123, "Delicious pasta recipe", "300");
    }

    // Test for adding a review successfully
    @Test
    void testAddReview_Success() {
        // Arrange
        String reviewContent = "Great recipe!";
        Review review = new Review(user, reviewContent, recipe.getName());

        // Act
        reviewInteractor.addReview(user, recipe, reviewContent);

        // Assert that saveReview is called once with any review object
        verify(reviewDao, times(1)).saveReview(any(Review.class));

        // Assert that prepareSuccessView is called once with a list of reviews
        verify(outputBoundary, times(1)).prepareSuccessView(anyList());

        // Ensure that prepareFailureView was not called
        verify(outputBoundary, times(0)).prepareFailureView(anyString());
    }

    // Test for invalid input when adding a review (empty content)
    @Test
    void testAddReview_Failure_InvalidInput() {
        // Arrange: Invalid review content (empty string)
        String reviewContent = "";

        // Act
        reviewInteractor.addReview(user, recipe, reviewContent);

        // Assert: Ensure that prepareFailureView was called with the appropriate error message
        verify(outputBoundary, times(1)).prepareFailureView("Invalid input: User, recipe, and content must not be null or empty.");

        // Ensure that prepareSuccessView was not called
        verify(outputBoundary, times(0)).prepareSuccessView(anyList());
    }

    // Test for successfully retrieving reviews for a recipe
    @Test
    void testGetReviewsForRecipe_Success() {
        // Arrange: Setup mock data for reviews
        Review review = new Review(user, "Awesome!", recipe.getName());
        List<Review> reviews = List.of(review);
        when(reviewDao.getReviewsByRecipe(recipe)).thenReturn(reviews); // Mock the DAO to return this list of reviews

        // Act
        reviewInteractor.getReviewsForRecipe(recipe);

        // Assert: Ensure prepareSuccessView is called with the list of reviews
        verify(outputBoundary, times(1)).prepareSuccessView(reviews);

        // Ensure that prepareFailureView was not called
        verify(outputBoundary, times(0)).prepareFailureView(anyString());
    }

    // Test when no reviews are available for a recipe
    @Test
    void testGetReviewsForRecipe_Failure_NoReviews() {
        // Arrange: Setup mock DAO to return an empty list of reviews
        when(reviewDao.getReviewsByRecipe(recipe)).thenReturn(Collections.emptyList());

        // Act
        reviewInteractor.getReviewsForRecipe(recipe);

        // Assert: Ensure that prepareFailureView is called with the correct message
        verify(outputBoundary, times(1)).prepareFailureView("No reviews available for this recipe.");

        // Ensure that prepareSuccessView was not called
        verify(outputBoundary, times(0)).prepareSuccessView(anyList());
    }

    // Test when an invalid (null) recipe is provided for retrieving reviews
    @Test
    void testGetReviewsForRecipe_Failure_InvalidRecipe() {
        // Act: Passing a null recipe
        reviewInteractor.getReviewsForRecipe(null);

        // Assert: Ensure prepareFailureView is called with the correct error message
        verify(outputBoundary, times(1)).prepareFailureView("Recipe cannot be null.");

        // Ensure that prepareSuccessView was not called
        verify(outputBoundary, times(0)).prepareSuccessView(anyList());
    }
}
