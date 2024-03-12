package ifpr.roteiropromo.core.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;




@Log4j2
public class JwtTokenHandler {


    //Extrai os dados do usuario a partir do token enviado na requisição
    public void getUserDataFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verifica se a autenticação é do tipo JWT (se o usuário estiver autenticado com um token JWT)
        if (authentication instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;

            //Extrai o token
            String token = jwtAuthenticationToken.getToken().getTokenValue();

            //Extrai os dados do token
            String idKeyclock = jwtAuthenticationToken.getName();
            Object userEmail = jwtAuthenticationToken.getTokenAttributes().get("email");
            Object userName = jwtAuthenticationToken.getTokenAttributes().get("given_name");


            log.info("Token JWT: " + token);
            log.info("Email: " + userEmail);
            log.info("User id keycloack: " + idKeyclock);
            log.info("User name: " + userName);
        } else {
            // Caso não seja um token JWT, você pode lidar de acordo com a lógica da sua aplicação
            System.out.println("Não foi encontrado um token JWT na requisição.");
        }
    }




}
