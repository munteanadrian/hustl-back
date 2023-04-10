package tech.adrianmuntean.hustl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.adrianmuntean.hustl.model.Gender;

public interface GenderRepository extends JpaRepository<Gender, Long> {
}
