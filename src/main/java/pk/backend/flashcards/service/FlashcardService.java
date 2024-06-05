package pk.wieik.flashcards.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pk.wieik.flashcards.entity.Flashcard;
import pk.wieik.flashcards.entity.Set;
import pk.wieik.flashcards.repository.FlashcardRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FlashcardService {
    private final FlashcardRepository flashcardRepository;

    @Autowired
    public FlashcardService(FlashcardRepository flashcardRepository) {
        this.flashcardRepository = flashcardRepository;
    }

    public List<Flashcard> getAllFlashcards() {
        return flashcardRepository.findAll();
    }

    public Optional<Flashcard> getFlashcardById(int id) {
        try {
            if(id <= 0) {
                throw new IllegalArgumentException("FlashcardRepository: incorrect id");
            }
            return flashcardRepository.findById(id);
        } catch (Exception e) {
            System.err.println("Error retrieving flashcard");
            throw e;
        }
    }

    public void addFlashcard(String word, String description, boolean isFavourite, Set set) {
        try {
            if(word == null || description == null || set == null) {
                throw new IllegalArgumentException("FlashcardService: incorrect data");
            }
            Flashcard flashcard = new Flashcard(word, description, isFavourite, set);
            flashcardRepository.save(flashcard);
        } catch (Exception e) {
            System.err.println("Error adding flashcard: " + e.getMessage());
        }
    }

    public void deleteFlashcardById(int id) {
        try {
            if(id <= 0) {
                throw new IllegalArgumentException("FlashcardService: incorrect id");
            }
            flashcardRepository.deleteById(id);
        } catch (Exception e) {
            System.err.println("Error deleting flashcard: " + e.getMessage());
        }
    }

    public void editFlashcard(int id, boolean isFavourite) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("FlashcardService: incorrect id");
            }
            Optional<Flashcard> existingFlashcardOptional = flashcardRepository.findById(id);
            if (existingFlashcardOptional.isPresent()) {
                Flashcard existingFlashcard = existingFlashcardOptional.get();
                existingFlashcard.setFavourite(isFavourite);

                flashcardRepository.save(existingFlashcard);
            } else {
                throw new IllegalArgumentException("FlashcardService: flashcard not found with id " + id);
            }
        } catch (Exception e) {
            System.err.println("Error editing flashcard: " + e.getMessage());
            throw e;
        }
    }
}
