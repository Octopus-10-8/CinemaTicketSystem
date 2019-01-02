package bizImpl;

import biz.CinemaBiz;
import biz.TicketBiz;
import dao.*;
import daoImpl.*;
import entity.*;
import utils.Utils;

import java.util.ArrayList;

/**
 * Date: 2018/12/25 0025
 **/
public class CinemaBizImpl implements CinemaBiz {

    private CinemaDao cinemaDao = new CinemaDaoImpl();
    private HallDao hallDao = new HallDaoImpl();
    private SessionDao sessionDao = new SessionDaoImpl();
    private TicketDao ticketDao = new TicketDaoImpl();
    private TicketBiz ticketBiz = new TicketBizImpl();
    private UserDao userDao = new UserDaoImpl();

    /**
     * 添加影院
     *
     * @param cinema 电影对象
     * @return boolean
     */
    @Override
    public boolean addCinema(Cinema cinema) {
        //如果公司名已被注册则不能添加
        ArrayList<Cinema> cinemas = queryCinema();
        for (Cinema c : cinemas) {
            if (c.getName().equals(cinema.getName())) {
                return false;
            }
        }
        cinemaDao.save(cinema);
        return true;
    }

    /**
     * 查询所有的电影院
     *
     * @return
     */
    @Override
    public ArrayList<Cinema> queryCinema() {
        return cinemaDao.queryCinema();
    }

    /**
     * 更新电影院信息
     * 影院的修改[影院名称、影院地址] 就俩个字段，这俩个都是和其他不关联的，可以直接修改
     *
     * @param cinema
     * @return
     */
    @Override
    public boolean updateCinema(Cinema cinema) {
        //如果不存在则返回失败
        ArrayList<Cinema> cinemas = queryCinema();
        for (Cinema c : cinemas) {
            if (c.getId() == cinema.getId()) {
                cinemaDao.updateCinema(cinema);
                return true;
            }
        }
        return false;
    }


    /**
     * 从网站中移除某个电影院
     *
     * @param cinemaId
     * @return
     */
    @Override
    public boolean deleteCinema(int cinemaId) {
        Cinema cinema = cinemaDao.queryCinemaById(cinemaId);
        if (cinema == null) {
            return false;
        }
        //关联四表：影院表、场厅表、场次表、影票表
        //1:查询场厅
        ArrayList<Hall> halls = hallDao.queryHall();
        for (Hall hall : halls) {
            if (hall.getCid() == cinemaId) {
                //查询场次，分已上映和未上映
                ArrayList<Session> sessionArrayList = sessionDao.querySession();
                for (Session session : sessionArrayList) {
                    if (session.getHid() == hall.getId()) {
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
                //删场厅
                hallDao.deleteHall(hall.getId());
            }
        }
        cinemaDao.deleteCinema(cinemaId);
        return true;
    }

    /**
     * 根据地址关键字查询电影院
     *
     * @param address
     * @return
     */
    @Override
    public ArrayList<Cinema> queryCinemaByAddress(String address) {
        ArrayList<Cinema> cinemas = queryCinema();
        ArrayList<Cinema> ciAddress = new ArrayList<>();
        for (Cinema cinema : cinemas) {
            if (cinema.getAddress().contains(address)) {
                ciAddress.add(cinema);
            }
        }
        return ciAddress;
    }

    /**
     * 根据ID查询影院
     *
     * @param cinemaId
     * @return
     */
    @Override
    public Cinema queryById(int cinemaId) {
        ArrayList<Cinema> cinemas = queryCinema();
        for (Cinema cinema : cinemas) {
            if (cinema.getId() == cinemaId) {
                return cinema;
            }
        }
        return null;
    }

    /**
     * 根据影院ID查询所有的场厅
     *
     * @param cid
     * @return
     */
    @Override
    public ArrayList<HallPerfect> queryHallByCid(int cid) {
        ArrayList<Hall> halls = hallDao.queryHall();
        ArrayList<HallPerfect> res = new ArrayList<>();
        for (Hall hall : halls) {
            if (hall.getCid() == cid) {
                Cinema cinema = cinemaDao.queryCinemaById(cid);
                HallPerfect hallPerfect = new HallPerfect(cinema, hall);
                res.add(hallPerfect);
            }
        }
        return res;
    }

}
