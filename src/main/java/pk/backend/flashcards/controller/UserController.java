package pk.backend.flashcards.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pk.backend.flashcards.entity.AppUser;
import pk.backend.flashcards.service.UserService;

import java.util.List;
import javax.servlet.http.HttpSession;
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

    @GetMapping("/edit/password")
    public void editUserPassword(int id, String password) {
        userService.editUserPassword(id, password);
    }

    @PostMapping("/authentication")
    public ResponseEntity<?> authenticateUser(@RequestBody String email, @RequestBody String password, HttpSession session) {
        Optional<AppUser> user = userService.authenticateUser(email, password);
        if (user.isPresent()) {
            session.setAttribute("userId", user.get().getUserId());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
