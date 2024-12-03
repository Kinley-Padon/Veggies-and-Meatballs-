package java.usecase.recipe_add;
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
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Flour", 2.0, "cups"));
        ingredients.add(new Ingredient("Sugar", 1.0, "cups"));
        String recipeName = "Test Recipe";
        String description = "Mix ingredients and bake.";
        int generatedId = 12345;

        when(mockDataAccess.generateRecipeId()).thenReturn(generatedId);
        recipeAddInteractor.addRecipe(recipeName, ingredients, description);
        verify(mockDataAccess, times(1)).saveRecipe(any(Recipes.class));
        ArgumentCaptor<RecipeAddOutputData> captor = ArgumentCaptor.forClass(RecipeAddOutputData.class);
        verify(mockOutputBoundary, times(1)).displayAddedRecipe(captor.capture());

        RecipeAddOutputData capturedOutputData = captor.getValue();
        assertEquals(generatedId, capturedOutputData.getId());
        assertEquals(recipeName, capturedOutputData.getRecipeName());
        assertEquals(ingredients, capturedOutputData.getIngredients());
        assertEquals(description, capturedOutputData.getDescription());
    }



    @Test
    void testAddRecipeFailure() {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Flour", 2.0, "cups"));
        ingredients.add(new Ingredient("Sugar", 1.0, "cups"));
        String recipeName = "Test Recipe";
        String description = "Mix ingredients and bake.";

        doThrow(new RuntimeException("Database error")).when(mockDataAccess).saveRecipe(any(Recipes.class));

        recipeAddInteractor.addRecipe(recipeName, ingredients, description);
        verify(mockOutputBoundary, times(1)).displayError("Failed to add recipe: Database error");
    }


    @Test
    void testGenerateRecipeId() {
        int expectedId = 12345;
        when(mockDataAccess.generateRecipeId()).thenReturn(expectedId);

        int actualId = recipeAddInteractor.generateRecipeId();

        assertEquals(expectedId, actualId);
    }

    @Test
    void testAddRecipeWithEmptyName() {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Flour", 2.0, "cups"));
        ingredients.add(new Ingredient("Sugar", 1.0, "cups"));
        String recipeName = ""; // Empty name
        String description = "Mix ingredients and bake.";
        int generatedId = 12345;
        when(mockDataAccess.generateRecipeId()).thenReturn(generatedId);
        recipeAddInteractor.addRecipe(recipeName, ingredients, description);

        verify(mockDataAccess, times(0)).saveRecipe(any(Recipes.class));

        verify(mockOutputBoundary, times(1)).displayError("Recipe name cannot be empty.");
    }


    @Test
    void testAddRecipeWithEmptyDescription() {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Flour", 2.0, "cups"));
        ingredients.add(new Ingredient("Sugar", 1.0, "cups"));
        String recipeName = "Test Recipe";
        String description = "";
        int generatedId = 12345;
        when(mockDataAccess.generateRecipeId()).thenReturn(generatedId);
        recipeAddInteractor.addRecipe(recipeName, ingredients, description);

        verify(mockDataAccess, times(0)).saveRecipe(any(Recipes.class));

        verify(mockOutputBoundary, times(1)).displayError("Instructions cannot be empty.");
    }

    @Test
    void testAddRecipeWithInvalidIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Flour", -2.0, "cups"));
        String recipeName = "Test Recipe";
        String description = "Mix ingredients and bake.";
        int generatedId = 12345;
        when(mockDataAccess.generateRecipeId()).thenReturn(generatedId);
        recipeAddInteractor.addRecipe(recipeName, ingredients, description);

        verify(mockDataAccess, times(0)).saveRecipe(any(Recipes.class));
        verify(mockOutputBoundary, times(1)).displayError("Ingredients cannot be empty.");
    }

}
