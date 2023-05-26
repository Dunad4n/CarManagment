package kartashov.vsu.cs.controllers;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kartashov.vsu.cs.annotations.DI;
import kartashov.vsu.cs.models.Car;
import kartashov.vsu.cs.models.Road;
import kartashov.vsu.cs.services.RoadService;
import kartashov.vsu.cs.utils.JsonParser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RoadController extends HttpServlet {

    @DI
    private static RoadService roadService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getPathInfo() != null && !request.getPathInfo().isEmpty()) {
            try {
                Road road = roadService.get(request.getQueryString());

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                response.getWriter().write(JsonParser.serializeRoad(road));
            } catch (Exception e) {
                response.sendError(500);
            }
        } else {
            try {
                List<Road> road = roadService.getAll();

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                response.getWriter().write(JsonParser.serializeRoads(road));
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

            roadService.save(json);

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

            roadService.update(json, request.getQueryString());

            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(500);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        try {
            roadService.delete(request.getQueryString());

            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(500);
        }
    }

}
