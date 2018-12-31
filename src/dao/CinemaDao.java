package dao;

import entity.Cinema;

import java.util.ArrayList;

/**
 *
 **/
public interface CinemaDao {

    void save(Cinema cinema);

    ArrayList<Cinema> queryCinema();

    void updateCinema(Cinema cinema);

    void deleteCinema(int cinemaId);

     Cinema queryCinemaById(int cinemaId);

}
