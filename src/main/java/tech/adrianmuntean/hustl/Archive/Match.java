//package tech.adrianmuntean.hustl.model;
//
//import jakarta.persistence.*;
//
//import java.time.LocalDate;
//
//@Entity
//@Table(name = "match_table")
//public class Match {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "match_id")
//    private Long matchId;
//
//    @ManyToMany(mappedBy = "matches")
//    private Set<User> users;
//
//    //  jsonFormat, auto generate
//    @Column(name = "date", nullable = false)
//    private LocalDate date;
//
//    @OneToMany(mappedBy = "match", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<Message> messages;
//}
