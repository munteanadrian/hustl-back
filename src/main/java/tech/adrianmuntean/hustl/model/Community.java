package tech.adrianmuntean.hustl.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "community")
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_id")
    private Long communityId;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "communities")
    @JsonBackReference
    private Set<User> users;

    @OneToMany(mappedBy = "recipient")
    @JsonManagedReference
    private Set<Message> messages;

    public Community(String name) {
        this.name = name;
        this.users = new HashSet<>();
    }
}
