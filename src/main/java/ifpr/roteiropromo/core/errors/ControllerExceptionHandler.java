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
    public ResponseEntity<StandartError> serviceNotPossible(ServiceError e, WebRequest webRequest){
        StandartError standartError = new StandartError();
        standartError.setError("Invalid request");
        standartError.setPath(webRequest.getDescription(false));
        standartError.setMessage(e.getMessage());
        standartError.setTimestamp(Instant.now());
        return new ResponseEntity<>(standartError, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(AuthenticationServerError.class)
    public ResponseEntity<StandartError> authenticationServiceNotPossible(AuthenticationServerError e, WebRequest webRequest){
        StandartError standartError = new StandartError();
        standartError.setError("Could not execute request");
        standartError.setPath(webRequest.getDescription(false));
        standartError.setMessage(e.getMessage());
        standartError.setTimestamp(Instant.now());
        return new ResponseEntity<>(standartError, e.getHttpStatusCode());
    }





}
