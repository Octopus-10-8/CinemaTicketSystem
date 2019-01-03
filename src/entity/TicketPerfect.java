package entity;


import utils.Utils;

/**
 *影票中间关联表
 **/
public class TicketPerfect {

    private Ticket ticket;   //影票
    private User user;   //用户
    private Session session;  //场次
    private Movie movie;  //电影


    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Override
    public String toString() {
        return "万达影票{\t影票ID[" + ticket.getId() + "]\t电影名称=[" + movie.getName() + "]\t用户账号=[" + user.getName() + "]\t场次=[" + session.getTime() + "]" +
                "\t座位=[" + Utils.seatFormat(ticket.getSeat()) + "]}";


    }

    public TicketPerfect(Ticket ticket, User user, Session session) {
        this.ticket = ticket;
        this.user = user;
        this.session = session;
    }

    public TicketPerfect(Ticket ticket, User user, Session session, Movie movie) {
        this.ticket = ticket;
        this.user = user;
        this.session = session;
        this.movie = movie;
    }
}
