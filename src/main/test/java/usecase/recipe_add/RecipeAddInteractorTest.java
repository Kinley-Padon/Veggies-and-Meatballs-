import entities.Recipes;
import org.junit.jupiter.api.Test;
import use_case.recipe_add.RecipeAddDataAccessInterface;
import use_case.recipe_add.RecipeAddInteractor;
import use_case.recipe_add.RecipeAddOutputBoundary;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RecipeAddInteractorTest {

    private static class MockDataAccess implements RecipeAddDataAccessInterface {
        private final Set<Integer> existingIds = new HashSet<>();

        @Override
        public Set<Integer> fetchExistingRecipeIds() {
            return existingIds;
        }

        @Override
        public void saveRecipe(Recipes recipe) {
            existingIds.add(recipe.getID());
        }
    }

    @Test
    public void testGenerateUniqueRecipeId() {
        MockDataAccess mockDataAccess = new MockDataAccess();
        RecipeAddOutputBoundary mockOutput = new RecipeAddOutputBoundary() {
            @Override
            public void displayAddedRecipe(Recipes recipe) {
                System.out.println("Recipe added with ID: " + recipe.getID());
            }

            @Override
            public void displayError(String errorMessage) {
                System.err.println("Error: " + errorMessage);
            }
        };

        RecipeAddInteractor interactor = new RecipeAddInteractor(mockOutput, mockDataAccess);
        interactor.addRecipe("Test Recipe 1", new ArrayList<>(), "Description 1");
        interactor.addRecipe("Test Recipe 2", new ArrayList<>(), "Description 2");

        Set<Integer> existingIds = mockDataAccess.fetchExistingRecipeIds();
        assertEquals(2, existingIds.size(), "The number of existing IDs should be 2.");
        int generatedId = interactor.generateRecipeId();
        assertFalse(existingIds.contains(generatedId), "Generated ID should be unique and not exist in the existing IDs.");

        mockDataAccess.saveRecipe(new Recipes(generatedId, "New Recipe", new ArrayList<>(), "New Description"));
        assertTrue(existingIds.contains(generatedId), "The new ID should be added to the existing IDs after saving the recipe.");
    }
}
