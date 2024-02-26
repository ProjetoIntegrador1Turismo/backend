package ifpr.roteiropromo.core.erros;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.time.Instant;
@Log4j2
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<StandardError> serviceError(ServiceException erro, HttpServletRequest httpServletRequest){
        log.info("Chamou o metodo pertinente ao erro disparado.");
        log.info("Mensagem do erro que veio:" + erro.getMessage());
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setError("Cant complete request");
        error.setMessage(erro.getMessage());
        error.setPath(httpServletRequest.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}
