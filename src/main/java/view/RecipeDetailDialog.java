package view;

import app.AppBuilder;
import data_access.DBRecipeDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import entities.CommonUser;
import entities.Ingredient;
import entities.Recipes;
import interface_adapter.recipe_review.RecipeReviewController;
import interface_adapter.recipe_review.RecipeReviewViewModel;
import use_case.login.LoginInteractor;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;

public class RecipeDetailDialog extends JDialog {

    private final AppBuilder appBuilder;
    private final JLabel nameLabel = new JLabel();
    private final JLabel idLabel = new JLabel();
    private final JLabel imageLabel = new JLabel();
    private final JTextArea instructionArea = new JTextArea(5, 30);
    private final JButton addReviewButton = new JButton("Add Review");
    private CommonUser currentUser;
    private Recipes currentRecipe;


    public RecipeDetailDialog(JFrame parent, Recipes recipe, AppBuilder appBuilder, CommonUser currentUser) {
        super(parent, "Recipe Details", true);
        this.currentRecipe = recipe;
        this.appBuilder = (appBuilder != null) ? appBuilder : new AppBuilder();
        this.currentUser = currentUser;

        if (currentRecipe == null) {
            JOptionPane.showMessageDialog(this, "Recipe not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;  // Exit if no recipe is available
        }

        initUI();
        loadRecipeDetails(recipe);

        addReviewButton.addActionListener(e -> {
            if (e.getSource().equals(addReviewButton)) {
                openRecipeReviewDialog();
            }
        });

    }

    public RecipeDetailDialog(JFrame parent, Recipes recipe, AppBuilder appBuilder) {
        this(parent, recipe, appBuilder, null);
    }


    private void initUI() {
        this.setLayout(new BorderLayout());
        this.setSize(600, 500);

        instructionArea.setLineWrap(true);
        instructionArea.setWrapStyleWord(true);
        instructionArea.setEditable(false);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.add(nameLabel);
        detailsPanel.add(idLabel);
        detailsPanel.add(imageLabel);
        detailsPanel.add(new JLabel("Instruction:"));
        detailsPanel.add(new JScrollPane(instructionArea));

        this.add(detailsPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addReviewButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadRecipeDetails(Recipes recipe) {
        nameLabel.setText("Name: " + recipe.getName());

        loadImage(recipe.getImage());

        new Thread(() -> {
            try {
                DBRecipeDataAccessObject dataAccessObject = new DBRecipeDataAccessObject();
                List<Ingredient> ingredients = dataAccessObject.getRecipeIngredients(recipe.getID());

                // get instruction and setinto the description field
                String instruction = dataAccessObject.getInstructions(recipe.getID());
                instructionArea.setText(instruction);

                SwingUtilities.invokeLater(() -> displayIngredients(ingredients));
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this, "Failed to load ingredients.", "Error", JOptionPane.ERROR_MESSAGE);
                });
            }
        }).start();
    }

    private void loadImage(final String imageUrl) {
        new Thread(() -> {
            try {
                System.out.println(imageUrl);
                ImageIcon imageIcon = new ImageIcon(new URL(imageUrl));
                Image scaledImage = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // 缩放图片
                imageIcon = new ImageIcon(scaledImage);

                ImageIcon finalImageIcon = imageIcon;
                SwingUtilities.invokeLater(() -> imageLabel.setIcon(finalImageIcon));
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    imageLabel.setText("Failed to load image.");
                    imageLabel.setIcon(null);
                });
            }
        }).start();
    }


    private void displayIngredients(List<Ingredient> ingredients) {
        JPanel ingredientsPanel = new JPanel();
        ingredientsPanel.setLayout(new BoxLayout(ingredientsPanel, BoxLayout.Y_AXIS));

        for (Ingredient ingredient : ingredients) {
            JPanel ingredientPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel ingredientLabel = new JLabel(String.format("%s: %.2f %s", ingredient.getName(), ingredient.getQuantity(), ingredient.getUnit()));
            JButton substitutionButton = new JButton("Show Substitution");

            substitutionButton.addActionListener(evt -> showSubstitution(ingredient.getName()));

            ingredientPanel.add(ingredientLabel);
            ingredientPanel.add(substitutionButton);
            ingredientsPanel.add(ingredientPanel);
        }

        JScrollPane scrollPane = new JScrollPane(ingredientsPanel);
        this.add(scrollPane, BorderLayout.CENTER);

        this.revalidate();
        this.repaint();
    }

    private void showSubstitution(String ingredientName) {
        new Thread(() -> {
            try {
                DBRecipeDataAccessObject dataAccessObject = new DBRecipeDataAccessObject();
                List<String> substitutions = dataAccessObject.getSubstitutions(ingredientName);

                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this,
                            String.join("\n", substitutions),
                            "Substitutions for " + ingredientName,
                            JOptionPane.INFORMATION_MESSAGE);
                });

            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this,
                            "Failed to fetch substitutions for " + ingredientName,
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                });
            }
        }).start();
    }

    private void openRecipeReviewDialog() {
        System.out.println("Opening Recipe Review Dialog");

        if (currentUser == null) {
            JOptionPane.showMessageDialog(this, "User is not logged in recipe details diaglog.", "Error", JOptionPane.ERROR_MESSAGE);
            return;  // Exit if no user is set
        }

        if (currentRecipe == null) {
            JOptionPane.showMessageDialog(this, "Recipe not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        System.out.println(currentRecipe.getName());
        System.out.println(currentUser.getName());

        SwingUtilities.invokeLater(() -> {
            JDialog reviewDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Reviews", true);
            RecipeReviewView reviewView = new RecipeReviewView(
                    appBuilder.getRecipeReviewViewModel(),
                    appBuilder.getLoginInteractor(),
                    appBuilder.getUserDataAccessObject()
            );

            reviewView.setRecipeReviewController(appBuilder.getRecipeReviewController());
            reviewView.setCurrentUserAndRecipe(currentUser, currentRecipe);
            reviewDialog.add(reviewView);
            reviewDialog.setSize(600, 400);
            reviewDialog.setLocationRelativeTo(this);
            reviewDialog.setVisible(true);
        });
    }
}
