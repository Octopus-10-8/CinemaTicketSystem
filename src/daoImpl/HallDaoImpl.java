package daoImpl;

import dao.HallDao;
import entity.Hall;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Date: 2018/12/26 0026
 **/
public class HallDaoImpl extends BaseDao implements HallDao {

    public HallDaoImpl() {
        super(new File("hall.txt"));
    }

    @Override
    public void save(Hall hall) {

        ArrayList<Hall> read = read();
        if (read.size() == 0) {
            hall.setId(1);
        } else {
            hall.setId(read.get(read.size() - 1).getId() + 1);
        }
        read.add(hall);
        write(read);
        closeAll();
    }

    @Override
    public ArrayList<Hall> queryHall() {
        ArrayList<Hall> read = read();
        return read;
    }

    @Override
    public void updateHall(Hall hall) {
        ArrayList<Hall> read = read();
        for (int i = 0; i < read.size(); i++) {
            if (read.get(i).getId() == hall.getId()) {
                read.set(i, hall);
            }
        }
        write(read);
        closeAll();

    }

    @Override
    public void deleteHall(int hallId) {
        ArrayList<Hall> read = read();
        Iterator<Hall> iterator = read.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId() == hallId) {
                iterator.remove();
            }
        }
        write(read);
        closeAll();
    }

    @Override
    public Hall queryHallByID(int hallId) {
        ArrayList<Hall> halls = queryHall();
        for (Hall hall : halls) {
            if (hall.getId() == hallId) {
                return hall;
            }
        }
        return null;
    }
}
