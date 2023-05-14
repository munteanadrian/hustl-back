package tech.adrianmuntean.hustl.controller;

// Create a standard for requests, add all to Postman, document them in Postman
// Community to do steps:
// 1. Create community model DONE
// 2. Create community repository DONE
// 3. Create community service
// 4. Create community controller: APIs for create community, delete community, update community, get all communities,
// get community information and also join community, leave community, see my communities, see members of a community
// 5. Test APIs - Postman - create community, get info, join 3 members, see members, leave 1 member, change community
// name, see communities of a member, see all communities, delete community with 2 members

// Create chat model:
// 1. Group chat
// 2. Private chat and friends list

// Create events for communities
// 1. Create event model tied to a community
// 2. Create event repository
// 3. Create event service
// 4. Create event controller: APIs for create event, delete event, update event, get all events for each community
// 5. Test APIs - Postman - create event, get info, update event, delete event, see all events for a community


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tech.adrianmuntean.hustl.dto.CommunityDTO;
import tech.adrianmuntean.hustl.service.CommunityService;
import tech.adrianmuntean.hustl.service.UserService;
import tech.adrianmuntean.hustl.utils.APIResponse;

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

    // region CREATE
    @PostMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> createCommunity(@RequestBody CommunityDTO communityDTO) {
        //    Modify this so that communities are created by users and only the owners can delete them
        boolean created = communityService.createCommunity(communityDTO);

        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new APIResponse(HttpStatus.CREATED.value(), "Community " + communityDTO.getName() + " created successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new APIResponse(HttpStatus.CONFLICT.value(), "Community " + communityDTO.getName() + " already exists"));
        }
    }
    // endregion

    // region READ
    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> getAllCommunities() {
        return ResponseEntity.ok(communityService.getAllCommunities());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> getCommunity(@PathVariable Long id) {
        return ResponseEntity.ok(communityService.getCommunityById(id));
    }
    // endregion

    // region UPDATE
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
    // endregion

    // region DELETE
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Boolean> deleteCommunity(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(communityService.deleteCommunity(id));
    }
    // endregion
}
