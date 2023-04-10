package tech.adrianmuntean.hustl.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "ISO", nullable = false)
    private String ISO;

//    @Column(name = "city", nullable = false)
//    private String city;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<User> users;
}
