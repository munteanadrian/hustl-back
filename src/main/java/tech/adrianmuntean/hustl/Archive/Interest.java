//package tech.adrianmuntean.hustl.model;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "interest")
//public class Interest {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long interestId;
//
//    @Column(name = "name", nullable = false)
//    private String name;
//
//    @ManyToMany(mappedBy = "interests", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<User> users;
//}
