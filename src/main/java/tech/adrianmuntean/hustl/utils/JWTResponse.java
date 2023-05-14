package tech.adrianmuntean.hustl.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JWTResponse {
    private String type = "Bearer";
    private String token;

    public JWTResponse(String token) {
        this.token = token;
    }
}
