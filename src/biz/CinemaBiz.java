package biz;

import entity.Cinema;
import entity.HallPerfect;

import java.util.ArrayList;

public interface CinemaBiz {
    //添加影院
    boolean addCinema(Cinema cinema);

    //查询影院
    ArrayList<Cinema> queryCinema();

    //修改影院
    boolean updateCinema(Cinema cinema);

    //删除影院
    boolean deleteCinema(int cinemaId);

    //根据地址查询影院
    ArrayList<Cinema> queryCinemaByAddress(String address);

    //根据ID查询影院
    Cinema queryById(int cinemaId);

    //根据影院查询场厅
    ArrayList<HallPerfect> queryHallByCid(int cid);

}
