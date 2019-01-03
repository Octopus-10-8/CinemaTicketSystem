package entity;

import java.io.Serializable;
import java.util.Arrays;

/**
 *
 * 影票表
 **/
public class Ticket implements Serializable {

    private int id;   //影票ID
    private int uid;   //用户ID
    private int sid;   //场次ID
    private int movieId;   //电影ID
    private String seat;   //eg:5排1座

    public Ticket(int uid, int sid, int movieId, String seat) {
        this.uid = uid;
        this.sid = sid;
        this.movieId = movieId;
        this.seat = seat;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", uid=" + uid +
                ", sid=" + sid +
                ", movieId=" + movieId +
                ", seat='" + seat + '\'' +
                '}';
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

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }
}
