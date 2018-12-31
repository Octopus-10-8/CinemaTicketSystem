package entity;

import java.io.Serializable;
import java.util.Arrays;

/**
 * ticket	影票	影票ID	所属用户	场次	座位号
 * id	uid	sid	seat
 **/
public class Ticket implements Serializable {

    private int id;
    private int uid;
    private int sid;
    private int movieId;
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
