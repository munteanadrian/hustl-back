package tech.adrianmuntean.hustl.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_category",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonManagedReference
    private List<Category> interests;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "membership",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "community_id")
    )
    @JsonManagedReference
    private List<Community> communities;

    @OneToMany(mappedBy = "sender", fetch = FetchType.EAGER)
    @JsonBackReference
    private List<Message> messages;

    public User(String email, String password, String name, List<Category> interests) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.interests = interests;

        this.communities = new ArrayList<>();
        this.messages = new ArrayList<>();
    }
}
