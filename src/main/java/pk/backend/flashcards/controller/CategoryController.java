package pk.backend.flashcards.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pk.backend.flashcards.entity.Category;
import pk.backend.flashcards.service.CategoryService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/select/all")
    public List<Category> getAllUsers() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/select/id")
    public Optional<Category> getUserById(int id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addCategory(@RequestParam String name, @RequestParam String colour) {
        try {
            categoryService.addCategory(name, colour);
            return ResponseEntity.ok("Category added successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding the category");
        }
    }

    @GetMapping("/delete")
    public void deleteCategoryById(int id) {
        categoryService.deleteCategoryById(id);
    }

    @GetMapping("/edit")
    public void editSet(int id, String newName, String newColour) {
        categoryService.editCategory(id, newName, newColour);
    }
}
