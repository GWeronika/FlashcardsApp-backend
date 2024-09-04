package pk.backend.flashcards.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pk.backend.flashcards.entity.Set;
import pk.backend.flashcards.entity.AppUser;
import pk.backend.flashcards.service.FlashcardService;
import pk.backend.flashcards.service.SetService;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/set")
public class SetController {
    private final SetService setService;
    private final FlashcardService flashcardService;

    @Autowired
    public SetController(SetService setService, FlashcardService flashcardService) {
        this.setService = setService;
        this.flashcardService = flashcardService;
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
    public Optional<List<Set>> getSetByUserId(@RequestParam int userID) {
        return setService.getSetByUserId(userID);
    }

    @GetMapping("/add")
    public void addSet(String name, LocalDate date, AppUser user) {
        setService.addSet(name, date, user);
    }

    @PostMapping("/add/description")
    public ResponseEntity<?> addSetWithDescription(@RequestParam String name, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                   @RequestParam String description, @RequestParam String userJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            AppUser user = objectMapper.readValue(userJson, AppUser.class);

            Optional<Set> newSet = setService.addSetWithDescription(name, date, description, user);
            return ResponseEntity.ok(newSet);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Problems occurred adding the set");
        }
    }

    @DeleteMapping("/delete")
    public void deleteSetById(@RequestParam int id) {
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

    @GetMapping("/search")
    public List<Set> searchSets(@RequestParam String searchTerm) {
        return setService.searchSets(searchTerm);
    }

    @GetMapping("/sort-date")
    public List<Set> sortSetsByDate(boolean ascending) {
        return setService.sortSetsByDate(ascending);
    }


    @PostMapping("/import")
    public ResponseEntity<String> importSet(@RequestParam("file") MultipartFile file, @RequestParam int setId) {
        try {
            Optional<Set> optionalSet = setService.getSetById(setId);
            if (optionalSet.isPresent()) {
                flashcardService.importFlashcardsFromExcel(file, optionalSet.get());
                return ResponseEntity.ok("Flashcards imported successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Set not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error importing flashcards: " + e.getMessage());
        }
    }

}