package tech.adrianmuntean.hustl.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import tech.adrianmuntean.hustl.model.User;
import tech.adrianmuntean.hustl.repository.UserRepository;
import tech.adrianmuntean.hustl.security.jwt.JWTConfig;
import tech.adrianmuntean.hustl.security.services.UserDetailsImpl;
import tech.adrianmuntean.hustl.utils.responses.JWTResponse;
import tech.adrianmuntean.hustl.utils.responses.MessageResponse;

@CrossOrigin(origins = "*", maxAge = 4800)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LogManager.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JWTConfig jwtConfig;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder encoder, JWTConfig jwtConfig) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtConfig = jwtConfig;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtConfig.generateToken(authentication);
            UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();

            logger.info("User " + user.getEmail() + " logged in.");
            return ResponseEntity.ok(new JWTResponse(user.getId(), jwt, user.getEmail()));
        } catch (AuthenticationException e) {
            System.out.println("Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Invalid credentials"));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupDTO signupRequest) {
        System.out.println("Here");
//        User already exists
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

//        User doesn't exist so we create a new one and save it to the database
        User user = new User(signupRequest.getEmail(), encoder.encode(signupRequest.getPassword()), signupRequest.getName());
        userRepository.save(user);
        logger.info("User " + user.getEmail() + " signed up.");

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


}