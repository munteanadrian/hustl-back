package tech.adrianmuntean.hustl.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tech.adrianmuntean.hustl.dto.CommunityDTO;
import tech.adrianmuntean.hustl.model.Community;
import tech.adrianmuntean.hustl.security.services.UserDetailsImpl;
import tech.adrianmuntean.hustl.service.CommunityService;
import tech.adrianmuntean.hustl.service.UserService;
import tech.adrianmuntean.hustl.utils.APIResponse;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 4800)
@RestController
@RequestMapping("/api/community")
public class CommunityController {
    public UserService userService;
    public CommunityService communityService;

    public CommunityController(UserService userService, CommunityService communityService) {
        this.userService = userService;
        this.communityService = communityService;
    }

    @PostMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> createCommunity(@RequestBody CommunityDTO communityDTO, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();

        boolean created = communityService.createCommunity(communityDTO, userId);

        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new APIResponse(HttpStatus.CREATED.value(), "Community " + communityDTO.getName() + " created successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new APIResponse(HttpStatus.CONFLICT.value(), "Community " + communityDTO.getName() + " already exists"));
        }
    }

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> getAllCommunities() {
        List<CommunityDTO> communitiesDTO = new ArrayList<>();
        for (Community current : communityService.getAllCommunities()) {
            CommunityDTO temp = new CommunityDTO(current.getCommunityId(), current.getName(), current.getCategory().getName(), current.getDescription());
            communitiesDTO.add(temp);
        }

        return ResponseEntity.ok(communitiesDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> getCommunity(@PathVariable Long id) {
        return ResponseEntity.ok(communityService.getCommunityById(id));
    }

    @GetMapping("/popular")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> getPopularCommunities() {
        List<CommunityDTO> communitiesDTO = new ArrayList<>();
        for (Community current : communityService.getPopularCommunities()) {
            CommunityDTO temp = new CommunityDTO(current.getCommunityId(), current.getName(), current.getCategory().getName(), current.getDescription());
            communitiesDTO.add(temp);
        }

        return ResponseEntity.ok(communitiesDTO);
    }

    @GetMapping("/{communityId}/messages")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> getMessages(@PathVariable Long communityId) {
        return ResponseEntity.ok(communityService.getMessages(communityId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> updateCommunity(@PathVariable Long id, @RequestBody String change, Authentication authentication) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(change);
            boolean changed = communityService.updateCommunity(id, jsonNode.get("key").asText(), jsonNode.get("value").asText());

            if (changed) {
                return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(HttpStatus.OK.value(), "Community " + id + " updated successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new APIResponse(HttpStatus.CONFLICT.value(), "Community " + id + " not updated"));
            }
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIResponse(HttpStatus.BAD_REQUEST.value(), "Invalid JSON"));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Boolean> deleteCommunity(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(communityService.deleteCommunity(id));
    }
}
