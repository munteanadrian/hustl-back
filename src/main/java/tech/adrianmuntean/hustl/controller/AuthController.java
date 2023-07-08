package tech.adrianmuntean.hustl.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tech.adrianmuntean.hustl.dto.LoginDTO;
import tech.adrianmuntean.hustl.dto.SignupDTO;
import tech.adrianmuntean.hustl.model.Category;
import tech.adrianmuntean.hustl.model.User;
import tech.adrianmuntean.hustl.repository.CategoryRepository;
import tech.adrianmuntean.hustl.repository.UserRepository;
import tech.adrianmuntean.hustl.security.jwt.JWTConfig;
import tech.adrianmuntean.hustl.security.services.UserDetailsImpl;
import tech.adrianmuntean.hustl.utils.JWTResponse;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 4800)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder encoder;
    private final JWTConfig jwtConfig;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder encoder, JWTConfig jwtConfig, CategoryRepository categoryRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtConfig = jwtConfig;
        this.categoryRepository = categoryRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtConfig.generateToken(authentication);
            UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();

            return ResponseEntity.ok(new JWTResponse(jwt));
        } catch (AuthenticationException e) {
            System.out.println("Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return ResponseEntity.ok("Logged out successfully");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupDTO signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }

        String[] interestsString = signupRequest.getInterests().split(",");
        List<Category> interests = new ArrayList<>();

        for (String interest : interestsString) {
            Category category = categoryRepository.findByName(interest);
            if (category == null) {
                category = new Category(interest);
                categoryRepository.save(category);
            }
            interests.add(category);
        }

        User user = new User(signupRequest.getEmail(), encoder.encode(signupRequest.getPassword()), signupRequest.getName(), interests);
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }
}
