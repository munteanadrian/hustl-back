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
@Table(name = "community")
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_id")
    private Long communityId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToMany(mappedBy = "communities")
    @JsonBackReference
    private List<User> users;

    @OneToMany(mappedBy = "recipient", fetch = FetchType.EAGER)
    @JsonBackReference
    private List<Message> messages;

    @ManyToOne
    @JsonManagedReference
    private Category category;

    public Community(String name, Category category, String description) {
        this.name = name;
        this.category = category;
        this.description = description;

        this.users = new ArrayList<>();
        this.messages = new ArrayList<>();
    }
}
