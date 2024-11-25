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

    // Mock of the RecipeAddDataAccessInterface
    private static class MockDataAccess implements RecipeAddDataAccessInterface {
        private final Set<Integer> existingIds = new HashSet<>();

        @Override
        public Set<Integer> fetchExistingRecipeIds() {
            return existingIds; // Return the existing IDs (in-memory)
        }

        @Override
        public void saveRecipe(Recipes recipe) {
            // Simulate saving the recipe to the database (no action needed in the mock)
            existingIds.add(recipe.getID()); // Add the generated ID to the existing IDs
        }
    }

    @Test
    public void testGenerateUniqueRecipeId() {
        // Create a mock data access object
        MockDataAccess mockDataAccess = new MockDataAccess();

        // Create the interactor, passing the mock data access object
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

        // Add a few recipes to simulate the existing IDs
        interactor.addRecipe("Test Recipe 1", new ArrayList<>(), "Description 1");
        interactor.addRecipe("Test Recipe 2", new ArrayList<>(), "Description 2");

        // Validate the generated ID is unique (should not exist in the existing IDs set)
        Set<Integer> existingIds = mockDataAccess.fetchExistingRecipeIds();

        // Test that the number of unique recipe IDs is correct (should be 2)
        assertEquals(2, existingIds.size(), "The number of existing IDs should be 2.");

        // Generate a new ID and verify that it's not in the existing IDs
        int generatedId = interactor.generateRecipeId(); // Generate a new ID
        assertFalse(existingIds.contains(generatedId), "Generated ID should be unique and not exist in the existing IDs.");

        // Add the new ID to the mock data access to simulate saving it
        mockDataAccess.saveRecipe(new Recipes(generatedId, "New Recipe", new ArrayList<>(), "New Description"));

        // Check that the new ID is added
        assertTrue(existingIds.contains(generatedId), "The new ID should be added to the existing IDs after saving the recipe.");
    }
}
