//package tech.adrianmuntean.hustl.Archive;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import jakarta.persistence.*;
//import lombok.*;
//import tech.adrianmuntean.hustl.Archive.Gender;
//import tech.adrianmuntean.hustl.Archive.Location;
//import tech.adrianmuntean.hustl.Archive.UserImage;
//import tech.adrianmuntean.hustl.DTO.UserDTO;
//
//import java.awt.*;
//import java.time.LocalDate;
//import java.util.List;
//
//// finish with images, do the interests
//// start on the matching - match, match functionality (matches 2 users after each swiped)
//
//@ToString
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(name = "user")
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "user_id")
//    private Long userId;
//
//    @Column(name = "email", nullable = false)
//    private String email;
//
//    @Column(name = "password", nullable = false)
//    private String password;
//
//    @Column(name = "name", nullable = false)
//    private String name;
//
//    @JsonFormat(pattern = "dd-MM-yyyy")
//    @Column(name = "birthday", nullable = false)
//    private LocalDate birthday;
//
//    @Column(name = "bio", nullable = true)
//    private String bio;
//
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "gender_id", nullable = false)
//    @JsonManagedReference
//    private Gender gender;
//
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "location_id", nullable = false)
//    @JsonManagedReference
//    private Location location;
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    @JsonManagedReference
//    private List<UserImage> userImages;
////
////    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
////    @JoinTable(name = "user_interest", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "interest_id"))
////    private Set<Interest> interests;
////
////
////    @ManyToMany
////    @JoinTable(name = "user_match", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "match_id"))
////    private Set<Match> matches;
//
//    public void setFromDTO(UserDTO userDTO) {
//        setEmail(userDTO.getEmail());
//        setPassword(userDTO.getPassword());
//        setName(userDTO.getName());
//        setBirthday(userDTO.getBirthday());
//        setBio(userDTO.getBio());
//    }
//
//    public String addImage(Image image) {
//        String response;
//
//        if (userImages.size() < 6) {
//            userImages.add(image);
//            image.setUser(this);
//            response = "OK";
//        } else {
//            response = "Can't have more than 6 images";
//        }
//        return response;
//    }
//}
