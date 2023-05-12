//package tech.adrianmuntean.hustl.Archive;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import jakarta.persistence.*;
//import lombok.Getter;
//import tech.adrianmuntean.hustl.model.User;
//
//import java.util.Set;
//
//@Entity
//@Getter
//@Table(name = "location")
//public class Location {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "location_id")
//    private Long locationId;
//
//    @Column(name = "country", nullable = false)
//    private String country;
//
//    @Column(name = "iso", nullable = false)
//    private String iso;
//
//    //    @Column(name = "city", nullable = false)
////    private String city;
//    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
//    @JsonBackReference
//    private Set<User> users;
//}
