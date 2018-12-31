package biz;

import entity.Key;

import java.util.ArrayList;

public interface KeyBiz {

    ArrayList<Key> queryKey();

    boolean addKey(Key key);
}
