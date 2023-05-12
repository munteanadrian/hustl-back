//package tech.adrianmuntean.hustl.Archive.utils;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import tech.adrianmuntean.hustl.model.User;
//import tech.adrianmuntean.hustl.repository.UserRepository;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class InputValidation {
//    private UserRepository userRepository;
//
//    @Autowired
//    public InputValidation() {
//        this.userRepository = userRepository;
//    }
//
//    public String name(String name) {
//        if (name.matches(".{2,50}")) {
//            if (name.matches("[-a-zA-Z']*")) {
//                return "OK";
//            } else {
//                return "Invalid characters";
//            }
//        } else {
//            return "Must be between 2 and 50 characters";
//        }
//    }
//
//    public String pass(String pass) {
//        if (pass.matches(".{8,}")) {
//            if (pass.matches(".*[a-z].*") && pass.matches(".*[A-Z].*") && pass.matches(".*[!@#$%^&*()_+].*") && pass.matches(".*\\d.*")) {
//                return "OK";
//            } else {
//                return "Must have a lowercase character, an uppercase character, a special character and a number";
//            }
//        } else {
//            return "Must be over 8 characters long";
//        }
//    }
//
//    public String email(String email) {
//        if (email.matches("^[-a-zA-Z0-9._]+@[-a-zA-Z0-9]+\\.[a-zA-Z]+")) {
//            if (userRepository.findByEmail(email).isEmpty()) {
//                return "OK";
//            } else {
//                return "Email already registered";
//            }
//        } else {
//            return "Invalid format example@email.com";
//        }
//    }
//
//    public List<String> user(User user) {
//        List<String> responses = new ArrayList<>();
//        responses.add(name(user.getName()));
//        responses.add(email(user.getEmail()));
//        responses.add(pass(user.getPassword()));
////        birthday(user.getBirthday());
//        return responses;
//    }
//
//    public void birthday(LocalDate birthday) {
//
//    }
//}
