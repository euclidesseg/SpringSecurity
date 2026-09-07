package SpringSecurity.SpringSecurity.controller;

import SpringSecurity.SpringSecurity.dto.auth.AuthenticationRequestDTO;
import SpringSecurity.SpringSecurity.dto.auth.AuthenticationResponseDTO;
import SpringSecurity.SpringSecurity.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Clase para que un usuario pueda realizar una autenticación, logueo o inicio de sesión
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    public AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }


    @GetMapping("/validate")
    public ResponseEntity<Boolean> validate (@RequestParam String jwt){
        Boolean isTokenValid = authenticationService.validateToken(jwt);
        return ResponseEntity.ok(isTokenValid);
    }

    @PostMapping("/authenticate")
    // responderá con AuthenticationResponse y recibira RequestAuthentication
    public ResponseEntity<AuthenticationResponseDTO> authenticate(@RequestBody @Valid AuthenticationRequestDTO authenticationRequestDTO){
        AuthenticationResponseDTO responseDTO = authenticationService.login(authenticationRequestDTO);
        return  ResponseEntity.ok(responseDTO);
    }
}
