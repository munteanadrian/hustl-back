//package tech.adrianmuntean.hustl.Archive;
//
//import jakarta.persistence.EntityNotFoundException;
//import org.springframework.stereotype.Service;
//import tech.adrianmuntean.hustl.Archive.Gender;
//import tech.adrianmuntean.hustl.Archive.GenderRepository;
//
//@Service
//public class GenderService {
//    private final GenderRepository genderRepository;
//
//    public GenderService(GenderRepository genderRepository) {
//        this.genderRepository = genderRepository;
//    }
//
//    public Gender findById(Long genderId) {
//        return genderRepository.findById(genderId).orElseThrow(() -> new EntityNotFoundException("No gender with id " + genderId));
//    }
//}
