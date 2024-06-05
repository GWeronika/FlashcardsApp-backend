package pk.wieik.flashcards.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pk.wieik.flashcards.entity.Set;
import pk.wieik.flashcards.entity.AppUser;
import pk.wieik.flashcards.service.SetService;

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

    @GetMapping("/add")
    public void addSet(String name, LocalDate date, AppUser user) {
        setService.addSet(name, date, user);
    }

    @GetMapping("/delete")
    public void deleteSetById(int id) {
        setService.deleteSetById(id);
    }

    @GetMapping("/edit")
    public void editSet(int id, String newName) {
        setService.editSet(id, newName);
    }
}