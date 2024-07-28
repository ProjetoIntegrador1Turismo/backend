package ifpr.roteiropromo.core.errors;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ResourceServerError extends RuntimeException{

    private String message;
    private HttpStatus httpStatusCode;


    public ResourceServerError(){ super();}

    public ResourceServerError(String message, HttpStatus httpStatusCode) {
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

}
