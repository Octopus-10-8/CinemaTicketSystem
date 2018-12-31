package bizImpl;

import biz.CinemaBiz;
import biz.SessionBiz;
import biz.TicketBiz;
import dao.*;
import daoImpl.*;
import entity.*;
import utils.Utils;

import java.util.ArrayList;

/**
 * Date: 2018/12/26 0026
 **/
public class SessionBizImpl implements SessionBiz {
    private SessionDao sessionDao = new SessionDaoImpl();
    private CinemaDao cinemaDao = new CinemaDaoImpl();
    private HallDao hallDao = new HallDaoImpl();
    private MovieDao movieDao = new MovieDaoImpl();
    private CinemaBiz cinemaBiz = new CinemaBizImpl();
    private UserDao userDao = new UserDaoImpl();
    private TicketDao ticketDao = new TicketDaoImpl();
    private CommentsDao commentsDao = new CommentsDaoImpl();
    private TicketBiz ticketBiz = new TicketBizImpl();

    @Override
    public boolean addSession(Session session) {
        Cinema cinema = cinemaDao.queryCinemaById(session.getCid());
        if (cinema == null) return false;
        //1：对应的场厅必须存在  此场厅必须属于后面的影院
        Hall hall = hallDao.queryHallByID(session.getHid());
        ArrayList<HallPerfect> hallPerfects = cinemaBiz.queryHallByCid(cinema.getId());
        boolean flag = false;
        for (HallPerfect hallPerfect : hallPerfects) {
            if (hallPerfect.getHall().getId() == session.getHid()) {
                flag = true;

            }
        }
        if (flag == false) return false;
        //3:对应的电影必须存在
        Movie movie = movieDao.queryById(session.getMmovie_id());
        if (movie == null) return false;
        /**
         * 如果没有数据的话，直接存储
         */
        if (querySession().size() == 0) {
            sessionDao.save(session);
            return true;
        }
        //查询当前session对象的多表联系信息,并保存一个String的时间对象（一个是开始时间，一个是结束时间）
        String currentEnd = Utils.scheduljudge(session.getTime(), movie.getDuration());//当前存储场次的结束时间
        String currendStart = session.getTime(); //开始时间

        //根据场厅查询该场厅对应的所有场次信息
        ArrayList<SessionPerfect> sessionPerfects = queryByHid(session.getHid());
        for (SessionPerfect sessionPerfect : sessionPerfects) {
            //4:遍历场次必须保证不能冲突
            //当前添加的场次开始时间必须大于某个对象的开始时间+时长+15分钟间隔
            //在满足上面的基础上场次结束时间必须小于某个对象的开始时间
            //不用判断满足，对立条件不满足就返回false
            String objEnd = Utils.scheduljudge(sessionPerfect.getSession().getTime(), sessionPerfect.getMovie().getDuration() + 15);  //比较的对象
            String objStart = sessionPerfect.getSession().getTime();

            if (objEnd.compareTo(currentEnd) > 0 && objStart.compareTo(currentEnd) < 0) {

                return false;
            }
            if (objStart.compareTo(currendStart) < 0 && objEnd.compareTo(currendStart) > 0) {

                return false;
            }
        }
        sessionDao.save(session);
        return true;
    }

    @Override
    public ArrayList<SessionPerfect> querySession() {
        ArrayList<Session> sessions = sessionDao.querySession();
        ArrayList<SessionPerfect> sessionPerfects = new ArrayList<>();
        for (Session session : sessions) {
            //查询场厅对象
            Hall hall = hallDao.queryHallByID(session.getHid());
            //查询对应的电影对象
            Movie movie = movieDao.queryById(session.getMmovie_id());
            //查询对应的电影院对象
            Cinema cinema = cinemaDao.queryCinemaById(session.getCid());
            SessionPerfect sessionPerfect = new SessionPerfect(session, hall, cinema, movie);
            sessionPerfects.add(sessionPerfect);
        }

        return sessionPerfects;
    }

    /**
     * 修改可以借用添加的代码思想，因为如果修改的元素可以添加，说明他是满足于修改的，只是添加的位置是指覆盖而已
     *
     * @param session（包括时间的修改）
     * @return
     */
    @Override
    public boolean updateSession(Session session) {

        Session s = sessionDao.querySessionByID(session.getId());
        if (s == null) {
            return false;  //输入的场次为空，失败
        }
        Cinema cinema = cinemaDao.queryCinemaById(session.getCid());
        if (cinema == null) return false;
        //1：对应的场厅必须存在  此场厅必须属于后面的影院
        Hall hall = hallDao.queryHallByID(session.getHid());
        ArrayList<HallPerfect> hallPerfects = cinemaBiz.queryHallByCid(cinema.getId());
        boolean flag = false;
        for (HallPerfect hallPerfect : hallPerfects) {
            if (hallPerfect.getHall().getId() == session.getHid()) {
                flag = true;

            }
        }
        if (flag == false) return false;
        //3:对应的电影必须存在
        Movie movie = movieDao.queryById(session.getMmovie_id());
        if (movie == null) return false;
        /**
         * 如果没有数据的话，直接存储
         */
        //查询当前session对象的多表联系信息,并保存一个String的时间对象（一个是开始时间，一个是结束时间）
        String currentEnd = Utils.scheduljudge(session.getTime(), movie.getDuration());//当前存储场次的结束时间
        String currendStart = session.getTime(); //开始时间

        //根据场厅查询该场厅对应的所有场次信息
        ArrayList<SessionPerfect> sessionPerfects = queryByHid(session.getHid());
        for (SessionPerfect sessionPerfect : sessionPerfects) {
            //4:遍历场次必须保证不能冲突
            //当前添加的场次开始时间必须大于某个对象的开始时间+时长+15分钟间隔
            //在满足上面的基础上场次结束时间必须小于某个对象的开始时间
            //不用判断满足，对立条件不满足就返回false
            String objEnd = Utils.scheduljudge(sessionPerfect.getSession().getTime(), sessionPerfect.getMovie().getDuration() + 15);  //比较的对象
            String objStart = sessionPerfect.getSession().getTime();
            if (objEnd.compareTo(currentEnd) > 0 && objStart.compareTo(currentEnd) < 0) {

                return false;
            }
            if (objStart.compareTo(currendStart) < 0 && objEnd.compareTo(currendStart) > 0) {

                return false;
            }
        }
        sessionDao.updateSession(session);
        return true;
    }

    /**
     * 场次表中不涉及时间的修改
     *
     * @param session
     * @return
     */
    @Override
    public boolean updateSessionHasNoTime(Session session) {
        Session s = sessionDao.querySessionByID(session.getId());
        if (s == null) {
            return false;
        }
        sessionDao.updateSession(session);
        return true;
    }

    @Override
    public boolean deleteSession(int sessionId) {
        Session session = sessionDao.querySessionByID(sessionId);
        if (session == null) {
            return false;
        }
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
        sessionDao.deleteSession(sessionId);
        return true;

    }

    @Override
    public Session querySessionByID(int sessionId) {
        ArrayList<Session> sessions = sessionDao.querySession();
        for (Session s : sessions) {
            if (s.getId() == sessionId) {
                return s;
            }
        }
        return null;
    }

    @Override
    public ArrayList<SessionPerfect> queryByHid(int hallId) {
        //先查询所有的场次，然后在筛选出根场厅ID对应的所有的数据
        ArrayList<Session> sessions = sessionDao.querySession();
        ArrayList<SessionPerfect> sessionPerfects = new ArrayList<>();
        for (Session session : sessions) {
            if (session.getHid() == hallId) {
                //查询对应的场次对象
                Hall hall = hallDao.queryHallByID(hallId);
                //查询对应的电影对象
                Movie movie = movieDao.queryById(session.getMmovie_id());
                //查询对应的电影院对象
                Cinema cinema = cinemaDao.queryCinemaById(session.getCid());
                SessionPerfect sessionPerfect = new SessionPerfect(session, hall, cinema, movie);
                sessionPerfects.add(sessionPerfect);
            }
        }
        return sessionPerfects;
    }

    @Override
    public SessionPerfect queryPerfectBySeesion(int sessionId) {
        ArrayList<Session> sessions = sessionDao.querySession();
        for (Session session : sessions) {
            if (session.getHid() == sessionId) {
                //查询对应的场次对象
                Hall hall = hallDao.queryHallByID(session.getHid());
                //查询对应的电影对象
                Movie movie = movieDao.queryById(session.getMmovie_id());
                //查询对应的电影院对象
                Cinema cinema = cinemaDao.queryCinemaById(session.getCid());
                SessionPerfect sessionPerfect = new SessionPerfect(session, hall, cinema, movie);
                return sessionPerfect;
            }
        }


        return null;
    }

    /**
     * 根据电影院ID查询下面的所有场次
     *
     * @param cid
     * @return
     */
    @Override
    public ArrayList<SessionPerfect> querySessionByCid(int cid, int mid) {
        ArrayList<SessionPerfect> sessionPerfects = querySession();
        ArrayList<SessionPerfect> perfectsForCid = new ArrayList<>();
        for (SessionPerfect sessionPerfect : sessionPerfects) {
            if (sessionPerfect.getCinema().getId() == cid && sessionPerfect.getMovie().getId() == mid) {
                perfectsForCid.add(sessionPerfect);
            }
        }
        return perfectsForCid;
    }
}
