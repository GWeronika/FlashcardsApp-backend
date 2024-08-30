package pk.backend.flashcards.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pk.backend.flashcards.entity.AppUser;
import pk.backend.flashcards.repository.UserRepository;
import pk.backend.password.PasswordEncoder;
import pk.backend.password.SaltGenerator;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<AppUser> getUserById(int id) {
        try {
            if(id <= 0) {
                throw new IllegalArgumentException("UserRepository: incorrect id");
            }
            return userRepository.findById(id);
        } catch (Exception e) {
            System.err.println("Error retrieving user");
            throw e;
        }
    }

    public Optional<AppUser> getUserByEmail(String email) {
        try {
            if(email.isEmpty()) {
                throw new IllegalArgumentException("UserRepository: incorrect email");
            }
            return userRepository.findAppUserByEmail(email);
        } catch (Exception e) {
            System.err.println("Error retrieving user");
            throw e;
        }
    }

    public void addUser(String name, String email, String password) {
        try {
            if(name == null || password == null) {
                throw new IllegalArgumentException("UserService: incorrect data");
            }
            if(userRepository.existsByName(name)) {
                throw new IllegalArgumentException("UserService: user with this name already exists");
            }
            if(isPasswordValid(password) && isEmailValid(email)) {
                String salt = SaltGenerator.generateSalt();
                String hashedPassword = PasswordEncoder.hashPassword(password, salt);
                AppUser user = new AppUser(name, email, hashedPassword, salt);
                userRepository.save(user);
            } else {
                throw new IllegalArgumentException("UserService: password or email not valid");
            }
        } catch (Exception e) {
            System.err.println("Error adding user: " + e.getMessage());
        }
    }

    public void deleteUserById(int id) {
        try {
            if(id <= 0) {
                throw new IllegalArgumentException("UserService: incorrect id");
            }
            userRepository.deleteById(id);
        } catch (Exception e) {
            System.err.println("Error deleting user: " + e.getMessage());
        }
    }

    public void editUserPassword(int id, String newPassword) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("UserService: incorrect id");
            }
            if (!isPasswordValid(newPassword)) {
                throw new IllegalArgumentException("UserService: password not valid");
            }
            Optional<AppUser> existingUserOptional = userRepository.findById(id);
            if (existingUserOptional.isPresent()) {
                AppUser existingUser = existingUserOptional.get();
                String salt = SaltGenerator.generateSalt();
                String hashedPassword = PasswordEncoder.hashPassword(newPassword, salt);
                existingUser.setPassword(hashedPassword);
                existingUser.setSalt(salt);

                userRepository.save(existingUser);
            } else {
                throw new IllegalArgumentException("UserService: user not found with id " + id);
            }
        } catch (Exception e) {
            System.err.println("Error editing user: " + e.getMessage());
            throw e;
        }
    }

    public void editUserProfile(int id, String newUsername, String newEmail) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("UserService: incorrect id");
            }
            if(!isEmailValid(newEmail)) {
                throw new IllegalArgumentException("UserService: email not valid");
            }
            Optional<AppUser> existingUserOptional = userRepository.findById(id);
            if (existingUserOptional.isPresent()) {
                AppUser existingUser = existingUserOptional.get();
                existingUser.setName(newUsername);
                existingUser.setEmail(newEmail);

                userRepository.save(existingUser);
            } else {
                throw new IllegalArgumentException("UserService: user not found with id " + id);
            }
        } catch (Exception e) {
            System.err.println("Error editing user: " + e.getMessage());
            throw e;
        }
    }

    public Optional<AppUser> authenticateUser(String username, String password) {
        Optional<AppUser> optionalUser = userRepository.findByName(username);
        if(optionalUser.isPresent()) {
            AppUser user = optionalUser.get();
            String salt = user.getSalt();
            String hashedPassword = PasswordEncoder.hashPassword(password, salt);
            if(hashedPassword != null && hashedPassword.equals(user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public boolean verifyPassword(int id, String password) {
        Optional<AppUser> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            AppUser user = userOptional.get();
            String salt = user.getSalt();
            String hashedPassword = PasswordEncoder.hashPassword(password, salt);
            return hashedPassword != null && hashedPassword.equals(user.getPassword());
        }
        return false;
    }

    private boolean isPasswordValid(String password) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private boolean isEmailValid(String email) {
        String emailPattern = "^[a-zA-Z0-9._]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
