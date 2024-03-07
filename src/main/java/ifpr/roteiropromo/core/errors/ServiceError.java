package ifpr.roteiropromo.core.errors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceError extends RuntimeException{

    public ServiceError(){ super();}

    public ServiceError(String message) { super(message);}

}
