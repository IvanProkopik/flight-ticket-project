package org.example.service;

import org.example.dao.FlightDao;
import org.example.dto.FlightDto;
import org.example.entity.Flight;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class FlightService {
    private static final FlightService INSTANCE = new FlightService();
    private static final FlightDao flightDao = FlightDao.getInstance();

    private FlightService() {

    }

    public static FlightService getInstance() {
        return INSTANCE;
    }

    public List<FlightDto> findAll() {
        return flightDao.findAll().stream()
                .map(this::buildFlightDto)
                .toList();
    }

    public Optional<Flight> findById(Long id) {
        return flightDao.findById(id);
    }

    public boolean delete(Long id) {
        return flightDao.delete(id);
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

    public Flight save(Flight flight) {
        return flightDao.save(flight);
    }

    public void update(Flight flight){
        flightDao.update(flight);
    }
}
