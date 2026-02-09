package org.example.service;

import org.example.dao.TicketDao;
import org.example.dto.TicketDto;
import org.example.entity.Ticket;

import java.util.List;

public class TicketService {
    private static final  TicketService INSTANCE = new TicketService();
    private static final TicketDao ticketDao = TicketDao.getInstance();

    private TicketService(){

    }

    public static TicketService getInstance(){
        return INSTANCE;
    }

    public List<TicketDto> findAllByFlightId(Long flightId){
        return ticketDao.findAllByFlightId(flightId).stream()
                .map(this::buildTicketDto)
                .toList();
    }

    private TicketDto buildTicketDto(Ticket ticket) {
        return new TicketDto(
                ticket.getId(),
                ticket.getFlightId(),
                ticket.getSeatNo(),
                ticket.getPassengerName(),
                ticket.getCost()
        );
    }

}
