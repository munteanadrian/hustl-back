package tech.adrianmuntean.hustl.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tech.adrianmuntean.hustl.model.User;
import tech.adrianmuntean.hustl.security.services.UserDetailsImpl;
import tech.adrianmuntean.hustl.service.UserService;
import tech.adrianmuntean.hustl.utils.APIResponse;

@CrossOrigin(origins = "*", maxAge = 4800)
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //region read
    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();
        return ResponseEntity.ok(userService.findById(userId));
    }
    //endregion

    //region update
    @PutMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> updateCurrentUser(@RequestBody String change, Authentication authentication) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(change);

            String updateKey = jsonNode.get("key").asText();
            String updateValue = jsonNode.get("value").asText();

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Long userId = userDetails.getId();

            boolean updated = userService.update(userId, updateKey, updateValue);

            if (updated) {
                return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(HttpStatus.OK.value(), "User " + userService.findById(userId).getEmail() + " updated successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new APIResponse(HttpStatus.CONFLICT.value(), "User " + userService.findById(userId).getEmail() + " not updated."));
            }

        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid update request");
        }
    }
    //endregion

    //region delete
    @DeleteMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> deleteCurrentUser(Authentication authentication, HttpServletRequest request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();

        boolean deleted = userService.delete(userId);
        request.getSession().invalidate();

        if (deleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(HttpStatus.OK.value(), "User deleted successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new APIResponse(HttpStatus.CONFLICT.value(), "User not deleted."));
        }
    }
    //endregion
}
