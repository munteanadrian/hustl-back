package tech.adrianmuntean.hustl.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tech.adrianmuntean.hustl.model.Community;
import tech.adrianmuntean.hustl.model.User;
import tech.adrianmuntean.hustl.repository.CommunityRepository;
import tech.adrianmuntean.hustl.repository.UserRepository;
import tech.adrianmuntean.hustl.security.services.UserDetailsImpl;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;

    public UserService(UserRepository userRepository, CommunityRepository communityRepository) {
        this.userRepository = userRepository;
        this.communityRepository = communityRepository;
    }

    //    region FIND
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found: " + email));
    }

    public UserDetails findByEmailSecurity(String email) {
        return UserDetailsImpl.build(findByEmail(email));
    }
    //   endregion

    public boolean update(Long userId, String key, String value) {
        Optional<User> temp = userRepository.findById(userId);

        if (temp.isPresent()) {
            User user = temp.get();
            switch (key) {
                case "email" -> user.setEmail(value);
                case "name" -> user.setName(value);
                case "password" -> user.setPassword(value);
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
}
