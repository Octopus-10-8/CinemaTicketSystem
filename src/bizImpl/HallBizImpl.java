package bizImpl;

import biz.HallBiz;
import biz.TicketBiz;
import dao.*;
import daoImpl.*;
import entity.*;
import utils.Utils;


import java.util.ArrayList;

/**
 * Date: 2018/12/26 0026
 **/
public class HallBizImpl implements HallBiz {

    private HallDao hallDao = new HallDaoImpl();  //持有场厅Dao层对象（场厅关联电影院）
    private CinemaDao cinemaDao = new CinemaDaoImpl();//持有电影院对象
    private SessionDao sessionDao = new SessionDaoImpl();
    private TicketDao ticketDao = new TicketDaoImpl();
    private TicketBiz ticketBiz = new TicketBizImpl();
    private UserDao userDao = new UserDaoImpl();


    /**
     * 添加场厅，注意在同一个影院里场厅的名字不能相同
     *
     * @param hall
     * @return
     */
    @Override
    public boolean save(Hall hall) {
        //先判断hall所对应的电影院ID是否存在
        ArrayList<Cinema> cinemas = cinemaDao.queryCinema();
        if (hall == null) {
            return false;
        }
        for (Cinema cinema : cinemas) {
            if (hall.getCid() == cinema.getId()) {
                //判断名字重复
                ArrayList<HallPerfect> hallPerfects = queryHallByCid(cinema.getId());
                for (HallPerfect hallPerfect : hallPerfects) {
                    if (hallPerfect.getHall().getName().equals(hall.getName())) {
                        //名字重复的失败
                        return false;
                    }
                }
                //且传来的对象不为空，
                hallDao.save(hall);
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<HallPerfect> queryHall() {
        ArrayList<Hall> halls = hallDao.queryHall();
        ArrayList<HallPerfect> hallAndCinema = new ArrayList<>();
        //由于在添加的时候，电影院ID已经限制死了必须判断是否存在
        // 所以这里查询的时候不要判断ID查询的电影院是否为空
        for (Hall hall : halls) {
            Cinema cinema = cinemaDao.queryCinemaById(hall.getCid());
            HallPerfect temp = new HallPerfect(cinema, hall);
            hallAndCinema.add(temp);
        }
        return hallAndCinema;
    }

    @Override
    public boolean updateHall(Hall hall) {
        //更新同理，在处理cid的时候必须加以判断是否存在
        ArrayList<Cinema> cinemas = cinemaDao.queryCinema();
        for (Cinema cinema : cinemas) {
            if (cinema.getId() == hall.getCid()) {
                hallDao.updateHall(hall);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteHall(int hallId) {
        //先判断ID是否存在
        Hall hall = hallDao.queryHallByID(hallId);
        if (hall == null) {
            return false;
        }
        //1：查询下面的场次
        ArrayList<Session> sessionArrayList = sessionDao.querySession();
        for (Session session : sessionArrayList) {
            if (session.getHid() == hallId) {
                //2:找到下面的影票
                //2:如果该电影对应的场次已经上映过了，则直接删除场次对应的票，然后删除场次
                if (session.getTime().compareTo(Utils.getTime()) < 0) {
                    //获取对应的影票，然后删除
                    ArrayList<Ticket> tickets = ticketBiz.queryTicketBySession(session.getId());
                    for (Ticket ticket : tickets) {
                        ticketDao.deleteTicket(ticket.getId());
                    }
                } else {
                    //3：如果该电影的场次未上映的话，需要给相关用户退款(即相反条件)
                    //获取对应的影票，然后删除
                    ArrayList<Ticket> tickets = ticketBiz.queryTicketBySession(session.getId());
                    for (Ticket ticket : tickets) {
                        //找到用户
                        User user = userDao.queryByUserID(ticket.getUid());
                        //退款
                        user.setBalance(user.getBalance() + session.getPrice());
                        //更新进入数据库
                        userDao.updateUser(user);
                        //删除影票
                        ticketDao.deleteTicket(ticket.getId());
                    }
                }
                //删场次
                sessionDao.deleteSession(session.getId());
            }
        }
        hallDao.deleteHall(hallId);
        return true;
    }

    @Override
    public Hall queryHallByID(int hallId) {
        Hall hall = hallDao.queryHallByID(hallId);
        if (hall == null) {
            return null;
        }
        return hall;
    }

    @Override
    public ArrayList<HallPerfect> queryHallByCid(int cid) {
        ArrayList<Hall> halls = hallDao.queryHall();
        ArrayList<HallPerfect> list = new ArrayList<>();
        for (Hall hall : halls) {
            if (hall.getCid() == cid) {
                Cinema cinema = cinemaDao.queryCinemaById(cid);
                Hall hall1 = hallDao.queryHallByID(hall.getId());
                list.add(new HallPerfect(cinema, hall));
            }
        }

        return list;
    }
}
