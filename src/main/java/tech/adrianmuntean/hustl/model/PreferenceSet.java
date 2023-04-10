//package tech.adrianmuntean.hustl.model;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "preference_set")
//public class PreferenceSet {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "preference_set_id")
//    private Long preferenceSetId;
//
//    @Column(name = "dark_mode", nullable = false)
//    private Boolean darkMode;
//
//    @OneToOne(mappedBy = "preferenceSet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private User user;
//}
