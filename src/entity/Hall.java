package entity;

import java.io.Serializable;

/**
 * hall	场厅	场厅ID	场厅名称		场厅容量
 * id	name		capacity
 **/
public class Hall implements Serializable {

    private int id;  //场厅ID
    private int cid;    //电影院名称
    private String name;  //场厅名称
    private int capacity;  //场厅容量




    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Hall(int cid, String name, int capacity) {
        this.cid = cid;
        this.name = name;
        this.capacity = capacity;
    }
}
