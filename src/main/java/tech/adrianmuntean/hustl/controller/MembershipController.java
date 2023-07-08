package tech.adrianmuntean.hustl.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tech.adrianmuntean.hustl.security.services.UserDetailsImpl;
import tech.adrianmuntean.hustl.service.MembershipService;
import tech.adrianmuntean.hustl.utils.APIResponse;

@CrossOrigin(origins = "*", maxAge = 4800)
@RestController
@RequestMapping("/api/membership")
public class MembershipController {
    public MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }
    
    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> getMyCommunities(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(membershipService.getMyCommunities(userDetails.getId()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> getCommunityMembers(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(membershipService.getCommunityMembers(id));
    }

    @PostMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> joinCommunity(@PathVariable Long id, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        boolean joined = membershipService.joinCommunity(id, userDetails.getId());

        if (joined) {
            return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(HttpStatus.OK.value(), "User joined community " + id));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new APIResponse(HttpStatus.CONFLICT.value(), "User already joined community " + id));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> leaveCommunity(@PathVariable Long id, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        boolean left = membershipService.leaveCommunity(id, userDetails.getId());

        if (left) {
            return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(HttpStatus.OK.value(), "User left community " + id));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new APIResponse(HttpStatus.CONFLICT.value(), "User can't leave the community " + id));
        }
    }
}
