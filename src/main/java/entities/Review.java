package entities;

public class Review {

    private CommonUser user;
    private String content;
    private String recipeName;

    public Review(CommonUser user, String content, String recipeName) {
        this.user = user;
        this.content = content;
        this.recipeName = recipeName;
    }

    public CommonUser getUser() {
        return user;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return recipeName + "\n" + user.getName() + ": " + content;
    }

}
