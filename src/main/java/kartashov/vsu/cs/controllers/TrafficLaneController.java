package kartashov.vsu.cs.controllers;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kartashov.vsu.cs.annotations.DI;
import kartashov.vsu.cs.models.Car;
import kartashov.vsu.cs.models.TrafficLane;
import kartashov.vsu.cs.services.TrafficLaneService;
import kartashov.vsu.cs.utils.JsonParser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TrafficLaneController extends HttpServlet {

    @DI
    private static TrafficLaneService trafficLaneService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getPathInfo() != null && !request.getPathInfo().isEmpty()) {
            try {
                TrafficLane trafficLane = trafficLaneService.get(request.getQueryString());

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                response.getWriter().write(JsonParser.serializeTrafficLane(trafficLane));
            } catch (Exception e) {
                response.sendError(500);
            }
        } else {
            try {
                List<TrafficLane> trafficLanes = trafficLaneService.getAll();

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                response.getWriter().write(JsonParser.serializeTrafficLanes(trafficLanes));
            } catch (SQLException e) {
                response.sendError(500);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            StringBuilder builder = new StringBuilder();
            var reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            String json = builder.toString();

            trafficLaneService.save(json);

            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(500);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        try {
            StringBuilder builder = new StringBuilder();
            var reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            String json = builder.toString();

            trafficLaneService.update(json, request.getQueryString());

            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(500);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        try {
            trafficLaneService.delete(request.getQueryString());

            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(500);
        }
    }

}
