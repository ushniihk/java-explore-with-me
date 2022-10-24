package ru.practicum.exploreWithMe.location.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.exploreWithMe.location.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Location getLocationByLatAndLon(double lat, double lon);
}
