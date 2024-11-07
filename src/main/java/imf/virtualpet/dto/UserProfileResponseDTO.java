package imf.virtualpet.dto;

import imf.virtualpet.domain.user.Role;
import lombok.Data;

@Data
public class UserProfileResponseDTO {
    private String username;
    private Role role;
}
