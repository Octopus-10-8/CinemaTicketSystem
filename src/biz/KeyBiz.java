package biz;

import entity.Key;

import java.util.ArrayList;

public interface KeyBiz {
    //查询关键字
    ArrayList<Key> queryKey();

    //添加关键字
    boolean addKey(Key key);
}
