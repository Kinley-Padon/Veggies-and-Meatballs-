package view;

import data_access.DBRecipeDataAccessObject;
import entities.Ingredient;
import entities.Recipes;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;

public class RecipeDetailDialog extends JDialog {

    private final JLabel nameLabel = new JLabel();
    private final JLabel idLabel = new JLabel();
    private final JLabel imageLabel = new JLabel();
    private final JTextArea instructionArea = new JTextArea(5, 30);

    public RecipeDetailDialog(JFrame parent, Recipes recipe) {
        super(parent, "Recipe Details", true);
        this.setLayout(new BorderLayout());
        this.setSize(600, 400);

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

        loadRecipeDetails(recipe);
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
}
