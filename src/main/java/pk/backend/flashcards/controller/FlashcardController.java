package pk.backend.flashcards.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pk.backend.flashcards.entity.Flashcard;
import pk.backend.flashcards.entity.Set;
import pk.backend.flashcards.service.FlashcardService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/flashcard")
public class FlashcardController {
    @Autowired
    private final FlashcardService flashcardService;

    @Autowired
    public FlashcardController(FlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    @GetMapping("/select/all")
    public List<Flashcard> getAllFlashcards() {
        return flashcardService.getAllFlashcards();
    }

    @GetMapping("/select/id")
    public Optional<Flashcard> getFlashcardById(int id) {
        return flashcardService.getFlashcardById(id);
    }

    @GetMapping("/select/setid")
    public Optional<List<Flashcard>> getFlashcardsBySetId(int setId) {
        return flashcardService.getFlashcardsBySetId(setId);
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addFlashcard(@RequestParam String word, @RequestParam String description, @RequestBody Set set) {
        try {
            boolean isFavourite = false;
            flashcardService.addFlashcard(word, description, isFavourite, set);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Flashcard added successfully");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "An error occurred while adding the flashcard");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/delete")
    public void deleteFlashcardById(@RequestParam int id) {
        flashcardService.deleteFlashcardById(id);
    }

    @DeleteMapping("/delete/set")
    public void deleteFlashcardBySetId(@RequestParam int setId) {
        flashcardService.deleteFlashcardBySetId(setId);
    }

    @PutMapping("/edit/favourite")
    public void editFlashcardFavourite(int id, boolean isFavourite) {
        flashcardService.editFlashcardFavourite(id, isFavourite);
    }

    @PutMapping("/edit")
    public void editFlashcardWord(@RequestParam int id, @RequestParam String word, @RequestParam String description) {
        flashcardService.editFlashcard(id, word, description);
    }
}
