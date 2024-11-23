package entities;

public class Review {

    private CommonUser user;
    private String content;

    public Review(CommonUser user, String content) {
        this.user = user;
        this.content = content;
    }

    public CommonUser getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return user.getName() + ": " + content;
    }

}
