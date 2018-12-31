package biz;

import entity.Cinema;
import entity.Movie;
import entity.Ticket;
import entity.TicketPerfect;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Date: 2018/12/26 0026
 **/
public interface TicketBiz {
    boolean addTickets(Ticket ticket);

    ArrayList<TicketPerfect> queryTicket();

    boolean updateTicket(Ticket ticket);

    boolean deleteTicket(int ticketsId);

    Ticket queryTicketByID(int ticketsId);

    TicketPerfect queryTicketPerfectById(int id);

    ArrayList<String> getSeatBySessionId(int sessionId);

    ArrayList<TicketPerfect> queryTicketByuserId(int userID);

    HashMap<Movie, Double> queryBoxOffice();

    HashMap<Double, Movie> queryOneBoxOffice(int movieId);

    ArrayList<HashMap.Entry<Movie, Double>> queryBoxOfficeOrderDesc();

    ArrayList<HashMap.Entry<Cinema, Double>> queryBoxOfficeOfCinema();

    ArrayList<Ticket> queryTicketBySession(int sessionId);


    ArrayList<TicketPerfect>  showTicketBySession(int sessionId);


}
