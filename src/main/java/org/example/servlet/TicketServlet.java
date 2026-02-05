package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.TicketDto;
import org.example.service.TicketService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@WebServlet("/tickets")
public class TicketServlet extends HttpServlet {
    private final TicketService ticketService = TicketService.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String flightIdParam = req.getParameter("flightId");
        if (flightIdParam == null){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getOutputStream(), Map.of("error", "flightIdParameter is required"));
            return;
        }
        Long flightId = Long.valueOf(flightIdParam);
        List<TicketDto> tickets = ticketService.findAllByFlightId(flightId);
        objectMapper.writeValue(resp.getOutputStream(), tickets);
    }
}
