package pk.backend.flashcards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pk.backend.flashcards.entity.Flashcard;

import java.util.List;

public interface FlashcardRepository extends JpaRepository<Flashcard, Integer> {
    @Query("SELECT f FROM Flashcard f WHERE f.set.setId = :setId")
    List<Flashcard> getFlashcardsBySetId(@Param("setId") int setId);

    @Modifying
    @Query("DELETE FROM Flashcard f WHERE f.set.setId = :setId")
    void deleteFlashcardsBySetId(@Param("setId") int setId);
}
