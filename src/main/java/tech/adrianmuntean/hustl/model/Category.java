package tech.adrianmuntean.hustl.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "category")
    @JsonBackReference
    private List<Community> communities;

    @ManyToMany(mappedBy = "interests")
    @JsonBackReference
    private List<User> users;

    public Category(String name) {
        this.name = name;

        this.communities = new ArrayList<>();
        this.users = new ArrayList<>();
    }
}
