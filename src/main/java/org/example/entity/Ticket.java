package org.example.entity;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class Ticket {
    private Long id;
    private String passengerNo;
    private String passengerName;
    private Long flightId;
    private String seatNo;
    private BigDecimal cost;
}
