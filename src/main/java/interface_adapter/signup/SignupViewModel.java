package interface_adapter.signup;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Signup View.
 */
public class SignupViewModel extends ViewModel<SignupState> {

    public static final String TITLE_LABEL = "Get started with Gourmet Gateway";
    public static final String USERNAME_LABEL = "Enter Username";
    public static final String PASSWORD_LABEL = "Enter password";
    public static final String REPEAT_PASSWORD_LABEL = "Enter password again";

    public static final String SIGNUP_BUTTON_LABEL = "Sign up";
    public static final String CANCEL_BUTTON_LABEL = "Cancel";

    public static final String TO_LOGIN_BUTTON_LABEL = "Back to Login";

    public SignupViewModel() {
        super("sign up");
        setState(new SignupState());
    }

}
