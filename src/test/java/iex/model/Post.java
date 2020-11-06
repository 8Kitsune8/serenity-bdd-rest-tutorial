package iex.model;



public class Post {

    private Integer userId;
    private String title;
    private String body;

    public Post(Integer userId,  String title, String body) {
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    public Integer getUserId() {
        return userId;
    }


    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
