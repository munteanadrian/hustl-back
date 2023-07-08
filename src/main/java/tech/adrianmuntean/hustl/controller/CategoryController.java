package tech.adrianmuntean.hustl.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.adrianmuntean.hustl.model.Community;
import tech.adrianmuntean.hustl.service.CategoryService;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public List<String> getAllCategories() {
        List<String> categoryNames = new ArrayList<>();
        categoryService.getAllCategories().forEach(category -> categoryNames.add(category.getName()));
        return categoryNames;
    }

    @GetMapping("/{name}")
    public List<Community> getCommunitiesByCategory(@PathVariable String name) {
        return categoryService.getCommunitiesByCategory(name);
    }

    @PostMapping("/")
    public ResponseEntity<String> createCategory(@RequestBody String name) {
        if (categoryService.createCategory(name)) {
            return ResponseEntity.ok("Category created successfully");
        } else {
            return ResponseEntity.badRequest().body("Category already exists");
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<String> deleteCategory(@RequestBody String name) {
        if (categoryService.deleteCategory(name)) {
            return ResponseEntity.ok("Category deleted successfully");
        } else {
            return ResponseEntity.badRequest().body("Category does not exist");
        }
    }
}
