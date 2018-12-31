package dao;

import entity.Hall;


import java.util.ArrayList;

public interface HallDao {

    void save(Hall hall);

    ArrayList<Hall> queryHall();

    void updateHall(Hall hall);

    void deleteHall(int hallId);

    Hall queryHallByID(int hallId);

}
