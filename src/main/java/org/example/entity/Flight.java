package org.example.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Flight {
    private Long id;
    private String flightNo;
    private LocalDateTime departureDate;
    private String departureAirportCode;
    private LocalDateTime arrivalDate;
    private String arrivalAirportCode;
    private Integer aircraftId;
    private FlightStatus status;

    public Flight(Long id, String flightNo, LocalDateTime departureDate,
                  String departureAirportCode, LocalDateTime arrivalDate,
                  String arrivalAirportCode, Integer aircraftId, FlightStatus status) {
        this.id = id;
        this.flightNo = flightNo;
        this.departureDate = departureDate;
        this.departureAirportCode = departureAirportCode;
        this.arrivalDate = arrivalDate;
        this.arrivalAirportCode = arrivalAirportCode;
        this.aircraftId = aircraftId;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public String getDepartureAirportCode() {
        return departureAirportCode;
    }

    public void setDepartureAirportCode(String departureAirportCode) {
        this.departureAirportCode = departureAirportCode;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getArrivalAirportCode() {
        return arrivalAirportCode;
    }

    public void setArrivalAirportCode(String arrivalAirportCode) {
        this.arrivalAirportCode = arrivalAirportCode;
    }

    public Integer getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(Integer aircraftId) {
        this.aircraftId = aircraftId;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public void setStatus(FlightStatus status) {
        this.status = status;
    }

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Flight)) return false;

        Flight flight = (Flight) object;
        return Objects.equals(id, flight.id) && Objects.equals(flightNo, flight.flightNo) && Objects.equals(departureDate, flight.departureDate) && Objects.equals(departureAirportCode, flight.departureAirportCode) && Objects.equals(arrivalDate, flight.arrivalDate) && Objects.equals(arrivalAirportCode, flight.arrivalAirportCode) && Objects.equals(aircraftId, flight.aircraftId) && status == flight.status;
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(flightNo);
        result = 31 * result + Objects.hashCode(departureDate);
        result = 31 * result + Objects.hashCode(departureAirportCode);
        result = 31 * result + Objects.hashCode(arrivalDate);
        result = 31 * result + Objects.hashCode(arrivalAirportCode);
        result = 31 * result + Objects.hashCode(aircraftId);
        result = 31 * result + Objects.hashCode(status);
        return result;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", flightNo='" + flightNo + '\'' +
                ", departureDate=" + departureDate +
                ", departureAirportCode='" + departureAirportCode + '\'' +
                ", arrivalDate=" + arrivalDate +
                ", arrivalAirportCode='" + arrivalAirportCode + '\'' +
                ", aircraftId=" + aircraftId +
                ", status=" + status +
                '}';
    }

}
