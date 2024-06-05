package pk.backend.flashcards.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/add")
    public void addUser(String name, String password) {
        userService.addUser(name, password);
    }

    @GetMapping("/delete")
    public void deleteUserById(int id) {
        userService.deleteUserById(id);
    }

    @GetMapping("/edit")
    public void editUser(int id, String newName, String newPassword) {
        userService.editUser(id, newName, newPassword);
    }

    @GetMapping("/edit/password")
    public void editUserPassword(int id, String password) {
        userService.editUserPassword(id, password);
    }

    @GetMapping("/authentication")
    public Optional<AppUser> authenticateUser(String name, String password) {
        return userService.authenticateUser(name, password);
    }
}
