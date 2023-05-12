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
//@Table(name = "gender")
//public class Gender {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "gender_id")
//    private Long genderId;
//
//    @Column(name = "name")
//    private String name;
//
//    @OneToMany(mappedBy = "gender", cascade = CascadeType.ALL)
//    @JsonBackReference
//    private Set<User> users;
//}
