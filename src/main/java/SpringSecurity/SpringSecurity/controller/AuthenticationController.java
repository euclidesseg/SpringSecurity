package SpringSecurity.SpringSecurity.controller;

import SpringSecurity.SpringSecurity.dto.auth.AuthenticationRequestDTO;
import SpringSecurity.SpringSecurity.dto.auth.AuthenticationResponseDTO;
import SpringSecurity.SpringSecurity.persistance.entity.User;
import SpringSecurity.SpringSecurity.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

// Clase para que un usuario pueda realizar una autenticación, logueo o inicio de sesión
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    public AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }


    @PreAuthorize("permitAll")
    @GetMapping("/validate")
    public ResponseEntity<Boolean> validate (@RequestParam String jwt){
        Boolean isTokenValid = authenticationService.validateToken(jwt);
        return ResponseEntity.ok(isTokenValid);
    }

    @PreAuthorize("permitAll")
    @PostMapping("/authenticate")
    // responderá con AuthenticationResponse y recibirá RequestAuthentication
    public ResponseEntity<AuthenticationResponseDTO> authenticate(@RequestBody @Valid AuthenticationRequestDTO authenticationRequestDTO){
        AuthenticationResponseDTO responseDTO = authenticationService.login(authenticationRequestDTO);
        return  ResponseEntity.ok(responseDTO);
    }

    // metodo para que un usuario lea su propio perfil
    //@PreAuthorize("hasAnyRole('ADMINISTRATOR', 'ASSISTANT_ADMINISTRATOR','CUSTOMER')")
    @PreAuthorize("hasAuthority('READ_MY_PROFILE')")
    @GetMapping("/profile")
    public ResponseEntity<User> readMyProfile(){
        // si llego hasta este enpoint si está loqueado ya que este enpoint está protegido
        User myUser = this.authenticationService.getLoggedInUser();
        return ResponseEntity.status(HttpStatus.OK).body(myUser);
    }

}
