package org.example.dto;

import java.math.BigDecimal;


public record TicketDto(
        Long id,
        Long flightId,
        String seatNo,
        String passengerName,
        BigDecimal cost) {
}
