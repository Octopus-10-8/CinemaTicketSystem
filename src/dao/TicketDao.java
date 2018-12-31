package dao;

import entity.Hall;
import entity.Ticket;

import java.util.ArrayList;

public interface TicketDao {

    void save(Ticket ticket);

    ArrayList<Ticket> queryTicket();

    void updateTicket(Ticket ticket);

    void deleteTicket(int ticketsId);

    Ticket queryTicketByID(int ticketsId);


}
