package pk.backend.flashcards.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pk.backend.flashcards.entity.Flashcard;
import pk.backend.flashcards.entity.Set;
import pk.backend.flashcards.service.FlashcardService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/flashcard")
public class FlashcardController {
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

    @GetMapping("/add")
    public void addFlashcard(String word, String description, boolean isFavourite, Set set) {
        flashcardService.addFlashcard(word, description, isFavourite, set);
    }

    @GetMapping("/delete")
    public void deleteFlashcardById(int id) {
        flashcardService.deleteFlashcardById(id);
    }

    @GetMapping("/edit")
    public void editFlashcard(int id, boolean isFavourite) {
        flashcardService.editFlashcard(id, isFavourite);
    }
}
