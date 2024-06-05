package pk.wieik.flashcards.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Flashcard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int flashcardId;

    @Column(nullable = false)
    private String word;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private boolean isFavourite;

    @ManyToOne
    @JoinColumn(name = "set_id", referencedColumnName = "setId")
    private Set set;

    public Flashcard() {}

    public Flashcard(String word, String description, boolean isFavourite, Set set) {
        this.word = word;
        this.description = description;
        this.isFavourite = isFavourite;
        this.set = set;
    }
}