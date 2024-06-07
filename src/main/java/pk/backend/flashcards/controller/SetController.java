package pk.backend.flashcards.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pk.backend.flashcards.entity.Set;
import pk.backend.flashcards.entity.AppUser;
import pk.backend.flashcards.service.SetService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/set")
public class SetController {
    private final SetService setService;

    @Autowired
    public SetController(SetService setService) {
        this.setService = setService;
    }

    @GetMapping("/select/all")
    public List<Set> getAllSets() {
        return setService.getAllSets();
    }

    @GetMapping("/select/id")
    public Optional<Set> getSetById(int id) {
        return setService.getSetById(id);
    }

    @GetMapping("/select/userid")
    public Optional<List<Set>> getSetByUserId(int userID) {
        return setService.getSetByUserId(userID);
    }

    @GetMapping("/add")
    public void addSet(String name, LocalDate date, AppUser user) {
        setService.addSet(name, date, user);
    }

    @GetMapping("/add/description")
    public void addSetWithDescription(String name, LocalDate date, String description, AppUser user) {
        setService.addSetWithDescription(name, date, description, user);
    }

    @GetMapping("/delete")
    public void deleteSetById(int id) {
        setService.deleteSetById(id);
    }

    @GetMapping("/edit")
    public void editSet(int id, String newName) {
        setService.editSet(id, newName);
    }

    @GetMapping("/edit/description")
    public void editSetDescription(int id, String description) {
        setService.editSetDescription(id, description);
    }
}