package ifpr.roteiropromo.core.user.controller;

import ifpr.roteiropromo.core.auth.UserAuthenticationDTO;
import ifpr.roteiropromo.core.user.domain.dtos.UserDTO;
import ifpr.roteiropromo.core.user.domain.dtos.UserDTOForm;
import ifpr.roteiropromo.core.user.service.UserService;
import ifpr.roteiropromo.core.utils.JwtTokenHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Log4j2
public class UserController {

/*
* Já identifiquei as rotas aptas a criar um usuário a atualiar sua senha pelo keyclock
* O novo usuário já vem por padrão com a permisão de usuário simples (guia dependerá de atuação do adm)
*
**/
    private final UserService userService;


    public UserController(UserService userService){
        this.userService = userService;
    }

    //Create a new user
    @PostMapping("/create")
    public ResponseEntity<UserDTO> createNewUser(@RequestBody UserDTOForm userDTOForm){
        return ResponseEntity.ok(userService.creatNewUser(userDTOForm));
    }


    //Metodo só para testes. Provavelmente não será mantido.
//    @GetMapping()
//    public String getAdminToken(){
//        JwtTokenHandler jwtTokenHandler = new JwtTokenHandler();
//        return jwtTokenHandler.getAdminToken();
//    }


    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return ResponseEntity.ok(userService.getAll());
    }









}
