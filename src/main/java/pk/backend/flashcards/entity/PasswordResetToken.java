package pk.backend.flashcards.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String verificationCode;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

}