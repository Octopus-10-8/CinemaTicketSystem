package entity;

import java.io.Serializable;

/**
 * session	场次	场次ID	场厅	电影院	电影	播放时间	价格	剩余座位数
 * id	hid	cid	movie_id	time	price	remain
 **/
public class Session implements Serializable {

    private int id;  //场次ID
    private int hid;//场厅ID
    private int cid;//电影院ID
    private int movie_id;//电影ID
    private String time;//	播放时间
    private double price;//价格
    private int remain;    //剩余座位数


    public Session(int id, int hid, int cid, int mmovie_id, String time, double price, int remain) {
        this.id = id;
        this.hid = hid;
        this.cid = cid;
        this.movie_id = mmovie_id;
        this.time = time;
        this.price = price;
        this.remain = remain;
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", hid=" + hid +
                ", cid=" + cid +
                ", mmovie_id='" + movie_id + '\'' +
                ", time='" + time + '\'' +
                ", price=" + price +
                ", remain=" + remain +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHid() {
        return hid;
    }

    public void setHid(int hid) {
        this.hid = hid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getMmovie_id() {
        return movie_id;
    }

    public void setMmovie_id(int mmovie_id) {
        this.movie_id = mmovie_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRemain() {
        return remain;
    }

    public void setRemain(int remain) {
        this.remain = remain;
    }

    public Session(int hid, int cid, int movie_id, String time, double price, int remain) {
        this.hid = hid;
        this.cid = cid;
        this.movie_id = movie_id;
        this.time = time;
        this.price = price;
        this.remain = remain;
    }
}
