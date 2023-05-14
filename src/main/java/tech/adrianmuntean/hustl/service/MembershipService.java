package tech.adrianmuntean.hustl.service;

import org.springframework.stereotype.Service;
import tech.adrianmuntean.hustl.model.Community;
import tech.adrianmuntean.hustl.model.User;
import tech.adrianmuntean.hustl.repository.CommunityRepository;
import tech.adrianmuntean.hustl.repository.UserRepository;

import java.util.Set;

@Service
public class MembershipService {
    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;

    public MembershipService(CommunityRepository communityRepository, UserRepository userRepository) {
        this.communityRepository = communityRepository;
        this.userRepository = userRepository;
    }


    public boolean joinCommunity(Long communityID, Long userId) {
        Community community = communityRepository.findById(communityID).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (community == null || user == null || community.getUsers().contains(user)) {
//            Custom errors if community doesn't exist or other connexion issues
            return false;
        } else {
            community.getUsers().add(user);
            communityRepository.save(community);
            user.getCommunities().add(community);
            userRepository.save(user);
            return true;
        }
    }

    public boolean leaveCommunity(Long communityId, Long userId) {
        Community community = communityRepository.findById(communityId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (community == null || user == null || !community.getUsers().contains(user)) {
//            Custom errors if community doesn't exist or other connexion issues
            return false;
        } else {
            community.getUsers().remove(user);
            communityRepository.save(community);
            user.getCommunities().remove(community);
            userRepository.save(user);
            return true;
        }
    }

    public Set<Community> getMyCommunities(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        } else {
            return user.getCommunities();
        }
    }

    public Set<User> getCommunityMembers(Long id) {
        Community community = communityRepository.findById(id).orElse(null);
        if (community == null) {
            return null;
        } else {
            return community.getUsers();
        }
    }
}
