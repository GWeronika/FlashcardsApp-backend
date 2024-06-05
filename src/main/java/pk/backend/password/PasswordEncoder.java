package pk.backend.password;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordEncoder {
    public static String hashPassword(String password, String salt) {
        try {
            String passwordWithSalt = password + salt;
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = md.digest(passwordWithSalt.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch(NoSuchAlgorithmException e) {
            System.err.println("Error hashing password: " + e.getMessage());
            return null;
        }
    }
}