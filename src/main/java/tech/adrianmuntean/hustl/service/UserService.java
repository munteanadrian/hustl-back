package tech.adrianmuntean.hustl.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tech.adrianmuntean.hustl.model.User;
import tech.adrianmuntean.hustl.repository.UserRepository;
import tech.adrianmuntean.hustl.security.services.UserDetailsImpl;

import java.util.Optional;

/**
 * Service class for the User model.
 * <p>
 * This class contains all the methods that operate on the User model.
 * <p>
 * The methods are:
 * - create
 * - findById
 * - findByEmail
 * - findAll
 * - update
 * - delete
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //region CREATE
    //endregion

    //region READ
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found: " + email));
    }

    public UserDetails findByEmailSecurity(String email) {
        return UserDetailsImpl.build(userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found: " + email)));
    }

    //endregion

    //region UPDATE
    public String update(Long id, String key, String value) {
        Optional<User> temp = userRepository.findById(id);

        if (temp.isPresent()) {
            String response = "NOT DONE";

            response = "OK";

            User tempUser = temp.get();

            switch (key) {
                case "email" -> {
//                    response = validate.email(value);

                    if (response.equals("OK")) {
                        tempUser.setEmail(value);
                    }
                }
                case "name" -> {
//                    response = validate.name(value);


                    if (response.equals("OK")) {
                        tempUser.setName(value);
                    }
                }
                case "password" -> {
//                    response = validate.pass(value);

                    if (response.equals("OK")) {
                        tempUser.setPassword(value);
                    }
                }
                default -> response = "Invalid request";
            }

            userRepository.save(tempUser);
            return response;
        } else {
            throw new EntityNotFoundException("User not found: " + id);
        }
    }
    //endregion

    //region DELETE
    public String delete(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return "OK";
        } else {
            throw new EntityNotFoundException("User not found: " + id);
        }
    }
    //endregion

}
