package pk.wieik.flashcards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pk.wieik.flashcards.entity.Set;

public interface SetRepository extends JpaRepository<Set, Integer> {
}
