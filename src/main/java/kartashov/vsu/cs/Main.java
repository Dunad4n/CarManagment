package kartashov.vsu.cs;

import kartashov.vsu.cs.annotations.DI;
import kartashov.vsu.cs.dao.CarDao;
import kartashov.vsu.cs.dao.RoadDao;
import kartashov.vsu.cs.dao.TrafficLaneDao;
import kartashov.vsu.cs.models.Road;

import java.util.ArrayList;
import java.util.List;

public class Main {

    @DI
    private static CarDao carDao;

    @DI
    private static RoadDao roadDao;

    @DI
    private static TrafficLaneDao trafficLaneDao;

    static {
        try {
            ApplicationContext.injectDependencies();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        List<Long> trafficLanesId = new ArrayList<>();
        trafficLanesId.add(2L);
        trafficLanesId.add(10L);
        trafficLanesId.add(7L);

        roadDao.save(new Road(trafficLanesId));
        roadDao.save(new Road(trafficLanesId));
        roadDao.save(new Road(trafficLanesId));

        System.out.println(roadDao.get(1L).toString());
        System.out.println(roadDao.get(2L).toString());
        roadDao.update(2L, new Road(new ArrayList<>()));
        System.out.println(roadDao.get(2L));
        roadDao.delete(3L);
    }
}