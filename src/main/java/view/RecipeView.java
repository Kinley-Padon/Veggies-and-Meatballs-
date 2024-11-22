package view;

import entities.Recipes;
import interface_adapter.recipe_search.RecipeController;
import interface_adapter.recipe_search.RecipeState;
import interface_adapter.recipe_search.RecipeViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for managing and displaying recipe details in the program.
 */
public class RecipeView extends JPanel implements ActionListener, PropertyChangeListener {

    private final RecipeViewModel recipeViewModel;

    private final JLabel recipeNameLabel = new JLabel("Enter Recipe Name:");
    private final JTextArea recipeInputField = new JTextArea(1, 20); // Single-line input for recipe name
    private final JButton searchButton = new JButton("Search");
    private final JLabel allergenNameLabel = new JLabel("Enter Allergen:");
    private final JTextArea allergenInputField = new JTextArea(1, 20); // Single-line input for recipe name

    private final DefaultListModel<Recipes> recipeListModel = new DefaultListModel<>();
    private final JList<Recipes> recipeResultsList = new JList<>(recipeListModel);

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
                            // 当点击搜索按钮时，调用控制器方法
                            String recipeName = recipeInputField.getText();
                            String allergen = allergenInputField.getText();
                            recipeController.searchRecipe(recipeName, allergen);
                        }
                    }
                }
        );

        // 设置列表为不可编辑
        recipeResultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        recipeResultsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // 双击事件
                    int index = recipeResultsList.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        Recipes selectedRecipe = recipeListModel.getElementAt(index);
                        showRecipeDetails(selectedRecipe);
                    }
                }
            }
        });

        JScrollPane recipeResultsScrollPane = new JScrollPane(recipeResultsList);
        this.add(recipeNameLabel);
        this.add(recipeInputField);
        this.add(allergenNameLabel);
        this.add(allergenInputField);
        this.add(searchButton);
        this.add(new JLabel("Search Results:"));
        this.add(recipeResultsScrollPane);
    }

    /**
     * 显示菜谱详细信息
     *
     * @param recipe 被选中的菜谱
     */
    private void showRecipeDetails(Recipes recipe) {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        RecipeDetailDialog detailDialog = new RecipeDetailDialog(parentFrame, recipe);
        detailDialog.setLocationRelativeTo(this);
        detailDialog.setVisible(true);
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
        recipeListModel.clear();
        if (state.getRecipeDetails() != null && !state.getRecipeDetails().isEmpty()) {
            for (Recipes recipe: state.getRecipeDetails()) {
                recipeListModel.addElement(recipe);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No recipes found for the given search.",
                    "No Results", JOptionPane.INFORMATION_MESSAGE);
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
