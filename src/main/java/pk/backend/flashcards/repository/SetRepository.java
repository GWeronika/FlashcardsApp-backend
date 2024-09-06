package pk.backend.flashcards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pk.backend.flashcards.entity.Set;

import java.util.List;
import java.util.Optional;

public interface SetRepository extends JpaRepository<Set, Integer> {
    @Query("SELECT s FROM Set s WHERE s.user.userId = :userId")
    Optional<List<Set>> getSetByUserId(@Param("userId") int userId);

    @Query("SELECT s FROM Set s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(s.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Set> searchSets(@Param("searchTerm") String searchTerm);

    @Query("SELECT s FROM Set s WHERE s.category.categoryId = :categoryId")
    List<Set> getSetsByCategoryId(@Param("categoryId") int categoryId);
}
