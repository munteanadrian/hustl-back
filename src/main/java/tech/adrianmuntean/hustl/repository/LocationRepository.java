package tech.adrianmuntean.hustl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.adrianmuntean.hustl.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
