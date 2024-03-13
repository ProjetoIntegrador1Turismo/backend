package ifpr.roteiropromo.core.user.controller;

import ifpr.roteiropromo.core.utils.JwtTokenHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Log4j2
public class UserController {

/*
* Já identifiquei as rotas aptas a criar um usuário a atualiar sua senha pelo keyclock
* O novo usuário já vem por padrão com a permisão de usuário simples (guia dependerá de atuação do adm)
*
**/




    @PostMapping("/create")
    public void createNewUser(){

        JwtTokenHandler jwtTokenHandler = new JwtTokenHandler();
        jwtTokenHandler.getUserDataFromToken();

    }

    @GetMapping()
    public String getAdminToken(){
        JwtTokenHandler jwtTokenHandler = new JwtTokenHandler();
        return jwtTokenHandler.getAdminToken();
    }








}
