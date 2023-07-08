package tech.adrianmuntean.hustl.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tech.adrianmuntean.hustl.model.Category;
import tech.adrianmuntean.hustl.model.Community;
import tech.adrianmuntean.hustl.model.User;
import tech.adrianmuntean.hustl.repository.CategoryRepository;
import tech.adrianmuntean.hustl.repository.CommunityRepository;
import tech.adrianmuntean.hustl.repository.UserRepository;
import tech.adrianmuntean.hustl.security.services.UserDetailsImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;
    private final CategoryRepository categoryRepository;

    public UserService(UserRepository userRepository, CommunityRepository communityRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.communityRepository = communityRepository;
        this.categoryRepository = categoryRepository;
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found: " + email));
    }

    public UserDetails findByEmailSecurity(String email) {
        return UserDetailsImpl.build(findByEmail(email));
    }

    public boolean update(Long userId, String key, String value) {
        Optional<User> temp = userRepository.findById(userId);

        if (temp.isPresent()) {
            User user = temp.get();

            switch (key) {
                case "email" -> user.setEmail(value);
                case "name" -> user.setName(value);
                case "password" -> user.setPassword(value);
                case "interests" -> {
                    if (value.equals("null")) {
                        user.setInterests(null);
                    } else {
                        String[] interests = value.split(",");
                        List<String> interestsList = new ArrayList<>();

                        for (String interest : interests) {
                            interestsList.add(interest.trim());
                        }

                        List<Category> newInterests = new ArrayList<>();

                        for (String interest : interestsList) {
                            newInterests.add(categoryRepository.findByName(interest));
                        }

                        user.setInterests(newInterests);
                    }
                }
            }

            userRepository.save(user);
            return true;
        } else {
            throw new EntityNotFoundException("User not found: " + userId);
        }
    }

    public boolean delete(Long id) {
        if (userRepository.findById(id).isPresent()) {
            User user = userRepository.findById(id).orElse(null);

            if (user != null) {
                for (Community community : user.getCommunities()) {
                    community.getUsers().remove(user);
                    communityRepository.save(community);
                }
            }

            userRepository.deleteById(id);
            return true;
        } else {
            throw new EntityNotFoundException("User not found: " + id);
        }
    }

    public List<Community> getCommunityRecommendations(Long userId) {
        Optional<User> tempUser = userRepository.findById(userId);

        if (tempUser.isPresent()) {
            User user = tempUser.get();
            List<Community> recommendations = new ArrayList<>();

            for (Category interest : user.getInterests()) {
                if (!interest.getCommunities().isEmpty() && recommendations.size() < 20) {
                    recommendations.addAll(interest.getCommunities());
                }
            }

            return recommendations;
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }
}
