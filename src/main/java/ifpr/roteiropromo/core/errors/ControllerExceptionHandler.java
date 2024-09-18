package ifpr.roteiropromo.core.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ServiceError.class)
    public ResponseEntity<StandardError> serviceNotPossible(ServiceError e, WebRequest webRequest){
        StandardError standardError = new StandardError();
        standardError.setError("Invalid request");
        standardError.setPath(webRequest.getDescription(false));
        standardError.setMessage(e.getMessage());
        standardError.setTimestamp(Instant.now());
        return new ResponseEntity<>(standardError, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(AuthenticationServerError.class)
    public ResponseEntity<StandardError> authenticationServiceNotPossible(AuthenticationServerError e, WebRequest webRequest){
        StandardError standardError = new StandardError();
        standardError.setError("Could not execute request");
        standardError.setPath(webRequest.getDescription(false));
        standardError.setMessage(e.getMessage());
        standardError.setTimestamp(Instant.now());
        return new ResponseEntity<>(standardError, e.getHttpStatusCode());
    }





}
