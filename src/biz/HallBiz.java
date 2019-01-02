package biz;


import entity.Hall;
import entity.HallPerfect;


import java.util.ArrayList;

public interface HallBiz {
    //添加场厅
    boolean save(Hall hall);

    //查询场厅
    ArrayList<HallPerfect> queryHall();

    //修改场厅
    boolean updateHall(Hall hall);

    //删除场厅
    boolean deleteHall(int hallId);

    //查询单个场厅
    Hall queryHallByID(int hallId);

    //查询某个电影院下面的场厅
    ArrayList<HallPerfect> queryHallByCid(int cid);
}
