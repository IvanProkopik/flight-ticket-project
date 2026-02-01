package org.example.dto;

import java.util.Objects;

public class FlightDto {
    private final Long id;
    private final String description;

    public FlightDto(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof FlightDto)) return false;

        FlightDto flightDto = (FlightDto) object;
        return Objects.equals(id, flightDto.id) && Objects.equals(description, flightDto.description);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(description);
        return result;
    }

    @Override
    public String toString() {
        return "FlightDto{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
