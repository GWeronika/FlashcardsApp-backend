package pk.backend.flashcards.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pk.backend.flashcards.entity.Category;
import pk.backend.flashcards.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(int id) {
        try {
            if(id <= 0) {
                throw new IllegalArgumentException("CategoryService: incorrect id");
            }
            return categoryRepository.findById(id);
        } catch (Exception e) {
            System.err.println("Error retrieving category");
            throw e;
        }
    }

    public void addCategory(String name, String colour) {
        try {
            if(name == null || colour == null) {
                throw new IllegalArgumentException("CategoryService: incorrect data");
            }
            Category category = new Category(name, colour);
            categoryRepository.save(category);
        } catch (Exception e) {
            System.err.println("Error adding category: " + e.getMessage());
        }
    }

    public void deleteCategoryById(int id) {
        try {
            if(id <= 0) {
                throw new IllegalArgumentException("CategoryService: incorrect id");
            }
            categoryRepository.deleteById(id);
        } catch (Exception e) {
            System.err.println("Error deleting category: " + e.getMessage());
        }
    }

    public void editCategory(int id, String newName, String newColour) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("CategoryService: incorrect id");
            }
            Optional<Category> existingCategoryOptional = categoryRepository.findById(id);
            if (existingCategoryOptional.isPresent()) {
                Category existingCategory = existingCategoryOptional.get();
                existingCategory.setName(newName);
                existingCategory.setColour(newColour);

                categoryRepository.save(existingCategory);
            } else {
                throw new IllegalArgumentException("CategoryService: category not found with id " + id);
            }
        } catch (Exception e) {
            System.err.println("Error editing category: " + e.getMessage());
            throw e;
        }
    }
}
