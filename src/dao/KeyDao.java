package dao;

import entity.Key;

import java.util.ArrayList;


public interface KeyDao {

    ArrayList<Key> queryKey();

    void addKey(Key key);
}
