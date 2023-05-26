package kartashov.vsu.cs.controllers;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kartashov.vsu.cs.annotations.Component;
import kartashov.vsu.cs.annotations.DI;
import kartashov.vsu.cs.dao.CarDao;
import kartashov.vsu.cs.models.Car;
import kartashov.vsu.cs.services.CarService;
import kartashov.vsu.cs.utils.JsonParser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CarController extends HttpServlet {

    @DI
    private static CarService carService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getPathInfo() != null && !request.getPathInfo().isEmpty()) {
            try {
                Car car = carService.get(request.getQueryString());

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                response.getWriter().write(JsonParser.serializeCar(car));
            } catch (Exception e) {
                response.sendError(500);
            }
        } else {
            try {
                List<Car> cars = carService.getAll();

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                response.getWriter().write(JsonParser.serializeCars(cars));
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

            carService.save(json);

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

            carService.update(json, request.getQueryString());

            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(500);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        try {
            carService.delete(request.getQueryString());

            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(500);
        }
    }

}
