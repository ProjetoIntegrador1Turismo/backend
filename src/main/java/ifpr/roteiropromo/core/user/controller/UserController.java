package ifpr.roteiropromo.core.user.controller;

import ifpr.roteiropromo.core.user.domain.dtos.*;
import ifpr.roteiropromo.core.user.domain.entities.Guide;
import ifpr.roteiropromo.core.user.domain.entities.User;
import ifpr.roteiropromo.core.user.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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


    @PostMapping("/create")
    public ResponseEntity<UserDTO> createNewUser(@RequestBody UserDTOForm userDTOForm){
        return ResponseEntity.ok(userService.createNewUser(userDTOForm));
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAll());
    }


    @GetMapping("/email")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getOne(@RequestParam String email){
        return ResponseEntity.ok(userService.getOneByEmail(email));
    }


    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTOUpdate userDTOUpdate) {
        log.debug("Received update request for user: {}", userDTOUpdate);
        UserDTO updatedUser = userService.updateUser(userDTOUpdate);
        return ResponseEntity.ok(updatedUser);
    }


    // ENVIA e-mail de recuperação de senha para o usuário atual
    // OBS: PARA UTILIZAR ESSA ROTA É PRECISO JÁ ESTAR COM O CONTEINER NOVO ATUALIZADO... (O COM TEMA DO APP)
    @PostMapping("/recovery")
    public ResponseEntity<String> resetPassword(@RequestBody UserDTORecovery userDTORecovery) {
        return ResponseEntity.ok(userService.resetUserPassword(userDTORecovery));
    }


}
