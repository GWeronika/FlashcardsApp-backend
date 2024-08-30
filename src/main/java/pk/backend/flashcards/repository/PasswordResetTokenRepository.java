package pk.backend.flashcards.repository;

import org.springframework.data.repository.CrudRepository;
import pk.backend.flashcards.entity.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, String> {
    Optional<PasswordResetToken> findByEmail(String email);
}