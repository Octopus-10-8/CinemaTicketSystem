package entity;

/**
 * 包含所有的实体类，用于提供所有的多表联查显示功能
 * <p>
 * 根据不同的需求来舍设计不同的显示方法，多种组合来提供解决方案
 **/
public class HallPerfect {

    private Cinema cinema;
    private Hall hall;


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
