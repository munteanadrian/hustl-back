package tech.adrianmuntean.hustl.utils.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JWTResponse {
    private Long id;
    private String type = "Bearer";
    private String token;
    private String email;

    public JWTResponse(Long id, String token, String email) {
        this.id = id;
        this.token = token;
        this.email = email;
    }
}
