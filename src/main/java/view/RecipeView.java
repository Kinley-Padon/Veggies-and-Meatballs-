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
import javax.swing.JFrame;
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
            showResultsInNewFrame(state);
            if (state.getErrorMessage() != null) {
                JOptionPane.showMessageDialog(this, state.getErrorMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Displays the search results in a new JFrame.
     *
     * @param state The RecipeState containing the search results.
     */
    private void showResultsInNewFrame(RecipeState state) {
        JFrame resultsFrame = new JFrame("Search Results");
        resultsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        resultsFrame.setSize(400, 300);

        JTextArea recipeResultsArea = new JTextArea(10, 30); // Multiline area to display results
        recipeResultsArea.setEditable(false);
        JScrollPane recipeResultsScrollPane = new JScrollPane(recipeResultsArea);

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
            recipeResultsArea.setText("No recipes found.");
        }

        JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.add(new JLabel("Search Results:"));
        resultsPanel.add(recipeResultsScrollPane);

        resultsFrame.add(resultsPanel);
        resultsFrame.setVisible(true);
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
