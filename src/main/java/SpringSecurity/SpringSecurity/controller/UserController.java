package SpringSecurity.SpringSecurity.controller;

import SpringSecurity.SpringSecurity.persistance.entity.User;
import SpringSecurity.SpringSecurity.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final AuthenticationService authenticationService;
    public UserController (AuthenticationService authenticationService){
        this.authenticationService = authenticationService;

    }
    @GetMapping("/compare")
    public ResponseEntity<User> readAncompareWithContext (@RequestParam @Valid String username){
        User user = this.authenticationService.getMyProfile(username);
        if (user == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        //return ResponseEntity.status(HttpStatus.CREATED).body(user);
        return ResponseEntity.ok(user);
    }
}
