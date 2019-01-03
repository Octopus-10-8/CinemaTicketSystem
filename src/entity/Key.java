package entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * 热门关键字
 * Date: 2018/12/31 0031
 **/
public class Key implements Serializable {

    private String key;  //关键词

    private int count;   //关键词出现次数

    public Key(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Key{" +
                "key='" + key + '\'' +
                ", count=" + count +
                '}';
    }

    public Key(String key, int count) {
        this.key = key;
        this.count = count;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key)) return false;
        Key key1 = (Key) o;
        return Objects.equals(getKey(), key1.getKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey());
    }
}
