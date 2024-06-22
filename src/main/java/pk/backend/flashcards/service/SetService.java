package pk.backend.flashcards.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pk.backend.flashcards.entity.Set;
import pk.backend.flashcards.entity.AppUser;
import pk.backend.flashcards.repository.SetRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Optional<List<Set>> getSetByUserId(int userID) {
        try {
            if(userID <= 0) {
                throw new IllegalArgumentException("SetRepository: incorrect userID");
            }
            return setRepository.getSetByUserId(userID);
        } catch (Exception e) {
            System.err.println("Error retrieving sets");
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

    public Optional<Set> addSetWithDescription(String name, LocalDate date, String description, AppUser user) {
        try {
            if (name == null || name.trim().isEmpty() || description == null || description.trim().isEmpty() || date == null) {
                throw new IllegalArgumentException("SetService: incorrect data");
            }
            Set set = new Set(name, date, description, user);
            setRepository.save(set);
            return Optional.of(set);
        } catch (Exception e) {
            System.err.println("Error adding set: " + e.getMessage());
        }
        return Optional.empty();
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

    public void editSetDescription(int id, String description) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("SetService: incorrect id");
            }
            Optional<Set> existingSetOptional = setRepository.findById(id);
            if (existingSetOptional.isPresent()) {
                Set existingSet = existingSetOptional.get();
                existingSet.setDescription(description);

                setRepository.save(existingSet);
            } else {
                throw new IllegalArgumentException("SetService: set not found with id " + id);
            }
        } catch (Exception e) {
            System.err.println("Error editing set: " + e.getMessage());
            throw e;
        }
    }

    public List<Set> searchSets(String searchTerm) {
        return setRepository.searchSets(searchTerm);
    }

    public List<Set> sortSetsByDate(boolean ascending) {
        List<Set> sets = setRepository.findAll();
        return sets.stream()
                .sorted(ascending ? Comparator.comparing(Set::getDate) : Comparator.comparing(Set::getDate).reversed())
                .collect(Collectors.toList());
    }
}
