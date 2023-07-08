package tech.adrianmuntean.hustl.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReturnMessageDTO {
    private String timestamp;
    private String content;
    private String sender;
    private Long senderId;
}
