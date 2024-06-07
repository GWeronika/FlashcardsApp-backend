package pk.backend.flashcards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pk.backend.flashcards.entity.AppUser;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Integer> {
    boolean existsByName(String name);
    Optional<AppUser> findByEmail(String email);
}
