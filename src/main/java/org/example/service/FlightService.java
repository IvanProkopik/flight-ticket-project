package org.example.service;

import org.example.dao.FlightDao;
import org.example.dto.FlightDto;
import org.example.entity.Flight;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class FlightService {
    private static final FlightService INSTANCE = new FlightService();
    private static final FlightDao flightDao = FlightDao.getInstance();

    private FlightService(){

    }

    public static FlightService getInstance(){
        return INSTANCE;
    }

    public List<FlightDto> findAll(){
        return flightDao.findAll().stream()
                .map(this::buildFlightDto)
                .toList();
    }

    private FlightDto buildFlightDto(Flight flight) {
        String description = String.format(
                "%s%s -> %s%s",
                flight.getFlightNo(),
                flight.getDepartureAirportCode(),
                flight.getArrivalAirportCode(),
                flight.getDepartureDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        );
        return new FlightDto(flight.getId(), description);
    }

}
