package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.FlightDto;
import org.example.entity.Flight;
import org.example.entity.FlightStatus;
import org.example.service.FlightService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@WebServlet("/flight/*")
public class FlightServlet extends HttpServlet {
    private final FlightService flightService = FlightService.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String flightIdParam = req.getParameter("flightId");


        if (flightIdParam != null){
            try {
                Optional<FlightDto> flight = flightService.findById(Long.parseLong(flightIdParam));
                if (flight.isPresent()) {
                    objectMapper.writeValue(resp.getOutputStream(), flight);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().print("Flight not found!");
                }
            } catch (NumberFormatException e){
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error:\":\"Something went wrong! Invalid id\"}");
            }
        } else {
            List<FlightDto> flights = flightService.findAll();
            objectMapper.writeValue(resp.getOutputStream(), flights);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String flightIdParam = req.getParameter("id");

        try {
            if (flightIdParam != null || !flightIdParam.isEmpty()) {
                Long id = Long.parseLong(flightIdParam);
                boolean isDeleted = flightService.delete(id);
                if (isDeleted) {
                    resp.getWriter().write("{\"status:\":\"success\"}");
                } else {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("{\"error:\":\"Something went wrong! Maybe missing book id\"}");
                }
            }
        } catch (NumberFormatException e){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error:\":\"Something went wrong! Invalid id\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String id = req.getParameter("id");
        String flightNo = req.getParameter("flightNo");
        String departureDate = req.getParameter("departureDate");
        String departureAirportCode = req.getParameter("departureAirportCode");
        String arrivalDate = req.getParameter("arrivalDate");
        String arrivalAirportCode = req.getParameter("arrivalAirportCode");
        String aircraftId = req.getParameter("aircraftId");
        String status = req.getParameter("status");

        if (id != null && !id.isEmpty()){
            try {
                Long idParam = Long.parseLong(id);
                LocalDateTime departureDateParam = LocalDateTime.parse(departureDate);
                LocalDateTime arrivalDateParam = LocalDateTime.parse(arrivalDate);
                Integer aircraftIdParam = Integer.parseInt(aircraftId);
                FlightStatus statusParam = FlightStatus.valueOf(status);

                Flight flight = Flight.builder()
                        .id(idParam)
                        .flightNo(flightNo)
                        .departureDate(departureDateParam)
                        .departureAirportCode(departureAirportCode)
                        .arrivalDate(arrivalDateParam)
                        .arrivalAirportCode(arrivalAirportCode)
                        .aircraftId(aircraftIdParam)
                        .status(statusParam)
                        .build();

                flightService.save(flight);
                objectMapper.writeValue(resp.getWriter(), flight);

            } catch (NumberFormatException e){
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error:\":\"Something went wrong! Invalid id\"}");
            }
        }
    }
}
