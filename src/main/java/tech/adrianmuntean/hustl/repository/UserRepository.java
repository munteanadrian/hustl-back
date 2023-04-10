package tech.adrianmuntean.hustl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.adrianmuntean.hustl.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
