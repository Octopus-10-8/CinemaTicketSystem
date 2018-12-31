package bizImpl;

import biz.MovieBiz;
import biz.SessionBiz;
import biz.TicketBiz;
import biz.UserBiz;
import dao.*;
import daoImpl.*;
import entity.*;
import utils.Utils;

import java.util.ArrayList;

/**
 * Date: 2018/12/25 0025
 **/
public class MovieBizImpl implements MovieBiz {

    private MovieDao movieDao = new MovieDaoImpl();
    private SessionDao sessionDao = new SessionDaoImpl();
    private SessionBiz sessionBiz = new SessionBizImpl();
    private TicketBiz ticketBiz = new TicketBizImpl();
    private UserBiz userBiz = new UserBizImpl();
    private UserDao userDao = new UserDaoImpl();
    private TicketDao ticketDao = new TicketDaoImpl();
    private CommentsDao commentsDao = new CommentsDaoImpl();
    private HallDao hallDao = new HallDaoImpl();


    @Override
    public boolean save(Movie movie) {
        ArrayList<Movie> movies = movieDao.queryMovie();
        for (Movie m : movies) {
            //电影可能存在重名的情况
            if (m.getName().equals(movie.getName())) {
                return false;
            }
        }
        movieDao.save(movie);
        return true;

    }

    @Override
    public ArrayList<Movie> queryMovie() {

        return movieDao.queryMovie();
    }

    /**
     * 修改电影
     * 电影名称	电影详情	电影时长	电影类型
     * 根据电影ID查询它对应的场次，然后在找到该场次对应的场厅，遍历该场厅的所有场次，如果有冲突则失败
     *
     * @param movie
     * @return
     */
    @Override
    public boolean updateMovie(Movie movie) {

        ArrayList<Movie> movies = queryMovie();
        for (Movie m : movies) {

            if (movie.getId() == m.getId()) {

                //重点关注时长的修改，如果时长修改了但会造成排班的混乱，则不能修改
                //查询场次
                ArrayList<Session> sessionArrayList = sessionDao.querySession();

                boolean flag = true;
                for (Session session : sessionArrayList) {

                    if (session.getMmovie_id() == movie.getId()) {

                        //根据场厅查询该场厅对应的所有场次信息
                        ArrayList<SessionPerfect> sessionPerfects = sessionBiz.queryByHid(session.getHid());
                        String currentEnd = Utils.scheduljudge(session.getTime(), movie.getDuration());//当前存储场次的结束时间


                        String currendStart = session.getTime(); //开始时间

                        for (SessionPerfect sessionPerfect : sessionPerfects) {
                            //修改的情况，跳过当前ID的场次(即跳过同一场厅的当前场次,即开始时间相同)
                            if (sessionPerfect.getSession().getTime().equals(currendStart)) {
                                continue;
                            }

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

                            //如果能走到这里说明这个是可以修改的，那么直接修改
                            flag = true;
                            break;

                        }


                    }
                }
                if (flag) {

                    movieDao.updateMovie(movie);
                    return true;
                } else {

                    return false;
                }

            }
        }
        //如果用户数入的ID不存在直接退出
        return false;
    }


    /**
     * //  如果删除（下架）某部电影的话，需要做如下几件事
     * //1：对应的所有未上映场次必须全部删除
     * //2：所有未上映的场次购票的用户需要退款
     *
     * @param movieId
     * @return
     */
    @Override
    public boolean deleteMovie(int movieId) {
        Movie movie = movieDao.queryById(movieId);
        if (movie == null) {
            return false;
        }
        //1:根据电影查询未播放场次
        ArrayList<Session> sessionArrayList = querySessionBymid(movieId);
        for (Session session : sessionArrayList) {
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
            sessionDao.deleteSession(session.getId());
        }
        //最后删除电影所对应的所有评论
        ArrayList<Comments> comments = commentsDao.queryComments();
        for (Comments comment : comments) {
            if (comment.getMovie_id() == movieId) {
                commentsDao.deleteComments(comment.getId());

            }
        }
        //最后删除电影
        movieDao.deleteMovie(movieId);
        return true;
    }

    //如果用户数入的ID不存在直接退出


    @Override
    public Movie queryById(int movieId) {
        ArrayList<Movie> movies = queryMovie();
        for (Movie m : movies) {
            if (m.getId() == movieId) {
                return m;
            }
        }
        return null;
    }


    @Override
    public ArrayList<Movie> queryByKey(String key) {
        ArrayList<Movie> moviesKey = new ArrayList<>();
        ArrayList<Movie> movies = queryMovie();
        for (Movie movie : movies) {
            if (movie.getDetail().contains(key) || movie.getName().contains(key) || movie.getType().contains(key)) {
                moviesKey.add(movie);
            }

        }
        return moviesKey;
    }


    @Override
    public ArrayList<Session> querySessionBymid(int movieId) {
        ArrayList<Session> sessions = sessionDao.querySession();
        ArrayList<Session> sessionArrayList = new ArrayList<>();
        for (Session session : sessions) {
            //对应的id匹配，且场次处于未放映状态
            if (session.getMmovie_id() == movieId) {
                sessionArrayList.add(session);
            }
        }
        return sessionArrayList;
    }
}
