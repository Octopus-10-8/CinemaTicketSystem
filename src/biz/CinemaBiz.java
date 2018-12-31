package biz;

import entity.Cinema;
import entity.HallPerfect;

import java.util.ArrayList;

public interface CinemaBiz {

    boolean addCinema(Cinema cinema);

    ArrayList<Cinema> queryCinema();

    boolean updateCinema(Cinema cinema);

    boolean deleteCinema(int cinemaId);

    ArrayList<Cinema> queryCinemaByAddress(String address);

    Cinema queryById(int cinemaId);

    ArrayList<HallPerfect> queryHallByCid(int cid);


}
