package ifpr.roteiropromo.core.errors;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@Data
public class StandartError {
    private Instant timestamp;
    private String error;
    private String message;
    private String path;
}
