package tech.adrianmuntean.hustl.model;

import jakarta.persistence.*;
import lombok.*;


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

//    Another table called communities
//    Intersection table called community_membership

//    Another table for the chats (one user - many messages, one message one user)
//    Intersection table or something between user and community membership


    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
