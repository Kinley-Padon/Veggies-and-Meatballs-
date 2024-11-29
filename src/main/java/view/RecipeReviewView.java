package view;


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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


/**
 * The View for when the user is viewing and adding a recipe review.
 */
public class RecipeReviewView extends JPanel implements ActionListener, PropertyChangeListener {


    private final RecipeReviewViewModel recipeReviewViewModel;

    private final JLabel recipeName = new JLabel("Recipe Review for: [Recipe Name]");
    private final JTextArea reviewInputField = new JTextArea();

    private final JButton saveButton = new JButton("Save Review");
    private final JTextArea reviewsDisplayArea = new JTextArea();


    private RecipeReviewController recipeReviewController;
    private CommonUser currentUser;
    private Recipes currentRecipe;


    public RecipeReviewView(RecipeReviewViewModel recipeReviewViewModel, LoginInteractor loginInteractor,
                            InMemoryUserDataAccessObject userDataAccessObject) {

        recipeName.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.recipeReviewViewModel = recipeReviewViewModel;
        this.recipeReviewViewModel.addPropertyChangeListener(this);


        String currentUsername = loginInteractor.getCurrentUsername();
        if (currentUsername != null) {
            this.currentUser = (CommonUser) userDataAccessObject.get(currentUsername);
        }


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

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(recipeName);
        this.add(new JScrollPane(reviewInputField));
        this.add(new JScrollPane(reviewsDisplayArea));
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(saveButton);
        this.add(buttonsPanel);
    }

    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    public void setCurrentUserAndRecipe(CommonUser user, Recipes recipe) {
        System.out.println("Setting current user: " + user.getName());
        System.out.println("Setting current recipe: " + recipe.getName());
        this.currentUser = user;
        this.currentRecipe = recipe;
        if (user != null && recipe != null) {
            recipeName.setText("Recipe Review for: " + recipe.getName());
        } else {
            JOptionPane.showMessageDialog(this, "Error: User or Recipe is null.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("Property change event received: " + evt.getNewValue());
        if (evt.getNewValue() instanceof RecipeReviewState) {
            RecipeReviewState state = (RecipeReviewState) evt.getNewValue();
            setFields(state);
            if (state.getErrorMessage() != null) {
                JOptionPane.showMessageDialog(this, state.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            if (state.getSuccessMessage() != null) {
                JOptionPane.showMessageDialog(this, state.getSuccessMessage(), "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            System.out.println("Received an unexpected property change event: " + evt.getNewValue());
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
