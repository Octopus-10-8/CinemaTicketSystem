package daoImpl;

import dao.TicketDao;
import entity.Ticket;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Date: 2018/12/26 0026
 **/
public class TicketDaoImpl extends BaseDao implements TicketDao {
    public TicketDaoImpl() {
        super(new File("Ticket.txt"));
    }

    /**
     * 添加影票
     * @param ticket
     */
    @Override
    public void save(Ticket ticket) {
        ArrayList<Ticket> read = read();
        if (read.size() == 0) {
            ticket.setId(1);
        } else {
            ticket.setId(read.get(read.size() - 1).getId() + 1);
        }
        read.add(ticket);
        write(read);
        closeAll();
    }

    /**
     * 查询影票
     * @return
     */
    @Override
    public ArrayList<Ticket> queryTicket() {
        ArrayList<Ticket> read = read();
        return read;
    }

    /**
     * 修改影票
     * @param ticket
     */
    @Override
    public void updateTicket(Ticket ticket) {
        ArrayList<Ticket> read = read();
        for (int i = 0; i < read.size(); i++) {
            if (read.get(i).getId() == ticket.getId()) {
                read.set(i, ticket);
            }

        }
        write(read);
        closeAll();

    }

    /**
     * 删除影票
     * @param ticketsId
     */
    @Override
    public void deleteTicket(int ticketsId) {
        ArrayList<Ticket> read = read();
        Iterator<Ticket> iterator = read.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId() == ticketsId) {
                iterator.remove();
            }
        }
        write(read);
        closeAll();
    }

    /**
     * 根据ID查询影票
     * @param ticketsId
     * @return
     */
    @Override
    public Ticket queryTicketByID(int ticketsId) {
        ArrayList<Ticket> read = read();
        for (Ticket ticket : read) {
            if (ticket.getId() == ticketsId) {
                return ticket;
            }
        }return null;
    }
}
