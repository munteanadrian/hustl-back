package tech.adrianmuntean.hustl.service;

import org.springframework.stereotype.Service;
import tech.adrianmuntean.hustl.model.Category;
import tech.adrianmuntean.hustl.model.Community;
import tech.adrianmuntean.hustl.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public boolean createCategory(String name) {
        if (categoryRepository.existsByName(name)) {
            return false;
        } else {
            Category newCategory = new Category(name);
            categoryRepository.save(newCategory);
            return true;
        }
    }

    public boolean deleteCategory(String name) {
        if (categoryRepository.existsByName(name)) {
//            Remove the category from all users that have it as an interest
            categoryRepository.findByName(name).getUsers().forEach(user -> user.getInterests().remove(categoryRepository.findByName(name)));
//            Remove the category from all communities that have it as a category
            categoryRepository.findByName(name).getCommunities().forEach(community -> community.setCategory(null));

            categoryRepository.deleteByName(name);
            return true;
        } else {
            return false;
        }
    }

    public List<Community> getCommunitiesByCategory(String name) {
        return categoryRepository.findByName(name).getCommunities();
    }
}
