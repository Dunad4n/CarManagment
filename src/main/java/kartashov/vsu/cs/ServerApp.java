package kartashov.vsu.cs;

import kartashov.vsu.cs.controllers.CarController;
import kartashov.vsu.cs.controllers.RoadController;
import kartashov.vsu.cs.controllers.TrafficLaneController;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ServerApp {

    final static private String URL = "jdbc:postgresql://localhost:9100/cars";
    final static private String USER = "root";
    final static private String PASSWORD = "root";

    static {
        try {
            ApplicationContext.injectDependencies();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Connection connection;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        try {
//            connection.createStatement().execute("TRUNCATE TABLE road RESTART IDENTITY CASCADE ");
//            connection.createStatement().execute("TRUNCATE TABLE lane RESTART IDENTITY CASCADE ");
//            connection.createStatement().execute("TRUNCATE TABLE car RESTART IDENTITY CASCADE ");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.setContextPath("/api");

        servletContextHandler.addServlet(new ServletHolder(new CarController()), "/cars/*");
        servletContextHandler.addServlet(new ServletHolder(new RoadController()), "/roads/*");
        servletContextHandler.addServlet(new ServletHolder(new TrafficLaneController()), "/trafficLanes/*");

        server.setHandler(servletContextHandler);

        server.start();
        server.join();
    }
}
