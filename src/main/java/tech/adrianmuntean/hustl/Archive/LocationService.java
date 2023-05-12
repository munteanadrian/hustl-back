//package tech.adrianmuntean.hustl.Archive;
//
//import jakarta.persistence.EntityNotFoundException;
//import org.springframework.stereotype.Service;
//import tech.adrianmuntean.hustl.Archive.Location;
//import tech.adrianmuntean.hustl.Archive.LocationRepository;
//
//@Service
//public class LocationService {
//    private final LocationRepository locationRepository;
//
//    public LocationService(LocationRepository locationRepository) {
//        this.locationRepository = locationRepository;
//    }
//
//    public Location findById(Long locationId) {
//        return locationRepository.findById(locationId).orElseThrow(() -> new EntityNotFoundException("No location with id " + locationId));
//    }
//}
