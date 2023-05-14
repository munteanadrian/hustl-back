package tech.adrianmuntean.hustl.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;


    @ManyToMany
    @JoinTable(
            name = "membership",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "community_id")
    )
    @JsonManagedReference
    private Set<Community> communities;

    @OneToMany(mappedBy = "sender")
    @JsonManagedReference
    private Set<Message> messages;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
