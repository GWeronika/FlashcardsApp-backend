package pk.wieik.flashcards.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String salt;

    public AppUser() {}

    public AppUser(String name, String password, String salt) {
        this.name = name;
        this.password = password;
        this.salt = salt;
    }
}