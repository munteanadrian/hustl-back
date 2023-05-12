//package tech.adrianmuntean.hustl.Archive;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import tech.adrianmuntean.hustl.Archive.Gender;
//import tech.adrianmuntean.hustl.Archive.GenderService;
//import tech.adrianmuntean.hustl.Archive.Location;
//import tech.adrianmuntean.hustl.Archive.LocationService;
//import tech.adrianmuntean.hustl.DTO.UserDTO;
//import tech.adrianmuntean.hustl.model.User;
//import tech.adrianmuntean.hustl.service.UserService;
//
//import java.util.List;
//
//@RestController
//@CrossOrigin
//@RequestMapping("/users")
//public class UserController {
//    private final UserService userService;
//    private final GenderService genderService;
//    private final LocationService locationService;
//
//    public UserController(UserService userService, GenderService genderService, LocationService locationService) {
//        this.userService = userService;
//        this.genderService = genderService;
//        this.locationService = locationService;
//    }
//
//    //    create
//    @PostMapping("/create")
//    public ResponseEntity<List<String>> create(@RequestBody UserDTO userDTO) {
//        User user = new User();
//        user.setFromDTO(userDTO);
//
//        Gender gender = genderService.findById(userDTO.getGenderId());
//        user.setGender(gender);
//
//        Location location = locationService.findById(userDTO.getLocationId());
//        user.setLocation(location);
//
//        return ResponseEntity.ok(userService.create(user));
//    }
//
//    //    read
//    @GetMapping("/{id}")
//    public ResponseEntity<User> findById(@PathVariable Long id) {
//        return ResponseEntity.ok(userService.findById(id));
//    }
//
//    @GetMapping("/email/{email}")
//    public ResponseEntity<User> findByEmail(@PathVariable String email) {
//        return ResponseEntity.ok(userService.findByEmail(email));
//    }
//
//    @GetMapping("/")
//    public ResponseEntity<List<User>> findAll() {
//        return ResponseEntity.ok(userService.findAll());
//    }
//
//    //    update
//    @PutMapping("/{id}")
//    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody String change) {
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            JsonNode jsonNode = mapper.readTree(change);
//            return ResponseEntity.ok(userService.update(id, jsonNode.get("key").asText(), jsonNode.get("value").asText()));
//        } catch (JsonProcessingException e) {
//            throw new IllegalArgumentException("Invalid update request");
//        }
//    }
//
//    //    delete
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> delete(@PathVariable Long id) {
//        return ResponseEntity.ok(userService.delete(id));
//    }
//}
