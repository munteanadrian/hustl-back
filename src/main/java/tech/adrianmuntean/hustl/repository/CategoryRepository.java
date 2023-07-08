package tech.adrianmuntean.hustl.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tech.adrianmuntean.hustl.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String category);

    boolean existsByName(String name);

    void deleteByName(String name);
}
