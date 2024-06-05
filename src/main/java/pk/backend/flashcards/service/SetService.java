package pk.backend.flashcards.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pk.backend.flashcards.entity.Set;
import pk.backend.flashcards.entity.AppUser;
import pk.backend.flashcards.repository.SetRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SetService {
    private final SetRepository setRepository;

    @Autowired
    public SetService(SetRepository setRepository) {
        this.setRepository = setRepository;
    }

    public List<Set> getAllSets() {
        return setRepository.findAll();
    }

    public Optional<Set> getSetById(int id) {
        try {
            if(id <= 0) {
                throw new IllegalArgumentException("SetRepository: incorrect id");
            }
            return setRepository.findById(id);
        } catch (Exception e) {
            System.err.println("Error retrieving set");
            throw e;
        }
    }

    public void addSet(String name, LocalDate date, AppUser user) {
        try {
            if(name == null || date == null || user == null) {
                throw new IllegalArgumentException("SetService: incorrect data");
            }
            Set set = new Set(name, date, user);
            setRepository.save(set);
        } catch (Exception e) {
            System.err.println("Error adding set: " + e.getMessage());
        }
    }

    public void deleteSetById(int id) {
        try {
            if(id <= 0) {
                throw new IllegalArgumentException("SetService: incorrect id");
            }
            setRepository.deleteById(id);
        } catch (Exception e) {
            System.err.println("Error deleting set: " + e.getMessage());
        }
    }

    public void editSet(int id, String newName) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("SetService: incorrect id");
            }
            Optional<Set> existingSetOptional = setRepository.findById(id);
            if (existingSetOptional.isPresent()) {
                Set existingSet = existingSetOptional.get();
                existingSet.setName(newName);

                setRepository.save(existingSet);
            } else {
                throw new IllegalArgumentException("SetService: set not found with id " + id);
            }
        } catch (Exception e) {
            System.err.println("Error editing set: " + e.getMessage());
            throw e;
        }
    }
}
