package pk.backend.flashcards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pk.backend.flashcards.entity.Set;

public interface SetRepository extends JpaRepository<Set, Integer> {
}
