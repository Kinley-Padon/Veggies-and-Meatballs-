package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import interface_adapter.recipe_search.RecipeController;
import interface_adapter.recipe_search.RecipeViewModel;
import interface_adapter.recipe_search.RecipeState;

/**
 * The View for managing and displaying recipe details in the program.
 */
public class RecipeView extends JPanel implements ActionListener, PropertyChangeListener {

    private final RecipeViewModel recipeViewModel;

    private final JLabel recipeName = new JLabel("Recipe Details");
    private final JTextArea recipeInputField = new JTextArea();

    private final JButton saveButton = new JButton("Save");
    private final JButton refreshButton = new JButton("Refresh");

    private RecipeController recipeController;

    /**
     * Constructor for initializing the RecipeView.
     *
     * @param recipeViewModel The RecipeViewModel to bind to the view.
     */
    public RecipeView(RecipeViewModel recipeViewModel) {

        this.recipeViewModel = recipeViewModel;
        this.recipeViewModel.addPropertyChangeListener(this);

        recipeName.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel buttons = new JPanel();
        buttons.add(saveButton);
        buttons.add(refreshButton);

        saveButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(saveButton)) {
                        if (recipeController != null) {
                            recipeController.searchRecipe(recipeInputField.getText());
                        }
                    }
                }
        );


        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(recipeName);
        this.add(recipeInputField);
        this.add(buttons);
    }

    /**
     * Handles button click actions.
     * @param evt the ActionEvent to handle.
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Button clicked: " + evt.getActionCommand());
    }

    /**
     * Reacts to property changes from the RecipeViewModel.
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
     * @param state The RecipeState to update from.
     */
    private void updateFields(RecipeState state) {
        recipeInputField.setText(state.getRecipeDetails());
    }

    /**
     * Sets the controller for handling recipe actions.
     * @param controller The RecipeController to set.
     */
    public void setRecipeController(RecipeController controller) {
        this.recipeController = controller;
    }
}
