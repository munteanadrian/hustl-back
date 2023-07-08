package tech.adrianmuntean.hustl.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MessageDTO {
    private Long groupId;
    private Long userId;
    private String message;
}
