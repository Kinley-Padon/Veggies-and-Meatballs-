package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import interface_adapter.recipe_search.RecipeController;
import interface_adapter.recipe_search.RecipeViewModel;
import interface_adapter.recipe_search.RecipeState;

/**
 * The View for managing and displaying recipe details in the program.
 */
public class RecipeView extends JPanel implements ActionListener, PropertyChangeListener {

    private final RecipeViewModel recipeViewModel;

    private final JLabel recipeNameLabel = new JLabel("Enter Recipe Name:");
    private final JTextArea recipeInputField = new JTextArea(1, 20); // Single-line input for recipe name
    private final JButton searchButton = new JButton("Search");

    private final JTextArea recipeResultsArea = new JTextArea(10, 30);  // Multiline area to display results
    private final JScrollPane recipeResultsScrollPane = new JScrollPane(recipeResultsArea);

    private RecipeController recipeController;

    /**
     * Constructor for initializing the RecipeView.
     *
     * @param recipeViewModel The RecipeViewModel to bind to the view.
     */
    public RecipeView(RecipeViewModel recipeViewModel) {
        this.recipeViewModel = recipeViewModel;
        this.recipeViewModel.addPropertyChangeListener(this);

        recipeNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        searchButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(searchButton)) {
                        if (recipeController != null) {
                            String recipeName = recipeInputField.getText();
                            recipeController.searchRecipe(recipeName);
                        }
                    }
                }
        );

        this.add(recipeNameLabel);
        this.add(recipeInputField);
        this.add(searchButton);
        this.add(new JLabel("Search Results:"));
        this.add(recipeResultsScrollPane);
    }

    /**
     * Handles button click actions.
     *
     * @param evt the ActionEvent to handle.
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Button clicked: " + evt.getActionCommand());
    }

    /**
     * Reacts to property changes from the RecipeViewModel.
     *
     * @param evt the PropertyChangeEvent to process.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            final RecipeState state = (RecipeState) evt.getNewValue();
            updateFields(state);
            if (state.getErrorMessage() != null) {
                JOptionPane.showMessageDialog(this, state.getErrorMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Updates the input fields based on the RecipeState.
     *
     * @param state The RecipeState to update from.
     */
    private void updateFields(RecipeState state) {
        // Retrieve the recipe details and display them as a formatted string
        HashMap<String, Integer> recipeDetails = state.getRecipeDetails();
        if (recipeDetails != null && !recipeDetails.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Integer> entry : recipeDetails.entrySet()) {
                sb.append("Recipe Name: ").append(entry.getKey())
                        .append(" | ID: ").append(entry.getValue())
                        .append("\n");
            }
            recipeResultsArea.setText(sb.toString());
        } else {
            recipeResultsArea.setText("No recipes found for the given search.");
        }
    }

    /**
     * Sets the controller for handling recipe actions.
     *
     * @param controller The RecipeController to set.
     */
    public void setRecipeController(RecipeController controller) {
        this.recipeController = controller;
    }
}
