package ifpr.roteiropromo.core.user.controller;

import ifpr.roteiropromo.core.user.domain.User;
import ifpr.roteiropromo.core.user.service.UserService;
import ifpr.roteiropromo.core.utils.JwtTokenHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/create")
    public void createNewUser(@RequestBody User user){
        userService.creatNewUser(user);
    }

    @GetMapping()
    public String getAdminToken(){
        JwtTokenHandler jwtTokenHandler = new JwtTokenHandler();
        return jwtTokenHandler.getAdminToken();
    }








}
