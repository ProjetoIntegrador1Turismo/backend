package ifpr.roteiropromo.core.user.controller;

import ifpr.roteiropromo.core.errors.StandartError;
import ifpr.roteiropromo.core.user.domain.dtos.*;
import ifpr.roteiropromo.core.user.domain.entities.User;
import ifpr.roteiropromo.core.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Log4j2
@Tag(name = "User", description = "Operations related to Users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }


    @PostMapping("/create")
    @Operation(summary = "Create new User",
            description = "Allow to create a new user of type: Tourist or Guide")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)) }),
            @ApiResponse(responseCode = "409", description = "User email already registered",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid data for user creation",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class)) })
    })
    public ResponseEntity<UserDTO> createNewUser(@RequestBody UserDTOForm userDTOForm){
        return ResponseEntity.ok(userService.createNewUser(userDTOForm));
    }


    @GetMapping
    @Operation(summary = "Get all users",
            description = "Allow ADMIN to get a list of all users registered.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of all users registered",
                    content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = User.class)))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden access",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class)) })
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAll());
    }


    @GetMapping("/email")
    @Operation(summary = "Find user by email",
            description = "Allow ADMIN to get a users by email.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User data found by email",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden access",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class)) }),
            @ApiResponse(responseCode = "400", description = "Not found user by email provided",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class)) })
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getOne(@RequestParam String email){
        return ResponseEntity.ok(userService.getOneByEmail(email));
    }


    @PutMapping("/update")
    @Operation(summary = "Update user",
            description = "Allow user to update data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User data updated successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class)) }),
            @ApiResponse(responseCode = "400", description = "Not found user by email provided",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class)) })
    })
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTOUpdate userDTOUpdate) {
        UserDTO updatedUser = userService.updateUser(userDTOUpdate);
        return ResponseEntity.ok(updatedUser);
    }


    // ENVIA e-mail de recuperação de senha para o usuário atual
    // OBS: PARA UTILIZAR ESSA ROTA É PRECISO JÁ ESTAR COM O CONTEINER NOVO ATUALIZADO... (O COM TEMA DO APP)
    @PostMapping("/recovery")
    @Operation(summary = "Recovery password",
            description = "Allow user to recovery password by email.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password recovery email sent successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class)) }),
            @ApiResponse(responseCode = "400", description = "Not found user by email provided",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class)) })
    })
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> resetPassword(@RequestParam String email) {
        return ResponseEntity.ok(userService.resetUserPassword(email));
    }


}
