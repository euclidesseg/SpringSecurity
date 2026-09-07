package SpringSecurity.SpringSecurity.controller;


import SpringSecurity.SpringSecurity.dto.RegisteredUserDTO;
import SpringSecurity.SpringSecurity.dto.SaveUserDTO;
import SpringSecurity.SpringSecurity.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// este controlador crea y authentica un usuario nuevo
@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    AuthenticationService authenticationService;
    // metodo para realizar un autoregistro de usuario por primera vez devuelve un DTO RegisteredUserDTO
    @PostMapping
    ResponseEntity<RegisteredUserDTO> registerOne (@RequestBody @Valid SaveUserDTO saveUserDTO){
        RegisteredUserDTO registeredUserDTO = authenticationService.registerOneCustomer(saveUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUserDTO);
    }
}


// RegisteredUserDTO objeto de respuesta (devuelto) al usuario
// SaveUserDTO objeto de petition que envía un usuario para registrarse

//**Nota: JPA Nunca devuelve un null o vacío*/