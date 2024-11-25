package view;

import entities.Ingredient;
import interface_adapter.recipe_add.RecipeAddViewModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;  // Don't forget to import List class

public class RecipeAddDialog extends JDialog {
    private final RecipeAddViewModel viewModel;
    private JTextField ingredientsField;
    private JTextField recipeNameField;
    private JTextArea stepsArea;
    private JButton submitButton;
    private JLabel recipeIdLabel;
    private JLabel recipeNameLabel;

    public RecipeAddDialog(RecipeAddViewModel viewModel) {
        this.viewModel = viewModel;
        setTitle("Add New Recipe");
        setSize(400, 300);
        setLocationRelativeTo(null);  // Center the dialog
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);  // Close the dialog when the user presses the close button

        initUI();
    }

    private void initUI() {
        setLayout(new GridLayout(5, 2, 10, 10));

        // Recipe ID and Name
        recipeIdLabel = new JLabel("Recipe ID: " + viewModel.getState().getRecipeID());
        add(recipeIdLabel);
        add(new JLabel());

        recipeNameLabel = new JLabel("Recipe Name");
        recipeNameField = new JTextField(10);// Empty label
        add(recipeNameLabel);
        add(recipeNameField); // Empty label

        // Recipe Ingredients
        JLabel ingredientsLabel = new JLabel("Ingredients:");
        ingredientsField = new JTextField(1000);
        add(ingredientsLabel);
        add(ingredientsField);

        // Recipe Steps
        JLabel stepsLabel = new JLabel("Steps:");
        stepsArea = new JTextArea(4, 20);
        JScrollPane stepsScrollPane = new JScrollPane(stepsArea);
        add(stepsLabel);
        add(stepsScrollPane);

        // Submit Button
        submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> submitRecipe());
        add(new JLabel());  // Empty cell
        add(submitButton);
    }

    private void submitRecipe() {
        String ingredientsText = ingredientsField.getText(); // Get the ingredients text
        String[] ingredientsArray = ingredientsText.split(","); // Split by comma or another separator

        List<Ingredient> ingredients = new ArrayList<>();

        for (String ingredientText : ingredientsArray) {
            // Assuming each ingredient is entered as "name | quantity | unit"
            String[] ingredientDetails = ingredientText.split("\\|"); // Assuming "|" separates name, quantity, and unit

            if (ingredientDetails.length == 3) {
                String name = ingredientDetails[0].trim();  // Ingredient name
                double quantity = Double.parseDouble(ingredientDetails[1].trim());  // Ingredient quantity
                String unit = ingredientDetails[2].trim();  // Ingredient unit

                // Create Ingredient objects with name, quantity, and unit
                ingredients.add(new Ingredient(name, quantity, unit));
            } else {
                JOptionPane.showMessageDialog(this, "Invalid ingredient format. Use: name | quantity | unit", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        String steps = stepsArea.getText(); // Get the recipe steps as text

        // Call the viewModel to trigger the Add Recipe use case
        viewModel.submitRecipe(ingredients, steps);

        // Close the dialog after submission
        dispose();
    }

    private void openAddRecipeDialog() {
        RecipeAddDialog dialog = new RecipeAddDialog(viewModel); // Pass the viewModel here
        dialog.setVisible(true);
    }
}
