package entities;

public class Review {

    private CommonUser user;
    private String content;
    private Recipes recipe;

    public Review(CommonUser user, String content, Recipes recipe) {
        this.user = user;
        this.content = content;
        this.recipe = recipe;
    }

    public CommonUser getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return recipe.getName() + "\n" + user.getName() + ": " + content;
    }

}
