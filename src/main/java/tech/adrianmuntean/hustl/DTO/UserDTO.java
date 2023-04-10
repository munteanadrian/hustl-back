package tech.adrianmuntean.hustl.DTO;

import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
    private String email;
    private String password;
    private String name;
    private String birthday;
    private String bio;
    private Long genderId;
    private Long locationId;

    public LocalDate getBirthday() {
        return LocalDate.parse(birthday, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}
