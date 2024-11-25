package view;

import data_access.DBUserDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import entities.CommonUser;
import entities.Recipes;
import entities.Review;
import interface_adapter.recipe_review.RecipeReviewController;
import interface_adapter.recipe_review.RecipeReviewViewModel;
import interface_adapter.recipe_review.RecipeReviewState;
import use_case.login.LoginInteractor;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for when the user is viewing and adding a recipe review.
 */
public class RecipeReviewView extends JPanel implements PropertyChangeListener {

    private final RecipeReviewViewModel recipeReviewViewModel;
    private final JLabel recipeName = new JLabel("Recipe Review for: [Recipe Name]");
    private final JTextArea reviewInputField = new JTextArea();
    private final JButton saveButton = new JButton("Save Review");
    private final JTextArea reviewsDisplayArea = new JTextArea();

    private RecipeReviewController recipeReviewController;
    private CommonUser currentUser;
    private Recipes currentRecipe;

    public RecipeReviewView(RecipeReviewViewModel recipeReviewViewModel, LoginInteractor loginInteractor, InMemoryUserDataAccessObject userDataAccessObject) {
        this.recipeReviewViewModel = recipeReviewViewModel;
        this.recipeReviewViewModel.addPropertyChangeListener(this);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        String currentUsername = loginInteractor.getCurrentUsername();
        if (currentUsername != null) {
            this.currentUser = (CommonUser) userDataAccessObject.get(currentUsername);
        }

        recipeName.setAlignmentX(Component.CENTER_ALIGNMENT);

        saveButton.addActionListener(evt -> {
            if (evt.getSource().equals(saveButton)) {
                String reviewContent = reviewInputField.getText();
                if (currentUser != null && currentRecipe != null) {
                    recipeReviewController.addReview(currentUser, currentRecipe, reviewContent);
                    reviewInputField.setText(""); // Clear after submission
                } else {
                    JOptionPane.showMessageDialog(this, "Error: User or Recipe not set.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        this.add(recipeName);
        this.add(new JScrollPane(reviewInputField));
        this.add(new JScrollPane(reviewsDisplayArea));
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(saveButton);
        this.add(buttonsPanel);
    }

    public void setCurrentUserAndRecipe(CommonUser user, Recipes recipe) {
        this.currentUser = user;
        this.currentRecipe = recipe;
        recipeName.setText("Recipe Review for: " + recipe.getName());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof RecipeReviewState) {
            RecipeReviewState state = (RecipeReviewState) evt.getNewValue();
            setFields(state);
            if (state.getErrorMessage() != null) {
                JOptionPane.showMessageDialog(this, state.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            if (state.getSuccessMessage() != null) {
                JOptionPane.showMessageDialog(this, state.getSuccessMessage(), "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void setFields(RecipeReviewState state) {
        if (state.getReviews() != null && !state.getReviews().isEmpty()) {
            StringBuilder reviewsText = new StringBuilder("Reviews:\n");
            for (Review review : state.getReviews()) {
                reviewsText.append(review.getContent()).append("\n");
            }
            reviewsDisplayArea.setText(reviewsText.toString());
        }
    }

    public void setRecipeReviewController(RecipeReviewController controller) {
        this.recipeReviewController = controller;
    }
}

