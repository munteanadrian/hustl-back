package tech.adrianmuntean.hustl.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import tech.adrianmuntean.hustl.model.Gender;
import tech.adrianmuntean.hustl.model.Location;
import tech.adrianmuntean.hustl.model.User;
import tech.adrianmuntean.hustl.repository.UserRepository;
import tech.adrianmuntean.hustl.utils.InputValidation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final GenderService genderService;
    private final InputValidation validate;
    private final LocationService locationService;

    public UserService(UserRepository userRepository, InputValidation validate, GenderService genderService, LocationService locationService) {
        this.userRepository = userRepository;

        this.validate = validate;
        this.genderService = genderService;
        this.locationService = locationService;
    }

    //    CRUD

    //    create
    public List<String> create(User user) {
        List<String> validateResponses = validate.user(user);

        if (validateResponses.stream().filter(crt -> crt.equals("OK")).count() == validateResponses.size()) {
            validateResponses.add(userRepository.save(user).toString());
            validateResponses.add("Added");
        }

        return validateResponses.get(validateResponses.size() - 1).equals("Added") ? List.of(validateResponses.get(validateResponses.size() - 2)) : validateResponses;
    }

    //    read
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found: " + email));
    }

    public List<User> findAll() {
        List<User> all = userRepository.findAll();
        return all;
    }

    //    update
    public String update(Long id, String key, String value) {
        Optional<User> temp = userRepository.findById(id);

        if (temp.isPresent()) {
            String response = "NOT DONE";
            User tempUser = temp.get();

            switch (key) {
                case "email" -> {
                    response = validate.email(value);

                    if (response.equals("OK")) {
                        tempUser.setEmail(value);
                    }
                }
                case "name" -> {
                    response = validate.name(value);

                    if (response.equals("OK")) {
                        tempUser.setName(value);
                    }
                }
                case "password" -> {
                    response = validate.pass(value);

                    if (response.equals("OK")) {
                        tempUser.setPassword(value);
                    }
                }
                case "birthday" -> {
                    LocalDate birthday = LocalDate.parse(value, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
//                    response = validate.birthday(birthday);

//                    if (response.equals("OK")) {
//                        tempUser.setBirthday(birthday);
//                    }
                }
                case "gender_id" -> {
                    Gender gender = genderService.findById(Long.valueOf(value));
                    tempUser.setGender(gender);
                    response = "OK";
                }
                case "location_id" -> {
                    Location location = locationService.findById(Long.valueOf(value));
                    tempUser.setLocation(location);
                    response = "OK";
                }
                case "bio" -> tempUser.setBio(value);
            }

            userRepository.save(tempUser);
            return response;
        } else {
            throw new EntityNotFoundException("User not found: " + id);
        }
    }

    //    delete
    public String delete(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return "OK";
        } else {
            throw new EntityNotFoundException("User not found: " + id);
        }
    }
}
