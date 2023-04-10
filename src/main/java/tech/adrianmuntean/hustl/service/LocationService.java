package tech.adrianmuntean.hustl.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import tech.adrianmuntean.hustl.model.Location;
import tech.adrianmuntean.hustl.repository.LocationRepository;

@Service
public class LocationService {
    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location findById(Long locationId) {
        return locationRepository.findById(locationId).orElseThrow(() -> new EntityNotFoundException("No location with id " + locationId));
    }
}
