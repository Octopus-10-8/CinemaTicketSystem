package entity;

import utils.Utils;

/**
 * Date: 2018/12/26 0026
 **/
public class SessionPerfect {

    private Session session;
    private Hall hall;
    private Cinema cinema;
    private Movie movie;

    @Override
    public String toString() {
        return "场次{场次编号=[" +session.getId()+"]\t场厅=["+hall.getName()+"]\t电影院=["+cinema.getName()+
                "]\t电影名=["+movie.getName()+"]\t播放时间=["+session.getTime()+"]\t时长=["+movie.getDuration()+
                "分钟]\t价格=["+ Utils.df.format(session.getPrice())+"元]\t剩余票数=["+session.getRemain()+"张]}";

    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public SessionPerfect(Session session, Hall hall, Cinema cinema, Movie movie) {
        this.session = session;
        this.hall = hall;
        this.cinema = cinema;
        this.movie = movie;
    }
}
