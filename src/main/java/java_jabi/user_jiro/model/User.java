package java_jabi.user_jiro.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String login;
    private String password;
    private Role role;
    private Boolean isDeleted;
}
