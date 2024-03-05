package ifpr.roteiropromo.core.security;


import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.Map;


public class JwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {


    //Metodo para "traduzir" o token enviado pelo Keycloack para o Spring Secutiry
    //O Spring Security lê as roles (permissões) do usuário com a seguinte sintaxe -> ROLE_ADMIN, ROLE_USER, ETC
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Map<String, Collection<String>> realmAcess = jwt.getClaim("realm_access"); //Captura o realm_access do token
        Collection<String> roles = realmAcess.get("roles"); //Retira somente as roles contidas agora no realmAcess
        var grants = roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).toList(); //Tranforma em lista a roles com a sintaxe ROLE_ADMIN,etc
        return new JwtAuthenticationToken(jwt, grants); //Gera o token com os dados formatados para o Spring
    }
}

/*CORPO DO TOKEN COMPLETO GERADO PELO KEYCLOAK

{
  "exp": 1709575584,
  "iat": 1709575284,
  "jti": "751c2538-bb62-4026-9ec9-4c74f515f568",
  "iss": "http://localhost:8080/realms/SpringBootKeycloak",
  "aud": "account",
  "sub": "961518cb-5b11-482b-84d1-364c98bdf459",
  "typ": "Bearer",
  "azp": "login-app",
  "session_state": "b3749d19-c90b-447c-b755-db85407ea22a",
  "acr": "1",
  "allowed-origins": [
    "http://localhost:8080/*"
  ],
  "realm_access": {
    "roles": [
      "default-roles-springbootkeycloak",
      "offline_access",
      "admin",
      "uma_authorization"
    ]
  },
  "resource_access": {
    "account": {
      "roles": [
        "manage-account",
        "manage-account-links",
        "view-profile"
      ]
    }
  },
  "scope": "profile microprofile-jwt",
  "sid": "b3749d19-c90b-447c-b755-db85407ea22a",
  "upn": "admin",
  "name": "Anderson Martins",
  "groups": [
    "default-roles-springbootkeycloak",
    "offline_access",
    "admin",
    "uma_authorization"
  ],
  "preferred_username": "admin",
  "given_name": "Anderson",
  "family_name": "Martins"
}
*
*
*
*
* */