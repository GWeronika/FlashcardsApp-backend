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

    public void addUser(String name, String password) {
        try {
            if(name == null || password == null) {
                throw new IllegalArgumentException("UserService: incorrect data");
            }
            if(userRepository.existsByName(name)) {
                throw new IllegalArgumentException("UserService: user with this name already exists");
            }
            if(isPasswordValid(password)) {
                String salt = SaltGenerator.generateSalt();
                String hashedPassword = PasswordEncoder.hashPassword(password, salt);
                AppUser user = new AppUser(name, hashedPassword, salt);
                userRepository.save(user);
            } else {
                throw new IllegalArgumentException("UserService: password not valid");
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

    public void editUser(int id, String newName, String newPassword) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("UserService: incorrect id");
            }
            Optional<AppUser> existingUserOptional = userRepository.findById(id);
            if (existingUserOptional.isPresent()) {
                AppUser existingUser = existingUserOptional.get();
                existingUser.setName(newName);
                existingUser.setPassword(newPassword);

                userRepository.save(existingUser);
            } else {
                throw new IllegalArgumentException("UserService: user not found with id " + id);
            }
        } catch (Exception e) {
            System.err.println("Error editing user: " + e.getMessage());
            throw e;
        }
    }

    public void editUserPassword(int id, String newPassword) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("UserService: incorrect id");
            }
            if(!isPasswordValid(newPassword)) {
                throw new IllegalArgumentException("UserService: password not valid");
            }
            Optional<AppUser> existingUserOptional = userRepository.findById(id);
            if (existingUserOptional.isPresent()) {
                AppUser existingUser = existingUserOptional.get();
                existingUser.setPassword(newPassword);

                userRepository.save(existingUser);
            } else {
                throw new IllegalArgumentException("UserService: user not found with id " + id);
            }
        } catch (Exception e) {
            System.err.println("Error editing user: " + e.getMessage());
            throw e;
        }
    }

    public Optional<AppUser> authenticateUser(String name, String password) {
        Optional<AppUser> optionalUser = userRepository.findByName(name);
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

    private boolean isPasswordValid(String password) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
