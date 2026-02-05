package org.example.dao;

import org.example.entity.Flight;
import org.example.entity.Ticket;
import org.example.util.ConnectionManager;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketDao implements Dao<Long, Ticket> {

    private static final TicketDao INSTANCE = new TicketDao();


    public static final String FIND_ALL_BY_FLIGHT_ID = """
            SELECT *
            FROM ticket
            WHERE flight_id = ?
            ORDER BY seat_no
            """;

    private static final String FIND_ALL = """
            SELECT *
            FROM ticket
            ORDER BY id
            """;

    private static final String FIND_BY_ID = """
            SELECT *
            FROM ticket
            WHERE id = ?
            """;

    private static final String DELETE_SQL = """
            DELETE FROM ticket
            WHERE id = ?
            """;

    private static final String SAVE_SQL = """
            INSERT INTO ticket (passenger_no, passenger_name, flight_id, seat_no, cost)
            VALUES (?, ?, ?, ?, ?)
            """;

    private static final String UPDATE_SQL = """
            UPDATE ticket
            SET passenger_no = ?,
                passenger_name = ?,
                flight_id = ?,
                seat_no = ?,
                cost = ?
            WHERE id = ?
            """;

    private TicketDao() {
    }

    public static TicketDao getInstance() {
        return INSTANCE;
    }

    public List<Ticket> findAllByFlightId(Long flightId) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_BY_FLIGHT_ID)) {

            preparedStatement.setObject(1, flightId);

            var resultSet = preparedStatement.executeQuery();
            List<Ticket> tickets = new ArrayList<>();

            while (resultSet.next()) {
                tickets.add(buildTicket(resultSet));
            }

            return tickets;

        } catch (SQLException e) {
            throw new RuntimeException("Помилка при отриманні квитків для рейсу: " + flightId, e);
        }
    }


    @Override
    public List<Ticket> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL))
        {
            var resultSet = preparedStatement.executeQuery();
            List<Ticket> tickets = new ArrayList<>();

            while (resultSet.next()){
                tickets.add(buildTicket(resultSet));
            }

            return tickets;

        }catch (SQLException e){
            throw new RuntimeException("Помилка при отриманні всіх квитків", e);
        }
    }

    @Override
    public Optional<Ticket> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID)) {

            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();

            Ticket ticket = null;
            if (resultSet.next()) {
                ticket = buildTicket(resultSet);
            }

            return Optional.ofNullable(ticket);

        } catch (SQLException e) {
            throw new RuntimeException("Помилка при пошуку квитка за ID: " + id, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(DELETE_SQL)) {

            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Помилка при видаленні квитка: " + id, e);
        }
    }

    @Override
    public void update(Ticket entity) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {

            preparedStatement.setString(1,entity.getPassengerNo());
            preparedStatement.setString(2, entity.getPassengerName());
            preparedStatement.setLong(3, entity.getFlightId());
            preparedStatement.setString(4, entity.getSeatNo());
            preparedStatement.setBigDecimal(5, entity.getCost());


            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Помилка при оновленні квитка: " + entity.getId(), e);
        }
    }

    @Override
    public Ticket save(Ticket entity) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL,
                     java.sql.Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1,entity.getPassengerNo());
            preparedStatement.setString(2, entity.getPassengerName());
            preparedStatement.setLong(3, entity.getFlightId());
            preparedStatement.setString(4, entity.getSeatNo());
            preparedStatement.setBigDecimal(5, entity.getCost());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                entity.setId(generatedKeys.getLong("id"));
            }

            return entity;

        } catch (SQLException e) {
            throw new RuntimeException("Помилка при збереженні квитка", e);
        }
    }
    private Ticket buildTicket(ResultSet resultSet) throws SQLException{
        return new Ticket(
        resultSet.getObject("id", Long.class),
        resultSet.getObject("passenger_no", String.class),
        resultSet.getObject("passenger_name", String.class),
        resultSet.getObject("flight_id", Long.class),
        resultSet.getObject("seat_no", String.class),
        resultSet.getObject("cost", BigDecimal.class));
    }
}
