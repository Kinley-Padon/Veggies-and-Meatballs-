package app;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.DBRecipeDataAccessObject;
import data_access.FileReviewDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import entities.CommonUserFactory;
import entities.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.ChangePasswordPresenter;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.recipe_review.RecipeReviewController;
import interface_adapter.recipe_review.RecipeReviewPresenter;
import interface_adapter.recipe_review.RecipeReviewViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.recipe_search.RecipeController;
import interface_adapter.recipe_search.RecipePresenter;
import interface_adapter.recipe_search.RecipeViewModel;
import use_case.change_password.ChangePasswordInputBoundary;
import use_case.change_password.ChangePasswordInteractor;
import use_case.change_password.ChangePasswordOutputBoundary;
import use_case.login.LoginInteractor;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.recipe_review.RecipeReviewDataAccessInterface;
import use_case.recipe_review.RecipeReviewInputBoundary;
import use_case.recipe_review.RecipeReviewInteractor;
import use_case.recipe_review.RecipeReviewOutputBoundary;
import use_case.recipe_search.RecipeInputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import use_case.recipe_search.RecipeDataAccessInterface;
import use_case.recipe_search.RecipeInteractor;
import use_case.recipe_search.RecipeOutputBoundary;
import view.*;

/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * <p/>
 * This is done by adding each View and then adding related Use Cases.
 */


public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    private final UserFactory userFactory = new CommonUserFactory();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);


    private final InMemoryUserDataAccessObject userDataAccessObject = new InMemoryUserDataAccessObject();
    private final RecipeDataAccessInterface recipeDAO = new DBRecipeDataAccessObject();
    private final RecipeReviewDataAccessInterface reviewDAO = new FileReviewDataAccessObject("path/to/review.csv"); // Path to review CSV

    private LoginPresenter loginPresenter;
    private final LoginInteractor loginInteractor = new LoginInteractor(userDataAccessObject, loginPresenter);

    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginViewModel loginViewModel;
    private LoggedInViewModel loggedInViewModel;
    private LoggedInView loggedInView;
    private LoginView loginView;
    private RecipeView recipeView;
    private RecipeReviewViewModel recipeReviewViewModel;
    private RecipeViewModel recipeViewModel;
    private RecipeReviewView recipeReviewView;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the Signup View to the application.
     * @return this builder
     */
    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    /**
     * Adds the Login View to the application.
     * @return this builder
     */
    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    /**
     * Adds the LoggedIn View to the application.
     * @return this builder
     */
    public AppBuilder addLoggedInView() {
        loggedInViewModel = new LoggedInViewModel();
        loggedInView = new LoggedInView(loggedInViewModel);
        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

    /**
     * Adds the Recipe Review View to the application.
     * @return this builder.
     */
    public AppBuilder addRecipeReviewView() {
        // Create the RecipeReviewView without any additional parameters.
        recipeReviewViewModel = new RecipeReviewViewModel();
        recipeReviewView = new RecipeReviewView(recipeReviewViewModel, loginInteractor, userDataAccessObject);

        cardPanel.add(recipeReviewView, "Recipe Review");
        return this;
    }

    /**
     * Adds the Recipe View to the application.
     * @return this builder.
     */
    public AppBuilder addRecipeView() {
        recipeViewModel = new RecipeViewModel();
        recipeView = new RecipeView(recipeViewModel);
        cardPanel.add(recipeView, "Gourmet Gateway");
        return this;
    }

    /**
     * Adds the Signup Use Case to the application.
     * @return this builder
     */
    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel, loginViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
                userDataAccessObject, signupOutputBoundary, userFactory);

        final SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
        return this;
    }

    /**
     * Adds the Login Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLoginUseCase() {
        loginPresenter = new LoginPresenter(viewManagerModel, loggedInViewModel, loginViewModel);
        loginPresenter.setLoginSuccessCallback(() -> cardLayout.show(cardPanel, "Gourmet Gateway"));

        final LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    public AppBuilder addRecipeReviewUseCase() {
        final RecipeReviewOutputBoundary recipeReviewOutputBoundary = new RecipeReviewPresenter(recipeReviewViewModel);
        final RecipeReviewInputBoundary recipeReviewInteractor = new RecipeReviewInteractor(reviewDAO, recipeReviewOutputBoundary);

        // Create the RecipeReviewController and link it with the RecipeReviewView
        final RecipeReviewController recipeReviewController = new RecipeReviewController(recipeReviewInteractor);
        recipeReviewView.setRecipeReviewController(recipeReviewController);

        return this;
    }

    /**
     * Adds the Recipe search Use Case to the application.
     * @return this builder
     */
    public AppBuilder addRecipeSearchUseCase() {
        final RecipeOutputBoundary recipeOutputBoundary = new RecipePresenter(recipeViewModel);
        final RecipeInputBoundary recipeInteractor = new RecipeInteractor(recipeDAO, recipeOutputBoundary);
        final RecipeController recipeController = new RecipeController(recipeInteractor);
        if (recipeView == null) {
            throw new RuntimeException("addRecipeView must be called before addRecipeUseCase");
        }
        recipeView.setRecipeController(recipeController);
        return this;
    }

    /**
     * Adds the Change Password Use Case to the application.
     * @return this builder
     */
    public AppBuilder addChangePasswordUseCase() {
        final ChangePasswordOutputBoundary changePasswordOutputBoundary =
                new ChangePasswordPresenter(loggedInViewModel);

        final ChangePasswordInputBoundary changePasswordInteractor =
                new ChangePasswordInteractor(userDataAccessObject, changePasswordOutputBoundary, userFactory);

        final ChangePasswordController changePasswordController =
                new ChangePasswordController(changePasswordInteractor);
        loggedInView.setChangePasswordController(changePasswordController);
        return this;
    }

    /**
     * Adds the Logout Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLogoutUseCase() {
        final LogoutOutputBoundary logoutOutputBoundary = new LogoutPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel);

        final LogoutInputBoundary logoutInteractor =
                new LogoutInteractor(userDataAccessObject, logoutOutputBoundary);

        final LogoutController logoutController = new LogoutController(logoutInteractor);
        loggedInView.setLogoutController(logoutController);
        return this;
    }

    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("Welcome to Gourmet Gateway");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }
}
