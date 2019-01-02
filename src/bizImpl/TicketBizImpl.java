package bizImpl;

import biz.CinemaBiz;
import biz.TicketBiz;
import dao.*;
import daoImpl.*;
import entity.*;

import java.util.*;

/**
 * Date: 2018/12/26 0026
 **/
public class TicketBizImpl implements TicketBiz {

    private TicketDao ticketDao = new TicketDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private SessionDao sessionDao = new SessionDaoImpl();
    private MovieDao movieDao = new MovieDaoImpl();
    private CinemaDao cinemaDao = new CinemaDaoImpl();

    /**
     * 添加影票
     *
     * @param ticket
     * @return
     */
    @Override
    public boolean addTickets(Ticket ticket) {
        //查询对应的用户是否存在
        User user = userDao.queryByUserID(ticket.getUid());
        if (user == null) {
            return false;
        }
        //查询对应的场次是否存在
        Session session = sessionDao.querySessionByID(ticket.getSid());
        if (session == null) {
            return false;
        }
        ticketDao.save(ticket);
        return true;
    }

    /**
     * 查询影票
     *
     * @return
     */
    @Override
    public ArrayList<TicketPerfect> queryTicket() {
        ArrayList<Ticket> tickets = ticketDao.queryTicket();
        ArrayList<TicketPerfect> ticketPerfectArrayList = new ArrayList<>();
        for (Ticket ticket : tickets) {
            //查询用户信息
            User user = userDao.queryByUserID(ticket.getUid());
            //查询场次信息
            Session session = sessionDao.querySessionByID(ticket.getSid());
            //查询对应的电影信息
            Movie movie = movieDao.queryById(ticket.getMovieId());
            TicketPerfect ticketPerfect = new TicketPerfect(ticket, user, session, movie);
            ticketPerfectArrayList.add(ticketPerfect);
        }
        return ticketPerfectArrayList;

    }

    /**
     * 根据ID查询影票
     *
     * @param id
     * @return
     */
    @Override
    public TicketPerfect queryTicketPerfectById(int id) {
        ArrayList<TicketPerfect> ticketPerfectArrayList = queryTicket();
        for (TicketPerfect ticketPerfect : ticketPerfectArrayList) {
            if (ticketPerfect.getTicket().getId() == id) {
                return ticketPerfect;
            }
        }
        return null;
    }

    /**
     * 修改影票
     *
     * @param ticket
     * @return
     */
    @Override
    public boolean updateTicket(Ticket ticket) {
        //判断对象是否为空，是否存在
        if (ticket == null) return false;
        Ticket ticket1 = queryTicketByID(ticket.getId());
        if (ticket1 == null) return false;
        ticketDao.updateTicket(ticket);
        return true;
    }

    /**
     * 删除影票
     *
     * @param ticketsId
     * @return
     */
    @Override
    public boolean deleteTicket(int ticketsId) {
        //判断对象是否为空，是否存在
        Ticket ticket1 = queryTicketByID(ticketsId);
        if (ticket1 == null) return false;
        ticketDao.deleteTicket(ticketsId);
        return true;
    }

    /**
     * 查询单个影票
     *
     * @param ticketsId
     * @return
     */
    @Override
    public Ticket queryTicketByID(int ticketsId) {
        ArrayList<Ticket> tickets = ticketDao.queryTicket();
        for (Ticket ticket : tickets) {
            if (ticket.getId() == ticketsId) {
                return ticket;
            }
        }
        return null;
    }

    /**
     * 根据场次在影票表中查询所有的座位信息，以字符串的形式保存在集合中
     *
     * @param sessionId
     * @return
     */
    @Override
    public ArrayList<String> getSeatBySessionId(int sessionId) {
        //首先查询所有的影票
        ArrayList<TicketPerfect> ticketPerfectArrayList = queryTicket();
        //在影票集合中查询出对应sessionId的信息集合
        //与此同时，在符合条件的场次ID中添加对应的座位号到集合中
        ArrayList<String> arrayList = new ArrayList<>();
        for (TicketPerfect ticketPerfect : ticketPerfectArrayList) {
            if (ticketPerfect.getSession().getId() == sessionId) {
                arrayList.add(ticketPerfect.getTicket().getSeat());
            }
        }
        return arrayList;
    }

    /**
     * 根据用户查询对应的影票
     *
     * @param userID
     * @return
     */
    @Override
    public ArrayList<TicketPerfect> queryTicketByuserId(int userID) {
        ArrayList<TicketPerfect> ticketPerfectArrayList = queryTicket();
        ArrayList<TicketPerfect> ticketForUser = new ArrayList<>();
        for (TicketPerfect ticketPerfect : ticketPerfectArrayList) {
            if (ticketPerfect.getUser().getId() == userID) {
                ticketForUser.add(ticketPerfect);
            }
        }

        return ticketForUser;
    }

    /**
     * 查询所有票房纪录
     * 统计所有卖掉的票的记录，然后匹配对应的电影ID，再根据对应电影的价格进行计算的。
     *
     * @return
     */
    @Override
    public HashMap<Movie, Double> queryBoxOffice() {
        ArrayList<TicketPerfect> ticketPerfectArrayList = queryTicket();
        HashMap<Movie, Double> boxOffice = new HashMap<>();
        for (TicketPerfect ticketPerfect : ticketPerfectArrayList) {
            if (boxOffice.containsKey(ticketPerfect.getMovie())) {
                boxOffice.replace(ticketPerfect.getMovie(), boxOffice.get(ticketPerfect.getMovie()) + ticketPerfect.getSession().getPrice());
            } else {
                boxOffice.put(ticketPerfect.getMovie(), ticketPerfect.getSession().getPrice());
            }
        }
        return boxOffice;
    }

    /**
     * 查询所有电影票房降序排列
     * <p>
     * 直接调用上面的方法然后将该集合放入Arraylist中进行排序
     *
     * @return
     */
    public ArrayList<HashMap.Entry<Movie, Double>> queryBoxOfficeOrderDesc() {
        HashMap<Movie, Double> movieDoubleHashMap = queryBoxOffice();
        ArrayList<HashMap.Entry<Movie, Double>> listForBoxOffice = new ArrayList<>(movieDoubleHashMap.entrySet());
        Collections.sort(listForBoxOffice, new Comparator<HashMap.Entry<Movie, Double>>() {
            @Override
            public int compare(Map.Entry<Movie, Double> o1, Map.Entry<Movie, Double> o2) {
                return Double.compare(o2.getValue(), o1.getValue());
            }
        });

        return listForBoxOffice;
    }


    @Override
    public HashMap<Double, Movie> queryOneBoxOffice(int movieId) {
        return null;
    }

    /**
     * 查询某个电影院的盈利
     * 先根据影票的sid场次查询场次对象，
     * 然后在根据场次查询对应的影院对象
     *
     * @return
     */
    @Override
    public ArrayList<HashMap.Entry<Cinema, Double>> queryBoxOfficeOfCinema() {
        ArrayList<TicketPerfect> ticketPerfectArrayList = queryTicket();
        HashMap<Cinema, Double> boxOffice = new HashMap<>();
        for (TicketPerfect ticketPerfect : ticketPerfectArrayList) {
            // 先根据影票的sid场次查询场次对象
            Session session = sessionDao.querySessionByID(ticketPerfect.getSession().getId());
            //然后在根据场次查询对应的影院对象
            Cinema cinema = cinemaDao.queryCinemaById(session.getCid());
            //设置Cinema根据ID判重
            if (boxOffice.containsKey(cinema)) {
                boxOffice.replace(cinema, boxOffice.get(cinema) + ticketPerfect.getSession().getPrice());
            } else {
                boxOffice.put(cinema, ticketPerfect.getSession().getPrice());
            }
        }
        //排序
        ArrayList<HashMap.Entry<Cinema, Double>> listForCinema = new ArrayList<>(boxOffice.entrySet());
        Collections.sort(listForCinema, new Comparator<HashMap.Entry<Cinema, Double>>() {
            @Override
            public int compare(Map.Entry<Cinema, Double> o1, Map.Entry<Cinema, Double> o2) {
                return Double.compare(o2.getValue(), o1.getValue());
            }
        });
        return listForCinema;
    }

    /**
     * 根据场次查询对应影票
     *
     * @param sessionId
     * @return
     */
    @Override
    public ArrayList<Ticket> queryTicketBySession(int sessionId) {
        ArrayList<Ticket> tickets = ticketDao.queryTicket();
        ArrayList<Ticket> ticketsForSession = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (ticket.getSid() == sessionId) {
                ticketsForSession.add(ticket);
            }
        }
        return ticketsForSession;
    }

    /**
     * 根据场次ID查询影票多表信息
     *
     * @param sessionId
     * @return
     */
    @Override
    public ArrayList<TicketPerfect> showTicketBySession(int sessionId) {
        ArrayList<Ticket> tickets = ticketDao.queryTicket();
        ArrayList<TicketPerfect> ticketsForSession = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (ticket.getSid() == sessionId) {
                User user = userDao.queryByUserID(ticket.getUid());
                Session session = sessionDao.querySessionByID(ticket.getSid());
                Movie movie = movieDao.queryById(ticket.getMovieId());
                TicketPerfect ticketPerfect = new TicketPerfect(ticket, user, session, movie);
                ticketsForSession.add(ticketPerfect);
            }
        }
        return ticketsForSession;
    }
}
