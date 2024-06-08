package pk.backend.flashcards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pk.backend.flashcards.entity.Flashcard;

import java.util.List;
import java.util.Optional;

public interface FlashcardRepository extends JpaRepository<Flashcard, Integer> {
    @Query("SELECT f FROM Flashcard f WHERE f.set.setId = :setId")
    Optional<List<Flashcard>> getFlashcardsBySetId(@Param("setId") int setId);
}
