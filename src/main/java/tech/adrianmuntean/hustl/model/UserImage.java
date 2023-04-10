//package tech.adrianmuntean.hustl.model;
//
//import jakarta.persistence.*;
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
//    @ManyToOne(fetch = FetchType.LAZY, optional = true)
//    @JoinColumn(name = "user_id", nullable = true)
//    private User user;
//}
