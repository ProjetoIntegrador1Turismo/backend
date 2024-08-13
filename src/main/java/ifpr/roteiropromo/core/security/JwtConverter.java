package ifpr.roteiropromo.core.security;


import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.Map;


public class JwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {


    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Map<String, Collection<String>> realmAcess = jwt.getClaim("realm_access"); //Captura o realm_access do token
        Collection<String> roles = realmAcess.get("roles"); //Retira somente as roles contidas agora no realmAcess
        var grants = roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).toList(); //Tranforma em lista a roles com a sintaxe ROLE_ADMIN,etc
        return new JwtAuthenticationToken(jwt, grants); //Gera o token com os dados formatados para o Spring
    }
}

