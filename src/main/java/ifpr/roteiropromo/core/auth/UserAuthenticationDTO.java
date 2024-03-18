package ifpr.roteiropromo.core.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuthenticationDTO {
    private String clientId;
    private String username;
    private String password;
    private String grantType;
}
