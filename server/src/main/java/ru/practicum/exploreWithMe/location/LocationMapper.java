package ru.practicum.exploreWithMe.location;

public class LocationMapper {
    public static LocationDto toLocationDto(Location location){
        return new LocationDto(
                location.getLat(),
                location.getLon()
        );
    }

    public static Location toLocation(LocationDto locationDto){
        return new Location(
                locationDto.getLat(),
                locationDto.getLon()
        );
    }
}
