CREATE TABLE road
(
    road_id         SERIAL NOT NULL PRIMARY KEY,
    next_road_id    INT REFERENCES road(road_id),
    prev_road_id    INT REFERENCES road(road_id)
);

CREATE TABLE lane
(
    lane_id         SERIAL NOT NULL PRIMARY KEY,
    road_id         INT NOT NULL REFERENCES road(road_id)
);

CREATE TABLE car
(
    car_id          SERIAL NOT NULL PRIMARY KEY,
    speed           INT NOT NULL,
    type            VARCHAR(15) NOT NULL,
    start_road_id   INT NOT NULL REFERENCES road(road_id),
    goal_road_id    INT NOT NULL REFERENCES road(road_id),
    lane_id         INT NOT NULL REFERENCES lane(lane_id)
);