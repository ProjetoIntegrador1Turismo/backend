package ifpr.roteiropromo.core.user.controller;

import ifpr.roteiropromo.core.user.domain.dtos.UserDTO;
import ifpr.roteiropromo.core.user.domain.dtos.UserDTOForm;
import ifpr.roteiropromo.core.user.domain.dtos.UserDTORecovery;
import ifpr.roteiropromo.core.user.domain.entities.User;
import ifpr.roteiropromo.core.user.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Log4j2
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    //Create a new user
    @PostMapping("/create")
    public ResponseEntity<UserDTO> createNewUser(@RequestBody UserDTOForm userDTOForm){
        return ResponseEntity.ok(userService.creatNewUser(userDTOForm));
    }

    //Metodo pausado. Ponderar a rota já pronta para disparar o email de recuperação!
    @GetMapping("/recovery")
    public ResponseEntity<String> resetPassword(
            @RequestBody UserDTORecovery userDTORecovery
            ){
        return ResponseEntity.ok(userService.resetUserPassword(userDTORecovery));
    }


    //Metodo só para testes. Provavelmente não será mantido.
//    @GetMapping()
//    public String getAdminToken(){
//        JwtTokenHandler jwtTokenHandler = new JwtTokenHandler();
//        return jwtTokenHandler.getAdminToken();
//    }


    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAll());
    }


    @GetMapping("/email")
    public ResponseEntity<User> getOne(@RequestParam String email){
        return ResponseEntity.ok(userService.getOneByEmail(email));
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.update(userDTO));
    }




}
