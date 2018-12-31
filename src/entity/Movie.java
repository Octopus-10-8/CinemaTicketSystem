package entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * movie	电影表	电影ID	电影名称	电影详情	电影时长	电影类型
 * id	name	detail	duration	type
 **/
public class Movie implements Serializable {

    private int id;       //电影ID
    private String name;  //电影名称
    private String detail; //电影详情
    private int duration; //电影时长
    private String type;    //电影类型


    @Override
    public String toString() {
        return "电影{" +
                "电影ID=[" + id +
                "]\t名称=[" + name +
                "]\t电影详情=[" + detail +
                "]\t电影时长=[" + duration +
                "]分钟\t类型=[" + type
                + "]}";
    }

    public Movie(String name, String detail, int duration, String type) {
        this.name = name;
        this.detail = detail;
        this.duration = duration;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Movie(int id, String name, String detail, int duration, String type) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.duration = duration;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        Movie movie = (Movie) o;
        return getId() == movie.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
