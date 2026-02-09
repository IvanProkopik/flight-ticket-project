package org.example.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class TicketDto {
    private final Long id;
    private final Long flightId;
    private final String seatNo;
    private final String passengerName;
    private final BigDecimal cost;

    public TicketDto(Long id, Long flightId, String seatNo, String passengerName, BigDecimal cost) {
        this.id = id;
        this.flightId = flightId;
        this.seatNo = seatNo;
        this.passengerName = passengerName;
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }

    public Long getFlightId() {
        return flightId;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public BigDecimal getCost() {
        return cost;
    }

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof TicketDto ticketDto)) return false;

        return Objects.equals(id, ticketDto.id) && Objects.equals(flightId, ticketDto.flightId) && Objects.equals(seatNo, ticketDto.seatNo) && Objects.equals(passengerName, ticketDto.passengerName) && Objects.equals(cost, ticketDto.cost);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(flightId);
        result = 31 * result + Objects.hashCode(seatNo);
        result = 31 * result + Objects.hashCode(passengerName);
        result = 31 * result + Objects.hashCode(cost);
        return result;
    }

    @Override
    public String toString() {
        return "TicketDto{" +
                "id=" + id +
                ", flightId=" + flightId +
                ", seatNo='" + seatNo + '\'' +
                ", passengerName='" + passengerName + '\'' +
                ", cost=" + cost +
                '}';
    }
}
