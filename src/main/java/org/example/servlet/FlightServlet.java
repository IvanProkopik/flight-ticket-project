package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.FlightDto;
import org.example.service.FlightService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/flight")
public class FlightServlet extends HttpServlet {
    private final FlightService flightService = FlightService.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        List<FlightDto> flights = flightService.findAll();
        objectMapper.writeValue(resp.getOutputStream(), flights);
    }
}
