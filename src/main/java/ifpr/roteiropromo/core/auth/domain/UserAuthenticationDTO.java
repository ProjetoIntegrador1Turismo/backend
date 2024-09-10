package ifpr.roteiropromo.core.auth.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuthenticationDTO {
    @Schema(description = "User email", example = "admin@admin.com")
    private String username;
    private String password;
}
