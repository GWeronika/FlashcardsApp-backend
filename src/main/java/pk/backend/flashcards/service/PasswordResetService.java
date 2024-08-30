package pk.backend.flashcards.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pk.backend.flashcards.entity.PasswordResetToken;
import pk.backend.flashcards.repository.PasswordResetTokenRepository;

import java.util.Optional;
import java.util.Properties;
import java.util.UUID;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class PasswordResetService {

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    public PasswordResetService(PasswordResetTokenRepository passwordResetTokenRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    public Optional<PasswordResetToken> getCodeByEmail(String email) {
        try {
            if (email == null) {
                throw new IllegalArgumentException("PasswordResetService: incorrect email");
            }
            return passwordResetTokenRepository.findByEmail(email);
        } catch (Exception e) {
            System.err.println("Error retrieving code by email: " + e.getMessage());
            throw e;
        }
    }

    public void initiatePasswordReset(String email) {
        String verificationCode = generateVerificationCode();

        PasswordResetToken token = passwordResetTokenRepository.findByEmail(email)
                .orElse(new PasswordResetToken());

        token.setEmail(email);
        token.setVerificationCode(verificationCode);
        passwordResetTokenRepository.save(token);

        sendPasswordResetEmail(email, verificationCode);
    }

    private String generateVerificationCode() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    private void sendPasswordResetEmail(String email, String verificationCode) {
        final String senderEmail = "email@gmail.com";
        final String password = "password";

        String host = "smtp.gmail.com";
        int port = 587;

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, password);
            }
        });
        session.setDebug(true);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Password Reset Verification Code");
            message.setText("Your password reset verification code is: " + verificationCode);

            Transport.send(message);

            System.out.println("Password reset verification code sent successfully to " + email);
        } catch (MessagingException e) {
            System.out.println("Error occurred while sending email: " + e.getMessage());
        }
    }
}