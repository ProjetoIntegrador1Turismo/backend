package ifpr.roteiropromo.core.auth.controller;

import ifpr.roteiropromo.core.auth.domain.AuthenticatedUserDTO;
import ifpr.roteiropromo.core.auth.domain.UserAuthenticationDTO;
import ifpr.roteiropromo.core.auth.service.AuthenticationService;
import ifpr.roteiropromo.core.errors.StandartError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@Log4j2
@Tag(name = "Authentication", description = "Operations related to user authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    //Retorna os dados do usuario e o token de autenticação
    @PostMapping()
    @Operation(summary = "Return user data and authentication token",
            description = "Allow user to authenticate and receive a token to access the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "User not found or invalid authentication data.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class)) }),
            @ApiResponse(responseCode = "401", description = "Guide account not aproved yet.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class)) }),
            @ApiResponse(responseCode = "200", description = "User authenticated successfully.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticatedUserDTO.class)) })
    })
    public ResponseEntity<AuthenticatedUserDTO> getTokenAndUserData(@RequestBody UserAuthenticationDTO user){
        return ResponseEntity.ok(authenticationService.getUSerTokenAndData(user));
    }


    @PostMapping("/refresh")
    @Operation(summary = "Return user data and authentication token",
            description = "Allow user to refresh authentication token using refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Expired or Invalid Token.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class)) }),
            @ApiResponse(responseCode = "200", description = "Authentication token refreshed successfully.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticatedUserDTO.class)) })
    })
    public ResponseEntity<AuthenticatedUserDTO> getAuthRefreshToken(@RequestParam String refreshToken){
        return ResponseEntity.ok(authenticationService.getAuthRefreshToken(refreshToken));
    }








}
