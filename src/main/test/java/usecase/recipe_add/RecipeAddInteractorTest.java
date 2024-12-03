import entities.Ingredient;
import entities.Recipes;
import use_case.recipe_add.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;


class RecipeAddInteractorTest {

    @Mock
    private RecipeAddOutputBoundary mockOutputBoundary;

    @Mock
    private RecipeAddDataAccessInterface mockDataAccess;

    private RecipeAddInteractor recipeAddInteractor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recipeAddInteractor = new RecipeAddInteractor(mockOutputBoundary, mockDataAccess);
    }


    @Test
    void testAddRecipeSuccess() {
        // Arrange: Prepare mock data
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Flour", 2.0, "cups"));
        ingredients.add(new Ingredient("Sugar", 1.0, "cups"));
        String recipeName = "Test Recipe";
        String description = "Mix ingredients and bake.";
        int generatedId = 12345;

        when(mockDataAccess.generateRecipeId()).thenReturn(generatedId);

        // Act: Call the addRecipe method
        recipeAddInteractor.addRecipe(recipeName, ingredients, description);

        // Assert: Verify that saveRecipe and displayAddedRecipe are called
        verify(mockDataAccess, times(1)).saveRecipe(any(Recipes.class));

        // Use ArgumentCaptor to capture the RecipeAddOutputData passed to displayAddedRecipe
        ArgumentCaptor<RecipeAddOutputData> captor = ArgumentCaptor.forClass(RecipeAddOutputData.class);
        verify(mockOutputBoundary, times(1)).displayAddedRecipe(captor.capture());

        // Get the captured argument
        RecipeAddOutputData capturedOutputData = captor.getValue();

        // Verify that the captured output data matches the expected values
        assertEquals(generatedId, capturedOutputData.getId());
        assertEquals(recipeName, capturedOutputData.getRecipeName());
        assertEquals(ingredients, capturedOutputData.getIngredients());
        assertEquals(description, capturedOutputData.getDescription());
    }



    @Test
    void testAddRecipeFailure() {
        // Arrange: Prepare mock data
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Flour", 2.0, "cups"));
        ingredients.add(new Ingredient("Sugar", 1.0, "cups"));
        String recipeName = "Test Recipe";
        String description = "Mix ingredients and bake.";

        // Mock an exception during saveRecipe
        doThrow(new RuntimeException("Database error")).when(mockDataAccess).saveRecipe(any(Recipes.class));

        // Act: Call the addRecipe method
        recipeAddInteractor.addRecipe(recipeName, ingredients, description);

        // Assert: Ensure the error message is displayed
        verify(mockOutputBoundary, times(1)).displayError("Failed to add recipe: Database error");
    }


    @Test
    void testGenerateRecipeId() {
        // Arrange: Mock the behavior of data access
        int expectedId = 12345;
        when(mockDataAccess.generateRecipeId()).thenReturn(expectedId);

        // Act: Call the generateRecipeId method
        int actualId = recipeAddInteractor.generateRecipeId();

        // Assert: Verify that the generated ID is as expected
        assertEquals(expectedId, actualId);
    }

    @Test
    void testAddRecipeWithEmptyName() {
        // Arrange: Prepare mock data with an empty name
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Flour", 2.0, "cups"));
        ingredients.add(new Ingredient("Sugar", 1.0, "cups"));
        String recipeName = ""; // Empty name
        String description = "Mix ingredients and bake.";

        // Mock generateRecipeId
        int generatedId = 12345;
        when(mockDataAccess.generateRecipeId()).thenReturn(generatedId);

        // Act: Call the addRecipe method with an empty name
        recipeAddInteractor.addRecipe(recipeName, ingredients, description);

        // Assert: Ensure that saveRecipe was not called
        verify(mockDataAccess, times(0)).saveRecipe(any(Recipes.class));
        // Verify the error message
        verify(mockOutputBoundary, times(1)).displayError("Recipe name cannot be empty.");
    }


    @Test
    void testAddRecipeWithEmptyDescription() {
        // Arrange: Prepare mock data with an empty description
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Flour", 2.0, "cups"));
        ingredients.add(new Ingredient("Sugar", 1.0, "cups"));
        String recipeName = "Test Recipe";
        String description = ""; // Empty description

        // Mock generateRecipeId
        int generatedId = 12345;
        when(mockDataAccess.generateRecipeId()).thenReturn(generatedId);

        // Act: Call the addRecipe method with an empty description
        recipeAddInteractor.addRecipe(recipeName, ingredients, description);

        // Assert: Ensure that saveRecipe was not called
        verify(mockDataAccess, times(0)).saveRecipe(any(Recipes.class));
        // Verify the error message
        verify(mockOutputBoundary, times(1)).displayError("Instructions cannot be empty.");
    }

    @Test
    void testAddRecipeWithInvalidIngredients() {
        // Arrange: Prepare mock data with invalid ingredients (e.g., negative quantity)
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Flour", -2.0, "cups")); // Invalid negative quantity
        String recipeName = "Test Recipe";
        String description = "Mix ingredients and bake.";

        // Mock generateRecipeId
        int generatedId = 12345;
        when(mockDataAccess.generateRecipeId()).thenReturn(generatedId);

        // Act: Call the addRecipe method with invalid ingredients
        recipeAddInteractor.addRecipe(recipeName, ingredients, description);

        // Assert: Ensure that saveRecipe was not called
        verify(mockDataAccess, times(0)).saveRecipe(any(Recipes.class));
        // Verify the error message
        verify(mockOutputBoundary, times(1)).displayError("Ingredients cannot be empty.");
    }

}
