package tech.adrianmuntean.hustl.service;

import org.springframework.stereotype.Service;
import tech.adrianmuntean.hustl.dto.CommunityDTO;
import tech.adrianmuntean.hustl.model.Community;
import tech.adrianmuntean.hustl.model.User;
import tech.adrianmuntean.hustl.repository.CommunityRepository;
import tech.adrianmuntean.hustl.repository.UserRepository;

import java.util.List;

@Service
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;

    public CommunityService(CommunityRepository communityRepository, UserRepository userRepository) {
        this.communityRepository = communityRepository;
        this.userRepository = userRepository;
    }

    // region CREATE
    public boolean createCommunity(CommunityDTO communityDTO) {
        if (communityRepository.existsByName(communityDTO.getName())) {
            return false;
        } else {
            communityRepository.save(new Community(communityDTO.getName()));
            return true;
        }
    }
    // endregion

    // region READ
    public List<Community> getAllCommunities() {
        return communityRepository.findAll();
    }

    public Community getCommunityById(Long id) {
        return communityRepository.findById(id).orElse(null);
    }
    // endregion

    // region UPDATE
    public boolean updateCommunity(Long id, String key, String value) {
        Community community = communityRepository.findById(id).orElse(null);

        if (community == null) {
            return false;
        } else {
            if (key.equals("name")) {
                community.setName(value);
            } else {
                return false;
            }
            communityRepository.save(community);
            return true;
        }
    }
    // endregion

    // region DELETE
    public boolean deleteCommunity(Long communityId) {
        Community community = communityRepository.findById(communityId).orElse(null);
        if (community == null) {
            return false;
        } else {
            for (User user : community.getUsers()) {
                user.getCommunities().remove(community);
                userRepository.save(user);
            }
            communityRepository.delete(community);
            
            return true;
        }
    }
    // endregion
}
