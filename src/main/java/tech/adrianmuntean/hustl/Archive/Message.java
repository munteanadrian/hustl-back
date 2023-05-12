//package tech.adrianmuntean.hustl.model;
//
//import jakarta.persistence.*;
//
//import java.time.LocalDate;
//
//@Entity
//@Table(name = "message")
//public class Message {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "message_id")
//    private Long messageId;
//
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "match_id", nullable = false)
//    private Match match;
//
//    //    json format, automatically dateTime get when created
//    @Column(name = "date_time", nullable = false)
//    private LocalDate dateTime;
//
//    @Column(name = "text", nullable = false)
//    private String text;
//}
