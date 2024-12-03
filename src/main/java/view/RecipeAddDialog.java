package view;

import entities.Ingredient;
import interface_adapter.recipe_add.RecipeAddController;
import interface_adapter.recipe_add.RecipeAddViewModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeAddDialog extends JDialog {
    private final RecipeAddController recipeAddController;

    private JTextField recipeNameField;
    private JTextArea stepsArea;
    private DefaultListModel<String> ingredientsListModel;
    private JList<String> ingredientsList;
    private JLabel recipeIDLabel;

    public RecipeAddDialog(RecipeAddController recipeAddController, RecipeAddViewModel viewModel) {
        this.recipeAddController = recipeAddController;

        setTitle("Add New Recipe");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 2, 10, 10));
        add(topPanel, BorderLayout.NORTH);

        JButton generateIDButton = new JButton("Generate Recipe ID");
        generateIDButton.addActionListener(e -> generateRecipeID());
        topPanel.add(generateIDButton);

        recipeIDLabel = new JLabel("Generated ID will be displayed here");
        topPanel.add(recipeIDLabel);

        JLabel recipeNameLabel = new JLabel("Recipe Name:");
        topPanel.add(recipeNameLabel);

        recipeNameField = new JTextField(20);
        topPanel.add(recipeNameField);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout(10, 10));
        add(centerPanel, BorderLayout.CENTER);

        JPanel ingredientsPanel = new JPanel();
        ingredientsPanel.setLayout(new BorderLayout(5, 5));
        centerPanel.add(ingredientsPanel, BorderLayout.NORTH);

        JLabel ingredientsLabel = new JLabel("Ingredients:");
        ingredientsPanel.add(ingredientsLabel, BorderLayout.NORTH);

        ingredientsListModel = new DefaultListModel<>();
        ingredientsList = new JList<>(ingredientsListModel);
        JScrollPane ingredientsScrollPane = new JScrollPane(ingredientsList);
        ingredientsPanel.add(ingredientsScrollPane, BorderLayout.CENTER);

        JPanel ingredientButtonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ingredientsPanel.add(ingredientButtonsPanel, BorderLayout.SOUTH);

        JButton addIngredientButton = new JButton("Add Ingredient");
        addIngredientButton.addActionListener(e -> addIngredient());
        ingredientButtonsPanel.add(addIngredientButton);

        JButton removeIngredientButton = new JButton("Remove Selected Ingredient");
        removeIngredientButton.addActionListener(e -> removeSelectedIngredient());
        ingredientButtonsPanel.add(removeIngredientButton);

        JPanel stepsPanel = new JPanel();
        stepsPanel.setLayout(new BorderLayout(5, 5));
        centerPanel.add(stepsPanel, BorderLayout.CENTER);

        JLabel stepsLabel = new JLabel("Steps:");
        stepsPanel.add(stepsLabel, BorderLayout.NORTH);

        stepsArea = new JTextArea(5, 30);
        JScrollPane stepsScrollPane = new JScrollPane(stepsArea);
        stepsPanel.add(stepsScrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        add(bottomPanel, BorderLayout.SOUTH);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> submitRecipe());
        bottomPanel.add(submitButton);
    }

    private void addIngredient() {
        String ingredientName = JOptionPane.showInputDialog(this, "Enter ingredient name:");
        String ingredientQuantity = JOptionPane.showInputDialog(this, "Enter ingredient quantity:");
        String ingredientUnit = JOptionPane.showInputDialog(this, "Enter ingredient unit:");

        if (ingredientName != null && ingredientQuantity != null && ingredientUnit != null) {
            try {
                double quantity = Double.parseDouble(ingredientQuantity);
                String ingredientDetails = ingredientName + " | " + quantity + " | " + ingredientUnit;
                ingredientsListModel.addElement(ingredientDetails);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid quantity. Please enter a valid number.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removeSelectedIngredient() {
        int selectedIndex = ingredientsList.getSelectedIndex();
        if (selectedIndex != -1) {
            ingredientsListModel.remove(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(this, "No ingredient selected to remove.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateRecipeID() {
        try {
            int generatedId = recipeAddController.generateRecipeId();
            recipeIDLabel.setText("Recipe ID: " + generatedId);
        } catch (Exception e) {
            recipeIDLabel.setText("Error generating ID: " + e.getMessage());
        }
    }

    private void submitRecipe() {
        String recipeName = recipeNameField.getText();
        String description = stepsArea.getText();
        List<String> ingredients = new ArrayList<>();

        for (int i = 0; i < ingredientsListModel.size(); i++) {
            ingredients.add(ingredientsListModel.getElementAt(i));
        }

        if (recipeName.isEmpty() || ingredients.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            recipeAddController.addRecipe(recipeName, parseIngredients(ingredients), description);
            JOptionPane.showMessageDialog(this, "Recipe added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private List<Ingredient> parseIngredients(List<String> ingredientDetails) {
        List<Ingredient> ingredients = new ArrayList<>();
        for (String detail : ingredientDetails) {
            String[] parts = detail.split("\\|");
            if (parts.length == 3) {
                String name = parts[0].trim();
                double quantity = Double.parseDouble(parts[1].trim());
                String unit = parts[2].trim();
                ingredients.add(new Ingredient(name, quantity, unit));
            }
        }
        return ingredients;
    }
}
