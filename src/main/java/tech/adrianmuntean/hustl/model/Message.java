package tech.adrianmuntean.hustl.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long messageId;
    @Column(name = "timestamp", nullable = false)
    private String timestamp;
    @Column(name = "content", nullable = false)
    private String content;

    //    add to user
    @ManyToOne
    @JsonBackReference
    private User sender;

    //    add to community
    @ManyToOne
    @JsonBackReference
    private Community recipient;

    public Message(String content, User sender, Community recipient) {
        this.timestamp = String.valueOf(System.currentTimeMillis());
        this.content = content;
        this.sender = sender;
        this.recipient = recipient;
    }
}
