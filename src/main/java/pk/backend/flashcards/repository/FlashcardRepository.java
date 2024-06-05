package pk.backend.flashcards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pk.backend.flashcards.entity.Flashcard;

public interface FlashcardRepository extends JpaRepository<Flashcard, Integer> {
}
