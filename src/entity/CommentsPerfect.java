package entity;

/**
 * private int id;   //主键ID
 * private int uid;    //用户ID
 * private int movie_id;   //电影ID
 * private String comments;   //评论内容
 * private String commentsTime;  //评论时间
 * Date: 2018/12/27 0027
 **/
public class CommentsPerfect {
    private Comments comments;
    private User user;
    private Movie movie;

    public Comments getComments() {
        return comments;
    }

    public void setComments(Comments comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    //（id，评论内容，电影id，评论时间，用户id）
    @Override
    public String toString() {
        return "评论{\tid=[" + comments.getId() + "]\t电影名称=[" + movie.getName() + "]\t评论内容=[" + comments.getComments()
                + "]\t用户账号=[" + user.getName() + "]\t评论时间=[" + comments.getCommentsTime() + "]}";

    }

    public CommentsPerfect(Comments comments, User user, Movie movie) {
        this.comments = comments;
        this.user = user;
        this.movie = movie;
    }
}
