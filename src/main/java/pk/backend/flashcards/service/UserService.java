package pk.wieik.flashcards.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pk.wieik.flashcards.entity.AppUser;
import pk.wieik.flashcards.repository.UserRepository;

import java.util.List;
import java.util.Optional;

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

    public void addUser(String name, String password, String salt) {
        try {
            if(name == null || password == null || salt == null) {
                throw new IllegalArgumentException("UserService: incorrect data");
            }
            AppUser user = new AppUser(name, password, salt);
            userRepository.save(user);
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

    public void editUserName(int id, String newName) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("UserService: incorrect id");
            }
            Optional<AppUser> existingUserOptional = userRepository.findById(id);
            if (existingUserOptional.isPresent()) {
                AppUser existingUser = existingUserOptional.get();
                existingUser.setName(newName);

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
}
