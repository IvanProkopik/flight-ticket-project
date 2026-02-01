package org.example.dto;

import java.util.Objects;

public class TicketDto {
    private final Long id;
    private final Long flightId;
    private final String seatNo;

    public TicketDto(Long id, Long flightId, String seatNo) {
        this.id = id;
        this.flightId = flightId;
        this.seatNo = seatNo;
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

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof TicketDto)) return false;

        TicketDto ticketDto = (TicketDto) object;
        return Objects.equals(id, ticketDto.id) && Objects.equals(flightId, ticketDto.flightId) && Objects.equals(seatNo, ticketDto.seatNo);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(flightId);
        result = 31 * result + Objects.hashCode(seatNo);
        return result;
    }

    @Override
    public String toString() {
        return "TicketDto{" +
                "id=" + id +
                ", flightId=" + flightId +
                ", seatNo='" + seatNo + '\'' +
                '}';
    }
}
