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

    @Override
    public ArrayList<Ticket> queryTicket() {
        ArrayList<Ticket> read = read();
        return read;
    }

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
