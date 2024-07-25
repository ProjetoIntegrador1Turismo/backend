package ifpr.roteiropromo.core.auth.domain;


import lombok.Data;

@Data
public class AuthenticatedUserDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String userType;
    private String authToken;

}
