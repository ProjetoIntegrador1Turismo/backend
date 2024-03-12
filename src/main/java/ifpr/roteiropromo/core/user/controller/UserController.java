package ifpr.roteiropromo.core.user.controller;

import ifpr.roteiropromo.core.utils.JwtTokenHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Log4j2
public class UserController {

/*
* Já criei rotas aptas a criar um usuário a atualiar sua senha (rest)
* O novo usuário já vem por padrão com a permisão de usuário simples (guia depende de acesso adm)
*
*
**/




    @PostMapping("/create")
    public void createNewUser(){

        JwtTokenHandler jwtTokenHandler = new JwtTokenHandler();
        jwtTokenHandler.getUserDataFromToken();

    }








}
