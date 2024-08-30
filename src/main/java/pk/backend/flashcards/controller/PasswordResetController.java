package pk.backend.flashcards.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pk.backend.flashcards.entity.PasswordResetToken;
import pk.backend.flashcards.service.PasswordResetService;

import java.util.Optional;

@RestController
@RequestMapping("/api/reset")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;


    @GetMapping("/password")
    public void addToken(String email) {
        passwordResetService.initiatePasswordReset(email);
    }

    @GetMapping("/code")
    public Optional<PasswordResetToken> getCode(String email) {
        return passwordResetService.getCodeByEmail(email);
    }
}
