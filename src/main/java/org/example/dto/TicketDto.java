package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class TicketDto {
    private final Long id;
    private final Long flightId;
    private final String seatNo;
    private final String passengerName;
    private final BigDecimal cost;

}
