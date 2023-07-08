package tech.adrianmuntean.hustl.service;

import org.springframework.stereotype.Service;
import tech.adrianmuntean.hustl.dto.CommunityDTO;
import tech.adrianmuntean.hustl.model.Community;
import tech.adrianmuntean.hustl.model.Message;
import tech.adrianmuntean.hustl.model.User;
import tech.adrianmuntean.hustl.repository.CategoryRepository;
import tech.adrianmuntean.hustl.repository.CommunityRepository;
import tech.adrianmuntean.hustl.repository.UserRepository;

import java.util.List;
import java.util.Objects;

@Service
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final MembershipService membershipService;

    public CommunityService(CommunityRepository communityRepository, UserRepository userRepository, CategoryRepository categoryRepository, MembershipService membershipService) {
        this.communityRepository = communityRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.membershipService = membershipService;
    }

    // region CREATE
    public boolean createCommunity(CommunityDTO communityDTO, Long userId) {
        if (communityRepository.existsByName(communityDTO.getName())) {
            return false;
        } else {
            communityRepository.save(new Community(communityDTO.getName(), categoryRepository.findByName(communityDTO.getCategory()), communityDTO.getDescription()));
            categoryRepository.findByName(communityDTO.getCategory()).getCommunities().add(communityRepository.findByName(communityDTO.getName()));

            Long communityId = communityRepository.findByName(communityDTO.getName()).getCommunityId();
            membershipService.joinCommunity(communityId, userId);

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
            } else if (key.equals("category")) {
                community.setCategory(categoryRepository.findByName(value));
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
            categoryRepository.findByName(community.getCategory().getName()).getCommunities().remove(community);

            for (User user : community.getUsers()) {
                user.getCommunities().remove(community);
                userRepository.save(user);
            }

            communityRepository.delete(community);

            return true;
        }
    }

    public List<Community> getPopularCommunities() {
        List<Community> communities = communityRepository.findAll();
        communities.sort((o1, o2) -> o2.getUsers().size() - o1.getUsers().size());
        return communities;
    }

    public List<Message> getMessages(Long communityId) {
        return Objects.requireNonNull(communityRepository.findById(communityId).orElse(null)).getMessages();
    }
    // endregion
}
