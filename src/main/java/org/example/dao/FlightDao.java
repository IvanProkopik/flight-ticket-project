package org.example.dao;

import org.example.entity.Flight;
import org.example.entity.FlightStatus;
import org.example.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightDao implements Dao<Long, Flight> {

    private static final FlightDao INSTANCE = new FlightDao();

    //Знайти всі елементи і відсортувати по departure_date
    private static final String FIND_ALL = """
            SELECT *
            FROM flight
            ORDER BY departure_date
            """;

    //пошук за id
    private static final String FIND_BY_ID = """
            SELECT *
            FROM flight
            WHERE id = ?
            """;

    //Видалення за id
    private static final String DELETE_SQL = """
            DELETE FROM flight
            WHERE id = ?
            """;

    //Зміна всих рядків за ID
    private static final String UPDATE_SQL = """
             UPDATE flight
                        SET flight_no = ?,
                            departure_date = ?,
                            departure_airport_code = ?,
                            arrival_date = ?,
                            arrival_airport_code = ?,
                            aircraft_id = ?,
                            status = ?
                        WHERE id = ?
            """;

    //створення нового рейса
    private static final String SAVE_SQL = """
            INSERT INTO flight (flight_no, departure_date, departure_airport_code,
                               arrival_date, arrival_airport_code, aircraft_id, status)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

    private FlightDao() {

    }

    public static FlightDao getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Flight> findAll() {

        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL)) {
            var resultSet = preparedStatement.executeQuery();
            List<Flight> flights = new ArrayList<>();

            while (resultSet.next()) {
                flights.add(buildFlight(resultSet));
            }

            return flights;

        } catch (SQLException e) {
            throw new RuntimeException("Помилка при отриманні всіх рейсів", e);
        }
    }

    @Override
    public Optional<Flight> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID)) {

            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();

            Flight flight = null;
            if (resultSet.next()) {
                flight = buildFlight(resultSet);
            }

            return Optional.ofNullable(flight);

        } catch (SQLException e) {
            throw new RuntimeException("Помилка при пошуку рейсу за ID: " + id, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(DELETE_SQL)) {

            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Помилка при видаленні рейсу: " + id, e);
        }
    }

    @Override
    public void update(Flight entity) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {

            preparedStatement.setString(1, entity.getFlightNo());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(entity.getDepartureDate()));
            preparedStatement.setString(3, entity.getDepartureAirportCode());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(entity.getArrivalDate()));
            preparedStatement.setString(5, entity.getArrivalAirportCode());
            preparedStatement.setInt(6, entity.getAircraftId());
            preparedStatement.setString(7, entity.getStatus().name());
            preparedStatement.setLong(8, entity.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Помилка при оновленні рейсу: " + entity.getId(), e);
        }
    }

    @Override
    public Flight save(Flight entity) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL,
                     java.sql.Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, entity.getFlightNo());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(entity.getDepartureDate()));
            preparedStatement.setString(3, entity.getDepartureAirportCode());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(entity.getArrivalDate()));
            preparedStatement.setString(5, entity.getArrivalAirportCode());
            preparedStatement.setInt(6, entity.getAircraftId());
            preparedStatement.setString(7, entity.getStatus().name());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                entity.setId(generatedKeys.getLong("id"));
            }

            return entity;

        } catch (SQLException e) {
            throw new RuntimeException("Помилка при збереженні рейсу", e);
        }
    }

    private Flight buildFlight(ResultSet resultSet) throws SQLException {
        return new Flight(
                resultSet.getObject("id", Long.class),
                resultSet.getObject("flight_no", String.class),
                resultSet.getObject("departure_date", Timestamp.class).toLocalDateTime(),
                resultSet.getObject("departure_airport_code", String.class),
                resultSet.getObject("arrival_date", Timestamp.class).toLocalDateTime(),
                resultSet.getObject("arrival_airport_code", String.class),
                resultSet.getObject("aircraft_id", Integer.class),
                FlightStatus.valueOf(resultSet.getObject("status", String.class))
        );
    }
}
