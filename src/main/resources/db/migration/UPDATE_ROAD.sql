ALTER TABLE road
ADD next_road_id INT NULL REFERENCES road(road_id) ON DELETE CASCADE ,
ADD prev_road_id INT NULL REFERENCES road(road_id) ON DELETE CASCADE

-- ALTER TABLE road
-- DROP next_road_id,
-- DROP prev_road_id