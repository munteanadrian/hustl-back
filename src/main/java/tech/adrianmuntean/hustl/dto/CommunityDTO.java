package tech.adrianmuntean.hustl.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommunityDTO {
    private Long communityId;
    private String name;
    private String category;
    private String description;
}
