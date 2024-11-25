package data_access;

import entities.CommonUser;
import entities.Review;
import entities.Recipes;
import use_case.recipe_review.RecipeReviewDataAccessException;
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

    public FileReviewDataAccessObject(String csvPath) throws RecipeReviewDataAccessException {
        csvFile = new File(csvPath);

        try {
            if (!csvFile.exists()) {
                saveToFile();
            }
            else {
                load();
            }
        } catch (IOException e) {
            throw new RecipeReviewDataAccessException("Error initializing review file: " + e.getMessage());
        }
    }

    /**
     * Load reviews from the file into memory.
     */
    private void load() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String header = reader.readLine();
            if (header == null || !header.equals(HEADER)) {
                System.err.println("Invalid file format. Expected header: " + HEADER);
                return;
            }

            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",", -1);
                if (columns.length < 3) continue;

                String recipeName = unescapeCsv(columns[0]);
                String username = unescapeCsv(columns[1]);
                String content = unescapeCsv(columns[2]);

                CommonUser user = new CommonUser(username, "");
                Review review = new Review(user, content, recipeName);
                recipeReviews.computeIfAbsent(recipeName, k -> new ArrayList<>()).add(review);
            }
        } catch (Exception e) {
            System.err.println("Error loading reviews from file: " + e.getMessage());
        }
    }

    private void saveToFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            writer.write(HEADER);
            writer.newLine();

            for (Map.Entry<String, List<Review>> entry : recipeReviews.entrySet()) {
                String recipeName = escapeCsv(entry.getKey());
                for (Review review : entry.getValue()) {
                    String line = String.format("%s,%s,%s",
                            recipeName,
                            escapeCsv(review.getUser().getName()),
                            escapeCsv(review.getContent()));
                    writer.write(line);
                    writer.newLine();
                }
            }
        }
    }

    @Override
    public void saveReview(Review review) {
        List<Review> reviews = recipeReviews.computeIfAbsent(review.getRecipeName(), k -> new ArrayList<>());

        Optional<Review> existingReview = reviews.stream()
                .filter(r -> r.getUser().getName().equals(review.getUser().getName()))
                .findFirst();

        if (existingReview.isPresent()) {
            existingReview.get().setContent(review.getContent());
        } else {
            reviews.add(review);
        }

        try {
            saveToFile();
        } catch (IOException e) {
            throw new RuntimeException("Error saving review to file", e);
        }
    }

    @Override
    public List<Review> getReviewsByRecipe(Recipes recipe) {
        return new ArrayList<>(recipeReviews.getOrDefault(recipe.getName(), Collections.emptyList()));
    }

    private String escapeCsv(String value) {
        return value.replace(",", "\\,");
    }

    private String unescapeCsv(String value) {
        return value.replace("\\,", ",");
    }
}
