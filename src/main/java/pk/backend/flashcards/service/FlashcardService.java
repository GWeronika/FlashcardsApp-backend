package pk.backend.flashcards.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pk.backend.flashcards.entity.Flashcard;
import pk.backend.flashcards.entity.Set;
import pk.backend.flashcards.repository.FlashcardRepository;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FlashcardService {
    private final FlashcardRepository flashcardRepository;

    @Autowired
    public FlashcardService(FlashcardRepository flashcardRepository) {
        this.flashcardRepository = flashcardRepository;
    }

    public List<Flashcard> getAllFlashcards() {
        return flashcardRepository.findAll();
    }

    public Optional<Flashcard> getFlashcardById(int id) {
        try {
            if(id <= 0) {
                throw new IllegalArgumentException("FlashcardService: incorrect id");
            }
            return flashcardRepository.findById(id);
        } catch (Exception e) {
            System.err.println("Error retrieving flashcard");
            throw e;
        }
    }

    public List<Flashcard> getFlashcardsBySetId(int setId) {
        try {
            if(setId <= 0) {
                throw new IllegalArgumentException("FlashcardService: incorrect setId");
            }
            return flashcardRepository.getFlashcardsBySetId(setId);
        } catch (Exception e) {
            System.err.println("Error retrieving flashcard");
            throw e;
        }
    }

    public void addFlashcard(String word, String description, boolean isFavourite, Set set) {
        try {
            if(word == null || description == null || set == null) {
                throw new IllegalArgumentException("FlashcardService: incorrect data");
            }
            Flashcard flashcard = new Flashcard(word, description, isFavourite, set);
            flashcardRepository.save(flashcard);
        } catch (Exception e) {
            System.err.println("Error adding flashcard: " + e.getMessage());
        }
    }

    public void deleteFlashcardById(int id) {
        try {
            if(id <= 0) {
                throw new IllegalArgumentException("FlashcardService: incorrect id");
            }
            flashcardRepository.deleteById(id);
        } catch (Exception e) {
            System.err.println("Error deleting flashcard: " + e.getMessage());
        }
    }

    public void deleteFlashcardBySetId(int setId) {
        try {
            if(setId <= 0) {
                throw new IllegalArgumentException("FlashcardService: incorrect id");
            }
            flashcardRepository.deleteFlashcardsBySetId(setId);
        } catch (Exception e) {
            System.err.println("Error deleting flashcard: " + e.getMessage());
        }
    }

    public void editFlashcard(int id, String word, String description) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("FlashcardService: incorrect id");
            }
            Optional<Flashcard> existingFlashcardOptional = flashcardRepository.findById(id);
            if (existingFlashcardOptional.isPresent()) {
                Flashcard existingFlashcard = existingFlashcardOptional.get();
                existingFlashcard.setWord(word);
                existingFlashcard.setDescription(description);
                flashcardRepository.save(existingFlashcard);
            } else {
                throw new IllegalArgumentException("FlashcardService: flashcard not found with id " + id);
            }
        } catch (Exception e) {
            System.err.println("Error editing flashcard: " + e.getMessage());
            throw e;
        }
    }

    public void editFlashcardFavourite(int id, boolean isFavourite) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("FlashcardService: incorrect id");
            }
            Optional<Flashcard> existingFlashcardOptional = flashcardRepository.findById(id);
            if (existingFlashcardOptional.isPresent()) {
                Flashcard existingFlashcard = existingFlashcardOptional.get();
                existingFlashcard.setFavourite(isFavourite);

                flashcardRepository.save(existingFlashcard);
            } else {
                throw new IllegalArgumentException("FlashcardService: flashcard not found with id " + id);
            }
        } catch (Exception e) {
            System.err.println("Error editing flashcard: " + e.getMessage());
            throw e;
        }
    }

    public void importFlashcardsFromExcel(MultipartFile file, Set set) {
        try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                String englishWord = row.getCell(0).getStringCellValue();
                String polishWord = row.getCell(1).getStringCellValue();
                addFlashcard(englishWord, polishWord, false, set);
            }
        } catch (Exception e) {
            System.err.println("Error importing flashcards: " + e.getMessage());
        }
    }
}
