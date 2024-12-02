import entities.Ingredient;
import entities.Recipes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.recipe_search.RecipeDataAccessException;
import use_case.recipe_search.RecipeDataAccessInterface;
import use_case.recipe_search.RecipeInteractor;
import use_case.recipe_search.RecipeOutputBoundary;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeSearchInteractorTest {

    private RecipeDataAccessInterface recipeDataAccessInterface;
    private RecipeOutputBoundary recipeOutputBoundary;
    private RecipeInteractor recipeInteractor;

    @BeforeEach
    public void setUp() {
        recipeDataAccessInterface = new InMemoryRecipeDataAccess();
        recipeOutputBoundary = new InMemoryRecipeOutputBoundary();
        recipeInteractor = new RecipeInteractor(recipeDataAccessInterface, recipeOutputBoundary);
    }

    @Test
    public void testExecuteSearchRecipe_Success() throws RecipeDataAccessException {
        String userInput = "pasta";
        List<Recipes> mockRecipes = new ArrayList<>();
        mockRecipes.add(new Recipes("Pasta Carbonara", 500));
        ((InMemoryRecipeDataAccess) recipeDataAccessInterface).setMockRecipeData(mockRecipes);
        recipeInteractor.executeSearchRecipe(userInput);
        List<Recipes> result = ((InMemoryRecipeOutputBoundary) recipeOutputBoundary).getResult();
        assertEquals(mockRecipes, result);
    }

    @Test
    public void testExecuteSearchRecipe_Failure() throws RecipeDataAccessException {
        String userInput = "nonexistentrecipe";
        String errorMessage = "Recipe not found";
        ((InMemoryRecipeDataAccess) recipeDataAccessInterface).setShouldThrow(true);
        recipeInteractor.executeSearchRecipe(userInput);
        String resultErrorMessage = ((InMemoryRecipeOutputBoundary) recipeOutputBoundary).getErrorMessage();
        assertEquals(errorMessage, resultErrorMessage);
    }

    @Test
    public void testExecuteSearchRecipeWithAllergen_Success() throws RecipeDataAccessException {
        String recipeName = "pasta";
        String allergen = "gluten";
        List<Recipes> mockRecipes = new ArrayList<>();
        mockRecipes.add(new Recipes("Gluten-Free Pasta", 400));
        ((InMemoryRecipeDataAccess) recipeDataAccessInterface).setMockRecipeData(mockRecipes);
        recipeInteractor.executeSearchRecipe(recipeName, allergen);
        List<Recipes> result = ((InMemoryRecipeOutputBoundary) recipeOutputBoundary).getResult();
        assertEquals(mockRecipes, result);
    }

    @Test
    public void testExecuteSearchRecipeWithAllergen_Failure() throws RecipeDataAccessException {
        String recipeName = "pasta";
        String allergen = "gluten";
        String errorMessage = "Recipe with allergen not found";
        ((InMemoryRecipeDataAccess) recipeDataAccessInterface).setShouldThrow(true);
        recipeInteractor.executeSearchRecipe(recipeName, allergen);
        String resultErrorMessage = ((InMemoryRecipeOutputBoundary) recipeOutputBoundary).getErrorMessage();
        assertEquals(errorMessage, resultErrorMessage);
    }

    class InMemoryRecipeDataAccess implements RecipeDataAccessInterface {
        private List<Recipes> mockRecipeData;
        private boolean shouldThrow = false;

        @Override
        public List<Recipes> searchRecipe(String userInput) throws RecipeDataAccessException {
            if (shouldThrow) {
                throw new RecipeDataAccessException("Recipe not found");
            }
            return mockRecipeData;
        }

        @Override
        public List<Recipes> searchRecipe(String recipe, String allergen) throws RecipeDataAccessException {
            if (shouldThrow) {
                throw new RecipeDataAccessException("Recipe with allergen not found");
            }
            return mockRecipeData;
        }

        @Override
        public List<Ingredient> getRecipeIngredients(int recipeId) throws RecipeDataAccessException {
            return null;
        }

        @Override
        public List<String> getSubstitutions(String ingredientName) throws RecipeDataAccessException {
            return null;
        }

        @Override
        public String getInstructions(int recipeId) throws RecipeDataAccessException {
            return null;
        }

        public void setMockRecipeData(List<Recipes> mockRecipeData) {
            this.mockRecipeData = mockRecipeData;
        }

        public void setShouldThrow(boolean shouldThrow) {
            this.shouldThrow = shouldThrow;
        }
    }

    class InMemoryRecipeOutputBoundary implements RecipeOutputBoundary {
        private List<Recipes> result;
        private String errorMessage;

        @Override
        public void prepareSuccessView(List<Recipes> recipeContents) {
            this.result = recipeContents;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public List<Recipes> getResult() {
            return result;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }
}
