package pk.backend.flashcards.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@Entity
public class Set {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int setId;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate date;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "userID", nullable = false)
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "categoryID", nullable = false)
    private Category category;

    public Set() {}

    public Set(String name, LocalDate date, AppUser user) {
        this.name = name;
        this.date = date;
        this.user = user;
    }

    public Set(String name, LocalDate date, String description, AppUser user) {
        this.name = name;
        this.date = date;
        this.user = user;
        this.description = description;
    }

    public Set(String name, LocalDate date, String description, AppUser user, Category category) {
        this.name = name;
        this.date = date;
        this.user = user;
        this.description = description;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Set{" +
                "setId=" + setId +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", user=" + user +
                '}';
    }
}