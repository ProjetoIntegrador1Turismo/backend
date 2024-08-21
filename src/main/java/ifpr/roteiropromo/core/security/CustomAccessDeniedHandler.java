package ifpr.roteiropromo.core.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ifpr.roteiropromo.core.errors.StandartError;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {


    @Override
    public void handle(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, jakarta.servlet.ServletException {
        StandartError standartError = new StandartError();
        standartError.setTimestamp(Instant.now());
        standartError.setError("Access Denied ");
        standartError.setPath(request.getRequestURI());
        standartError.setMessage("You do not have the required permissions to access this end point.");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String responseJson = objectMapper.writeValueAsString(standartError);

        response.getWriter().write(responseJson);
    }
}

