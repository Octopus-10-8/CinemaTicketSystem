package entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * cinema	影院	影院ID	影院名称	影院地址
 * id	name	address
 **/
public class Cinema implements Serializable {

    private int id;  //影院ID
    private String name;  //影院名称
    private String address;  //影院地址

    public Cinema(String name, String address) {
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return "电影院{\tID=[" + id +
                "]\t\t名称=[" + name +
                "]\t\t地址=[" + address +
                "]}";
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Cinema(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cinema)) return false;
        Cinema cinema = (Cinema) o;
        return getId() == cinema.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
