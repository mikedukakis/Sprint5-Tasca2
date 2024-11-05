package imf.virtualpet.dto;

import imf.virtualpet.domain.user.Role;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequestDTO {

    private String username;
    private String password;
    private Role role = Role.USER;

    @Builder
    public RegisterRequestDTO(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role != null ? role : Role.USER;
    }
}
