package ifpr.roteiropromo.core.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ifpr.roteiropromo.core.errors.StandartError;
import jakarta.servlet.ServletException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        StandartError standartError = new StandartError();
        standartError.setTimestamp(Instant.now());
        standartError.setError("Unauthorized");
        standartError.setPath(request.getRequestURI());
        standartError.setMessage("Expired or Invalid Token.");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String jsonResponse = objectMapper.writeValueAsString(standartError);
        response.getWriter().write(jsonResponse);
    }
}


