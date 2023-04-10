package tech.adrianmuntean.hustl.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import tech.adrianmuntean.hustl.DTO.UserDTO;

import java.time.LocalDate;

// changes in: create, update, f

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

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "bio", nullable = true)
    private String bio;

    @ManyToOne(optional = false)
    @JoinColumn(name = "gender_id", nullable = false)
    @JsonManagedReference
    private Gender gender;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "location_id", nullable = false)
    @JsonManagedReference
    private Location location;


//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<UserImage> userImages;

//    @OneToOne(fetch = FetchType.LAZY, optional = true)
//    @JoinColumn(name = "preference_set_id", nullable = true)
//    private PreferenceSet preferenceSet;

//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinTable(name = "user_interest", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "interest_id"))
//    private Set<Interest> interests;


//    @ManyToMany
//    @JoinTable(name = "user_match", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "match_id"))
//    private Set<Match> matches;

    public void setFromDTO(UserDTO userDTO) {
        setEmail(userDTO.getEmail());
        setPassword(userDTO.getPassword());
        setName(userDTO.getName());
        setBirthday(userDTO.getBirthday());
        setBio(userDTO.getBio());
    }
}
