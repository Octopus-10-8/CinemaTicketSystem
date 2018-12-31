package entity;

import java.io.Serializable;

/**
 * Date: 2018/12/25 0025
 **/
public class Comments implements Serializable {

    private int id;   //主键ID
    private int uid;    //用户ID
    private int movie_id;   //电影ID
    private String comments;   //评论内容
    private String commentsTime;  //评论时间

    public Comments(int uid, int movie_id, String comments, String commentsTime) {
        this.uid = uid;
        this.movie_id = movie_id;
        this.comments = comments;
        this.commentsTime = commentsTime;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "id=" + id +
                ", uid=" + uid +
                ", movie_id=" + movie_id +
                ", comments='" + comments + '\'' +
                ", commentsTime='" + commentsTime + '\'' +
                '}';
    }

    public String getCommentsTime() {
        return commentsTime;
    }

    public void setCommentsTime(String commentsTime) {
        this.commentsTime = commentsTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Comments(int id, int uid, int movie_id, String comments) {
        this.id = id;
        this.uid = uid;
        this.movie_id = movie_id;
        this.comments = comments;
    }
}
