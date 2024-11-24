package data_access;

import entities.CommonUser;
import entities.Review;
import entities.Recipes;
import entities.User;
import use_case.recipe_review.RecipeReviewDataAccessInterface;

import java.io.*;
import java.util.*;

/**
 * DAO for storing and retrieving recipe reviews implemented using a file (CSV).
 * Each recipe will have its reviews stored in the file.
 */
public class FileReviewDataAccessObject implements RecipeReviewDataAccessInterface {

    private static final String HEADER = "recipe_name,username,content"; // CSV header

    private final File csvFile;
    private final Map<String, List<Review>> recipeReviews = new HashMap<>();

    public FileReviewDataAccessObject(String csvPath) throws IOException {
        csvFile = new File(csvPath);

        // If the file doesn't exist, create it and initialize with the header
        if (!csvFile.exists()) {
            save();
        } else {
            load();
        }
    }

    /**
     * Load reviews from the file into memory.
     */
    private void load() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String header = reader.readLine();
            if (header == null || !header.equals(HEADER)) {
                throw new RuntimeException("Invalid file format. Expected header: " + HEADER);
            }

            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                String recipeName = columns[0];
                String username = columns[1];
                String content = columns[2];

                // Create a new Review object and store it in the appropriate recipe list
                Review review = new Review( CommonUser(username, password), content, recipeName);
                recipeReviews.computeIfAbsent(recipeName, k -> new ArrayList<>()).add(review);
            }
        }
    }

    /**
     * Save all reviews to the file.
     */
    private void save() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            writer.write(HEADER);
            writer.newLine();

            for (Map.Entry<String, List<Review>> entry : recipeReviews.entrySet()) {
                String recipeName = entry.getKey();
                for (Review review : entry.getValue()) {
                    String line = String.format("%s,%s,%s", review.getRecipeName(), review.getUser().getName(), review.getContent());
                    writer.write(line);
                    writer.newLine();
                }
            }
        }
    }

    /**
     * Save a review for a recipe.
     *
     * @param review The review to save.
     */
    @Override
    public void save(Review review) {
        // Add the review to the appropriate recipe list
        recipeReviews.computeIfAbsent(review.getRecipeName(), k -> new ArrayList<>()).add(review);
        try {
            save(); // Persist the updated reviews to the file
        } catch (IOException e) {
            throw new RuntimeException("Error saving review to file", e);
        }
    }

    /**
     * Get all reviews for a given recipe name.
     *
     * @param recipe The recipe whose reviews we want to retrieve.
     * @return A list of reviews for the given recipe.
     */
    @Override
    public List<Review> getReviewsByRecipe(Recipes recipe) {
        return recipeReviews.getOrDefault(recipe.getName(), Collections.emptyList());
    }

    /**
     * Check if a review exists for a given recipe.
     *
     * @param recipeName The recipe to check for reviews.
     * @return True if there are reviews for the given recipe, false otherwise.
     */
    @Override
    public boolean existsByRecipe(String recipeName) {
        return recipeReviews.containsKey(recipeName) && !recipeReviews.get(recipeName).isEmpty();
    }
}
