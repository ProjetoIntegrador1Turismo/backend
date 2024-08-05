package ifpr.roteiropromo.core.utils;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.warn("REQUISIÇÃO RECEBIDA SEM UM TOKEN PARA A ROTA -> {}", request.getRequestURI());
        }else {
            logger.warn("REQUISIÇÃO COM TOKEN NA ROTA-> {} - TOKEN {}", request.getRequestURI(), authHeader);
        }
        filterChain.doFilter(request, response);
    }
}
