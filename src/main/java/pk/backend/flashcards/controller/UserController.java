package pk.backend.flashcards.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pk.backend.flashcards.entity.AppUser;
import pk.backend.flashcards.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/select/all")
    public List<AppUser> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/select/id")
    public Optional<AppUser> getUserById(int id) {
        return userService.getUserById(id);
    }

    @GetMapping("/select/email")
    public ResponseEntity<AppUser> getUserByEmail(@RequestParam String email) {
        try {
            Optional<AppUser> userOptional = userService.getUserByEmail(email);
            return userOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestParam String name, @RequestParam String email, @RequestParam String password) {
        try {
            userService.addUser(name, email, password);
            return ResponseEntity.ok("User added successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding the user");
        }
    }

    @GetMapping("/delete")
    public void deleteUserById(int id) {
        userService.deleteUserById(id);
    }

    @PutMapping("/edit/password")
    public ResponseEntity<Void> editUserPassword(@RequestParam int id, @RequestParam String password) {
        try {
            userService.editUserPassword(id, password);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/edit/profile")
    public ResponseEntity<Void> editUserProfile(@RequestParam int id, @RequestParam String name, @RequestParam String email) {
        try {
            userService.editUserProfile(id, name, email);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AppUser> loginUser(@RequestParam String username, @RequestParam String password) {
        Optional<AppUser> userOptional = userService.authenticateUser(username, password);
        return userOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/verify-password")
    public ResponseEntity<Boolean> verifyPassword(@RequestParam int id, @RequestParam String password) {
        boolean isValid = userService.verifyPassword(id, password);
        if (isValid) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
    }

}
