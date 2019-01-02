package daoImpl;

import dao.CinemaDao;
import entity.Cinema;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 **/
public class CinemaDaoImpl extends BaseDao implements CinemaDao {
     //初始化表信息
    public CinemaDaoImpl() {
        super(new File("Cinema.txt"));
    }

    ArrayList<Cinema> cinemaArrayList = null;

    @Override
    public void save(Cinema cinema) {
        cinemaArrayList = read();
        if (cinemaArrayList.size() == 0) {
            cinema.setId(1);
        } else {
            cinema.setId(cinemaArrayList.get(cinemaArrayList.size() - 1).getId() + 1);
        }
        cinemaArrayList.add(cinema);
        write(cinemaArrayList);
        closeAll();
    }

    @Override
    public ArrayList<Cinema> queryCinema() {
        cinemaArrayList = read();
        return cinemaArrayList;
    }

    @Override
    public void updateCinema(Cinema cinema) {
        cinemaArrayList = read();
        for (int i = 0; i < cinemaArrayList.size(); i++) {
            if (cinemaArrayList.get(i).getId() == cinema.getId()) {
                cinemaArrayList.set(i, cinema);
            }
        }
        write(cinemaArrayList);
        closeAll();

    }

    @Override
    public void deleteCinema(int cinemaId) {

        cinemaArrayList = read();
        Iterator<Cinema> iterator = cinemaArrayList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId() == cinemaId) {
                iterator.remove();
            }
        }
        write(cinemaArrayList);
        closeAll();
    }

    @Override
    public Cinema queryCinemaById(int cinemaId) {
        ArrayList<Cinema> read = read();
        for (Cinema cinema : read) {
            if (cinema.getId() == cinemaId) {
                return cinema;
            }
        }
        return null;
    }
}
