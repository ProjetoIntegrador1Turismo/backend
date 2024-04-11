package ifpr.roteiropromo.core.auth.controller;

import ifpr.roteiropromo.core.auth.domain.AuthenticatedUserDTO;
import ifpr.roteiropromo.core.auth.domain.UserAuthenticationDTO;
import ifpr.roteiropromo.core.auth.service.AuthenticationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Log4j2
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    //Obtem o token de autenticação sem os dados do usuário
//    @PostMapping()
//    public ResponseEntity<String> getToken(@RequestBody UserAuthenticationDTO user){
//        return authenticationService.getAuthenticationToken(user);
//    }

    //Obtem o token de autenticação e os dados do usuário
    @PostMapping()
    public ResponseEntity<AuthenticatedUserDTO> getTokenAndUserData(@RequestBody UserAuthenticationDTO user){
        return ResponseEntity.ok(authenticationService.getUSerTokenAndData(user));
    }








}
