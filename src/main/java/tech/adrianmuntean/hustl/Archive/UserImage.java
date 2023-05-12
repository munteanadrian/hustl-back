//package tech.adrianmuntean.hustl.Archive;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import jakarta.persistence.*;
//import tech.adrianmuntean.hustl.model.User;
//
//@Entity
//@Table(name = "user_image")
//public class UserImage {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "user_image_id")
//    private Long userImageId;
//
//    @Lob
//    @Column(name = "image", nullable = false)
//    private byte[] image;
//
//    @ManyToOne(optional = true)
//    @JoinColumn(name = "user_id", nullable = true)
//    @JsonBackReference
//    private User user;
//}
