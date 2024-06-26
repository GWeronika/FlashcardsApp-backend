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

    @OneToOne
    @JoinColumn(name = "author_id", referencedColumnName = "userID")
    private AppUser user;

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