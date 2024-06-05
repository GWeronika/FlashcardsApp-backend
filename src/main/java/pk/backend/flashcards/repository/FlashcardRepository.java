package pk.wieik.flashcards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pk.wieik.flashcards.entity.Flashcard;

public interface FlashcardRepository extends JpaRepository<Flashcard, Integer> {
}
