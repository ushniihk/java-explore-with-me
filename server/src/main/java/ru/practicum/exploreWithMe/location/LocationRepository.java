package ru.practicum.exploreWithMe.location;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Location getLocationByLatAndLon(double lat, double lon);
}
