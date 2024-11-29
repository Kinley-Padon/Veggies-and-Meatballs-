package view;

import javax.swing.*;
import java.util.List;
import entities.Recipes;
import interface_adapter.recipe_favorites.FavoriteRecipeViewModel;

public class FavoriteRecipesView extends JPanel {
    private final FavoriteRecipeViewModel viewModel;

    public FavoriteRecipesView(FavoriteRecipeViewModel viewModel) {
        this.viewModel = viewModel;
        initUI();
    }

    private void initUI() {
        List<Recipes> favoriteRecipes = viewModel.getFavoriteRecipes();
        JList<Recipes> recipeList = new JList<>(favoriteRecipes.toArray(new Recipes[0]));
        add(new JScrollPane(recipeList));
    }
}