package biz;


import entity.Hall;
import entity.HallPerfect;


import java.util.ArrayList;

public interface HallBiz {

    boolean save(Hall hall);

    ArrayList<HallPerfect> queryHall();

    boolean updateHall(Hall hall);

    boolean deleteHall(int hallId);

    Hall queryHallByID(int hallId);

    ArrayList<HallPerfect> queryHallByCid(int cid);
}
