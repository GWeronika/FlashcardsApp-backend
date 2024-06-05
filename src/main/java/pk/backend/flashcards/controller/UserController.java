package pk.wieik.flashcards.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pk.wieik.flashcards.entity.AppUser;
import pk.wieik.flashcards.service.UserService;

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
    public void addUser(String name, String password, String salt) {
        userService.addUser(name, password, salt);
    }

    @GetMapping("/delete")
    public void deleteUserById(int id) {
        userService.deleteUserById(id);
    }

    @GetMapping("/edit")
    public void editUser(int id, String newName, String newPassword) {
        userService.editUser(id, newName, newPassword);
    }
    @GetMapping("/edit/name")
    public void editUserName(int id, String name) {
        userService.editUserName(id, name);
    }

    @GetMapping("/edit/password")
    public void editUserPassword(int id, String password) {
        userService.editUserPassword(id, password);
    }
}
