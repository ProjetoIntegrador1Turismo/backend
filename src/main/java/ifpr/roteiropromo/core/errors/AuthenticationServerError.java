package ifpr.roteiropromo.core.errors;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class AuthenticationServerError extends RuntimeException{

    private String message;
    private HttpStatus httpStatusCode;


    public AuthenticationServerError(){ super();}

    public AuthenticationServerError(String message, HttpStatus httpStatusCode) {
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

}
