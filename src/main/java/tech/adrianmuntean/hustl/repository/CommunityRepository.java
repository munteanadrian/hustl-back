package tech.adrianmuntean.hustl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.adrianmuntean.hustl.model.Community;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    boolean existsByName(String name);
}
