package entity;

/**
 * 场厅关联表
 *
 *
 **/
public class HallPerfect {

    private Cinema cinema;   //影院
    private Hall hall;       //场厅


    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    /**
     * 电影院-场厅关联查询
     */
    public HallPerfect(Cinema cinema, Hall hall) {
        this.cinema = cinema;
        this.hall = hall;
    }

    @Override
    public String toString() {
        return "场厅明细{场厅ID=[" + hall.getId() + "]\t场厅名称=[" + hall.getName() + "]\t电影院名称" +
                "=[" + cinema.getName() + "]\t场厅容量=[" + hall.getCapacity() + "]}";
    }
}
