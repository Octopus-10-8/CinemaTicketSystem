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
    //添加票
    boolean addTickets(Ticket ticket);

    //查询票
    ArrayList<TicketPerfect> queryTicket();

    //修改票
    boolean updateTicket(Ticket ticket);

    //删除票
    boolean deleteTicket(int ticketsId);

    //查询单个票
    Ticket queryTicketByID(int ticketsId);

    //查询单个票的完整信息
    TicketPerfect queryTicketPerfectById(int id);

    //根据场次获取座位情况
    ArrayList<String> getSeatBySessionId(int sessionId);

    //根据用户查询购票情况
    ArrayList<TicketPerfect> queryTicketByuserId(int userID);

    //获取某部票房
    HashMap<Movie, Double> queryBoxOffice();

    //获取一个电影的票房
    HashMap<Double, Movie> queryOneBoxOffice(int movieId);

    //电影票房降序排列
    ArrayList<HashMap.Entry<Movie, Double>> queryBoxOfficeOrderDesc();

    //影院票房排列
    ArrayList<HashMap.Entry<Cinema, Double>> queryBoxOfficeOfCinema();

    //根据场次获取购票信息
    ArrayList<Ticket> queryTicketBySession(int sessionId);

    //根据场次显示该购票情况
    ArrayList<TicketPerfect> showTicketBySession(int sessionId);


}
