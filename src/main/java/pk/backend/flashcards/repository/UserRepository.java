package pk.wieik.flashcards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pk.wieik.flashcards.entity.AppUser;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Integer> {
}
