package app;

import data_access.InMemoryFavoriteRecipeDAO;
import entities.Recipes;
import interface_adapter.recipe_search.RecipeController;
import interface_adapter.recipe_search.RecipePresenter;
import interface_adapter.recipe_search.RecipeViewModel;
import use_case.recipe_search.RecipeDataAccessInterface;
import use_case.recipe_search.RecipeInteractor;
import use_case.recipe_search.RecipeOutputBoundary;
import view.RecipeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class RecipeAppBuilder {
    public static final int HEIGHT = 300;
    public static final int WIDTH = 400;

    private RecipeDataAccessInterface recipeDAO;
    private RecipeViewModel recipeViewModel = new RecipeViewModel();
    private RecipeView recipeView;
    private RecipeInteractor recipeInteractor;
    private InMemoryFavoriteRecipeDAO favoriteRecipeDAO;

    /**
     * Sets the RecipeDAO to be used in this application.
     * @param recipeDataAccess the DAO to use
     * @return this builder
     */
    public RecipeAppBuilder addRecipeDAO(RecipeDataAccessInterface recipeDataAccess) {
        recipeDAO = recipeDataAccess;
        return this;
    }

    /**
     * Creates the objects for the Recipe Use Case and connects the RecipeView to its
     * controller.
     * <p>This method must be called after addRecipeView!</p>
     * @return this builder
     * @throws RuntimeException if this method is called before addRecipeView
     */
    public RecipeAppBuilder addRecipeUseCase() {
        final RecipeOutputBoundary recipeOutputBoundary = new RecipePresenter(recipeViewModel);
        recipeInteractor = new RecipeInteractor(recipeDAO, recipeOutputBoundary);

        final RecipeController controller = new RecipeController(recipeInteractor);
        if (recipeView == null) {
            throw new RuntimeException("addRecipeView must be called before addRecipeUseCase");
        }
        recipeView.setRecipeController(controller);
        return this;
    }

    /**
     * Creates the RecipeView and underlying RecipeViewModel.
     * @return this builder
     */
    public RecipeAppBuilder addRecipeView() {
        recipeViewModel = new RecipeViewModel();
        recipeView = new RecipeView(recipeViewModel);
        return this;
    }

    /**
     * Adds a Favorite Recipe DAO and sets up the Add to Favorites button.
     *
     * @param inMemoryFavoriteRecipeDAO the DAO to handle favorite recipes
     * @return this builder
     */
    public RecipeAppBuilder addFavoriteRecipeDAO(InMemoryFavoriteRecipeDAO inMemoryFavoriteRecipeDAO) {
        this.favoriteRecipeDAO = inMemoryFavoriteRecipeDAO;
        return this;
    }

    public RecipeAppBuilder addFavoriteRecipeUseCase() {
        if (favoriteRecipeDAO == null) {
            throw new RuntimeException("FavoriteRecipeDAO must be set before calling addFavoriteRecipeUseCase.");
        }

        // Add logic for managing favorite recipes if needed
        return this;
    }

    /**
     * Builds the application.
     *
     * @return the JFrame for the application
     */
    public JFrame build() {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Recipe Explorer");
        frame.setSize(WIDTH, HEIGHT);

        // Add the RecipeView
        frame.add(recipeView);

        // Add the "Add to Favorites" button
        JButton addToFavoritesButton = new JButton("Add to Favorites");
        addToFavoritesButton.setBounds(10, 250, 150, 30); // Adjust button position and size

        addToFavoritesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add current recipe to favorites
                if (recipeViewModel.getState().getRecipeDetails() != null) {
                    HashMap<String, Integer> recipeDetails = recipeViewModel.getState().getRecipeDetails();
                    Recipes recipes = new Recipes(recipeDetails);
                    favoriteRecipeDAO.addFavoriteRecipe(recipes);
                    JOptionPane.showMessageDialog(frame, "Recipe added to favorites!");
                } else {
                    JOptionPane.showMessageDialog(frame, "No recipe selected!");
                }
            }
        });

        frame.add(addToFavoritesButton);

        // Create the "Cancel" button (to go back to search)
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(170, 250, 150, 30);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current frame and go back to the recipe search view
                frame.setVisible(false); // Hide the current frame
                JFrame recipeSearchFrame = buildRecipeSearchFrame(); // Create a new frame for search view
                recipeSearchFrame.setVisible(true); // Display the recipe search view
            }
        });

        frame.add(cancelButton);

        // Use absolute layout for placing components
        frame.setLayout(null);
        return frame;
    }

    /**
     * Helper method to build the Recipe Search Frame.
     * @return a new JFrame for the recipe search view
     */
    private JFrame buildRecipeSearchFrame() {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Recipe Explorer");
        frame.setSize(WIDTH, HEIGHT);

        frame.add(recipeView); // Add the recipe view to the frame

        return frame;
    }
}

//
//package app;
//
//import javax.swing.JFrame;
//import javax.swing.WindowConstants;
//import javax.swing.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.HashMap;
//
//import data_access.InMemoryFavoriteRecipeDAO;
//import entities.Recipes;
//import interface_adapter.recipe_search.RecipeController;
//import interface_adapter.recipe_search.RecipePresenter;
//import interface_adapter.recipe_search.RecipeViewModel;
//import use_case.recipe_search.RecipeDataAccessInterface;
//import use_case.recipe_search.RecipeInteractor;
//import use_case.recipe_search.RecipeOutputBoundary;
//import view.RecipeView;
///**
// * Builder for the Recipe Application.
// */
//
//public class RecipeAppBuilder {
//    public static final int HEIGHT = 300;
//    public static final int WIDTH = 400;
//    private RecipeDataAccessInterface recipeDAO;
//    private RecipeViewModel recipeViewModel = new RecipeViewModel();
//    private RecipeView recipeView;
//    private RecipeInteractor recipeInteractor;
//
//
//    /**
//     * Sets the RecipeDAO to be used in this application.
//     * @param recipeDataAccess the DAO to use
//     * @return this builder
//     */
//    public RecipeAppBuilder addRecipeDAO(RecipeDataAccessInterface recipeDataAccess) {
//        recipeDAO = recipeDataAccess;
//        return this;
//    }
//
//    /**
//     * Creates the objects for the Recipe Use Case and connects the RecipeView to its
//     * controller.
//     * <p>This method must be called after addRecipeView!</p>
//     * @return this builder
//     * @throws RuntimeException if this method is called before addRecipeView
//     */
//    public RecipeAppBuilder addRecipeUseCase() {
//        final RecipeOutputBoundary recipeOutputBoundary = new RecipePresenter(recipeViewModel);
//        recipeInteractor = new RecipeInteractor(recipeDAO, recipeOutputBoundary);
//
//        final RecipeController controller = new RecipeController(recipeInteractor);
//        if (recipeView == null) {
//            throw new RuntimeException("addRecipeView must be called before addRecipeUseCase");
//        }
//        recipeView.setRecipeController(controller);
//        return this;
//    }
//
//    /**
//     * Creates the RecipeView and underlying RecipeViewModel.
//     * @return this builder
//     */
//    public RecipeAppBuilder addRecipeView() {
//        recipeViewModel = new RecipeViewModel();
//        recipeView = new RecipeView(recipeViewModel);
//        return this;
//    }
//
//    /**
//     * Adds a Favorite Recipe DAO and sets up the Add to Favorites button.
//     *
//     * @param inMemoryFavoriteRecipeDAO the DAO to handle favorite recipes
//     * @return this builder
//     */
//    public RecipeAppBuilder addFavoriteRecipeDAO(InMemoryFavoriteRecipeDAO inMemoryFavoriteRecipeDAO) {
//        this.favoriteRecipeDAO = inMemoryFavoriteRecipeDAO;
//        return this;
//    }
//    private InMemoryFavoriteRecipeDAO favoriteRecipeDAO;
//
//    public RecipeAppBuilder addFavoriteRecipeUseCase() {
//        if (favoriteRecipeDAO == null) {
//            throw new RuntimeException("FavoriteRecipeDAO must be set before calling addFavoriteRecipeUseCase.");
//        }
//
//        // Example: Integrating favorite recipe DAO into the application
//        // Add logic for managing favorite recipes if needed
//        return this;
//    }
//
//
//    /**
//     * Builds the application.
//     *
//     * @return the JFrame for the application
//     */
//    public JFrame build() {
//        final JFrame frame = new JFrame();
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.setTitle("Recipe Explorer");
//        frame.setSize(WIDTH, HEIGHT);
//
//        // Add the RecipeView
//        frame.add(recipeView);
//
//        // Add the "Add to Favorites" button
//        JButton addToFavoritesButton = new JButton("Add to Favorites");
//        addToFavoritesButton.setBounds(10, 250, 150, 30); // Adjust button position and size
//
//        // Add action listener to the button
//        addToFavoritesButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // Example: Add a currently selected recipe to favorites
//                if (recipeViewModel.getState().getRecipeDetails() != null) {
//                    // Step 1: Retrieve the recipe details as a HashMap
//                    HashMap<String, Integer> recipeDetails = recipeViewModel.getState().getRecipeDetails();
//
//                    // Step 2: Convert the HashMap into a Recipes object
//                    Recipes recipes = new Recipes(recipeDetails);
//
//                    // Step 3: Add the Recipes object to favorites
//                    favoriteRecipeDAO.addFavoriteRecipe(recipes);
//                    JOptionPane.showMessageDialog(frame, "Recipe added to favorites!");
//                } else {
//                    JOptionPane.showMessageDialog(frame, "No recipe selected!");
//                }
//            }
//        });
//
//        // Add button to frame
//        frame.add(addToFavoritesButton);
//
//        // Create the "Cancel" button (to go back to search)
//        JButton cancelButton = new JButton("Cancel");
//        cancelButton.setBounds(170, 250, 150, 30); // Adjust position
//
//        // Add action listener for the Cancel button
//        cancelButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // Close the current frame
//                frame.setVisible(false);// Or dispose()
//                    final JFrame frame = new JFrame();
//                    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//                    frame.setTitle("Recipe Explorer");
//                    frame.setSize(WIDTH, HEIGHT);
//
//                    frame.add(recipeView);
//
//                frame.add(recipeView).setVisible(true);
//
//
//            }
//        });
//
//        // Add the Cancel button to the frame
//        frame.add(cancelButton);
//
//        // Use absolute layout for placing components
//        frame.setLayout(null);
//        return frame;
//
//    }
//
//}



////    /**
////     * Builds the application.
////     * @return the JFrame for the application
////     */
////    public JFrame build() {
////        final JFrame frame = new JFrame();
////        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
////        frame.setTitle("Recipe Explorer");
////        frame.setSize(WIDTH, HEIGHT);
////
////        frame.add(recipeView);
////
////        return frame;
////    }
//
//
//}
//
////
//package app;
//
//import data_access.InMemoryFavoriteRecipeDAO;
//import entities.Recipes;
//import interface_adapter.recipe_search.RecipeController;
//import interface_adapter.recipe_search.RecipePresenter;
//import interface_adapter.recipe_search.RecipeViewModel;
//import use_case.recipe_search.RecipeDataAccessInterface;
//import use_case.recipe_search.RecipeInteractor;
//import use_case.recipe_search.RecipeOutputBoundary;
//import view.RecipeView;
//
//import javax.swing.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.HashMap;
//
//public class RecipeAppBuilder {
//    public static final int HEIGHT = 300;
//    public static final int WIDTH = 400;
//
//    private RecipeDataAccessInterface recipeDAO;
//    private RecipeViewModel recipeViewModel = new RecipeViewModel();
//    private RecipeView recipeView;
//    private RecipeInteractor recipeInteractor;
//    private InMemoryFavoriteRecipeDAO favoriteRecipeDAO;
//
//    /**
//     * Sets the RecipeDAO to be used in this application.
//     * @param recipeDataAccess the DAO to use
//     * @return this builder
//     */
//    public RecipeAppBuilder addRecipeDAO(RecipeDataAccessInterface recipeDataAccess) {
//        recipeDAO = recipeDataAccess;
//        return this;
//    }
//
//    /**
//     * Creates the objects for the Recipe Use Case and connects the RecipeView to its
//     * controller.
//     * <p>This method must be called after addRecipeView!</p>
//     * @return this builder
//     * @throws RuntimeException if this method is called before addRecipeView
//     */
//    public RecipeAppBuilder addRecipeUseCase() {
//        final RecipeOutputBoundary recipeOutputBoundary = new RecipePresenter(recipeViewModel);
//        recipeInteractor = new RecipeInteractor(recipeDAO, recipeOutputBoundary);
//
//        final RecipeController controller = new RecipeController(recipeInteractor);
//        if (recipeView == null) {
//            throw new RuntimeException("addRecipeView must be called before addRecipeUseCase");
//        }
//        recipeView.setRecipeController(controller);
//        return this;
//    }
//
//    /**
//     * Creates the RecipeView and underlying RecipeViewModel.
//     * @return this builder
//     */
//    public RecipeAppBuilder addRecipeView() {
//        recipeViewModel = new RecipeViewModel();
//        recipeView = new RecipeView(recipeViewModel);
//        return this;
//    }
//
//    /**
//     * Adds a Favorite Recipe DAO and sets up the Add to Favorites button.
//     *
//     * @param inMemoryFavoriteRecipeDAO the DAO to handle favorite recipes
//     * @return this builder
//     */
//    public RecipeAppBuilder addFavoriteRecipeDAO(InMemoryFavoriteRecipeDAO inMemoryFavoriteRecipeDAO) {
//        this.favoriteRecipeDAO = inMemoryFavoriteRecipeDAO;
//        return this;
//    }
//
//    public RecipeAppBuilder addFavoriteRecipeUseCase() {
//        if (favoriteRecipeDAO == null) {
//            throw new RuntimeException("FavoriteRecipeDAO must be set before calling addFavoriteRecipeUseCase.");
//        }
//
//        // Add logic for managing favorite recipes if needed
//        return this;
//    }
//
//    /**
//     * Builds the application.
//     *
//     * @return the JFrame for the application
//     */
//    public JFrame build() {
//        final JFrame frame = new JFrame();
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.setTitle("Recipe Explorer");
//        frame.setSize(WIDTH, HEIGHT);
//
//        // Add the RecipeView initially
//        frame.add(recipeView);
//
//        // Add the "Add to Favorites" button
//        JButton addToFavoritesButton = new JButton("Add to Favorites");
//        addToFavoritesButton.setBounds(10, 250, 150, 30); // Adjust button position and size
//
//        addToFavoritesButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // Switch to the favorites view when "Add to Favorites" is clicked
//                frame.setVisible(false); // Hide the recipe view
//                buildFavoriteView(frame); // Build and show the favorite view
//            }
//        });
//
//        frame.add(addToFavoritesButton);
//
//        // Use absolute layout for placing components
//        frame.setLayout(null);
//        return frame;
//    }
//
//    /**
//     * Builds the Favorite View, showing the list of favorite recipes.
//     * @param parentFrame The parent frame to be hidden
//     */
//    private void buildFavoriteView(JFrame parentFrame) {
//        final JFrame favoriteFrame = new JFrame();
//        favoriteFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        favoriteFrame.setTitle("Favorite Recipes");
//        favoriteFrame.setSize(WIDTH, HEIGHT);
//
//        // Display favorite recipes (Placeholder for now)
//        JTextArea favoriteRecipesArea = new JTextArea("Your Favorite Recipes will appear here.");
//        favoriteRecipesArea.setEditable(false);
//        favoriteRecipesArea.setBounds(10, 10, WIDTH - 20, 200);
//        favoriteFrame.add(favoriteRecipesArea);
//
//        // Create the "Cancel" button (to go back to search)
//        JButton cancelButton = new JButton("Back to Search");
//        cancelButton.setBounds(170, 250, 150, 30);
//
//        cancelButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // Close the favorites view and go back to the recipe view
//                favoriteFrame.setVisible(false); // Hide the favorite view
//                parentFrame.setVisible(true); // Show the recipe search view again
//            }
//        });
//
//        favoriteFrame.add(cancelButton);
//
//        favoriteFrame.setLayout(null);
//        favoriteFrame.setVisible(true);
//    }
//}
