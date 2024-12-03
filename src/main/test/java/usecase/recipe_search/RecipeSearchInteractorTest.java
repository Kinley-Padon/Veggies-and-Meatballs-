package use_case.recipe_search;

import entities.Recipes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

class RecipeSearchInteractorTest {

    private RecipeDataAccessInterface mockDataAccess;
    private RecipeOutputBoundary mockOutputBoundary;
    private RecipeInteractor recipeInteractor;

    @BeforeEach
    void setUp() {
        mockDataAccess = mock(RecipeDataAccessInterface.class);
        mockOutputBoundary = mock(RecipeOutputBoundary.class);
        recipeInteractor = new RecipeInteractor(mockDataAccess, mockOutputBoundary);
    }

    @Test
    void testExecuteSearchRecipe_success() throws RecipeDataAccessException {
        // Arrange
        String userInput = "Pasta";
        Recipes recipe1 = new Recipes(1, "Spaghetti Carbonara", "image1.jpg");
        Recipes recipe2 = new Recipes(2, "Fettuccine Alfredo", "image2.jpg");
        List<Recipes> mockRecipes = Arrays.asList(recipe1, recipe2);

        when(mockDataAccess.searchRecipe(userInput)).thenReturn(mockRecipes);
        recipeInteractor.executeSearchRecipe(userInput);
        verify(mockDataAccess).searchRecipe(userInput);
        verify(mockOutputBoundary).prepareSuccessView(mockRecipes);
        verifyNoMoreInteractions(mockOutputBoundary);
    }

    @Test
    void testExecuteSearchRecipe_failure() throws RecipeDataAccessException {
        String userInput = "NonExistent";
        String errorMessage = "No recipes found for input: NonExistent";

        when(mockDataAccess.searchRecipe(userInput)).thenThrow(new RecipeDataAccessException(errorMessage));
        recipeInteractor.executeSearchRecipe(userInput);
        verify(mockDataAccess).searchRecipe(userInput);
        verify(mockOutputBoundary).prepareFailView(errorMessage);
        verifyNoMoreInteractions(mockOutputBoundary);
    }

    @Test
    void testExecuteSearchRecipe_withAllergen_success() throws RecipeDataAccessException {
        String recipeName = "Salad";
        String allergen = "Nuts";
        Recipes recipe1 = new Recipes(3, "Caesar Salad", "image3.jpg");
        List<Recipes> mockRecipes = Collections.singletonList(recipe1);

        when(mockDataAccess.searchRecipe(recipeName, allergen)).thenReturn(mockRecipes);
        recipeInteractor.executeSearchRecipe(recipeName, allergen);
        verify(mockDataAccess).searchRecipe(recipeName, allergen);
        verify(mockOutputBoundary).prepareSuccessView(mockRecipes);
        verifyNoMoreInteractions(mockOutputBoundary);
    }

    @Test
    void testExecuteSearchRecipe_withAllergen_failure() throws RecipeDataAccessException {
        String recipeName = "Pizza";
        String allergen = "Dairy";
        String errorMessage = "No recipes found for input: Pizza, allergen: Dairy";

        when(mockDataAccess.searchRecipe(recipeName, allergen)).thenThrow(new RecipeDataAccessException(errorMessage));
        recipeInteractor.executeSearchRecipe(recipeName, allergen);
        verify(mockDataAccess).searchRecipe(recipeName, allergen);
        verify(mockOutputBoundary).prepareFailView(errorMessage);
        verifyNoMoreInteractions(mockOutputBoundary);
    }
}
