package SpringSecurity.SpringSecurity.service.auth;


import SpringSecurity.SpringSecurity.dto.auth.AuthenticationRequestDTO;
import SpringSecurity.SpringSecurity.dto.auth.AuthenticationResponseDTO;
import SpringSecurity.SpringSecurity.dto.RegisteredUserDTO;
import SpringSecurity.SpringSecurity.dto.SaveUserDTO;
import SpringSecurity.SpringSecurity.persistance.entity.User;
import SpringSecurity.SpringSecurity.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    private IUserService userService;

    public AuthenticationService (IUserService userService) {
        this.userService = userService;
    }

    @Autowired
    JwtService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;

    public RegisteredUserDTO registerOneCustomer(@Valid SaveUserDTO saveUserDTO) {

        User user = userService.createOneCustomer(saveUserDTO);
        RegisteredUserDTO registeredUserDTO = new RegisteredUserDTO();

        registeredUserDTO.setId(user.getId());
        registeredUserDTO.setName(user.getName());
        registeredUserDTO.setUsername(user.getUsername());
        registeredUserDTO.setRole(user.getRole().name());

        String jwt = jwtService.generateToken(user, generateExtraClaims(user));
        System.out.println("JWT " + jwt);
        registeredUserDTO.setJwt(jwt);
        return registeredUserDTO;
    }


    //Metodo para generar los Claims con el que se genera el token
    private Map<String, Object> generateExtraClaims(User user){
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getName());
        extraClaims.put("role", user.getRole().name());
        extraClaims.put("authorities", user.getAuthorities());
        return extraClaims;
        // Si quisiera podría agregar tantos claims aquí como fueran necesario
        // ejemplo extraClaims.put("isPremium", user.getIsPremium());
    }

    public AuthenticationResponseDTO login(AuthenticationRequestDTO authenticationRequestDTO) {
        // El authenticationmanager que tenemos en SecurityBeansInjector es el que tiene el
        // metodo authenticate que authentica a un usuario entonces hacemos uso de el aquí

        // creo el authentication
        Authentication authentication = new UsernamePasswordAuthenticationToken(authenticationRequestDTO.getUsername(),authenticationRequestDTO.getPassword()
        );
        // este objeto administrador va a buscar un metodo que le resuelva esta authenticación
        // y será DaoAuthenticationProvider el cual ya configuraromos tambien en SecurityBeansInjector
        // y pro eso se llama administrasion de authenticacaion porque recibe el authenticadorauthentication y lo administra
        authenticationManager.authenticate(authentication); // se hacen validaciones


       UserDetails user =  userService.findByUsername(authenticationRequestDTO.getUsername()).get();
       String jwt = jwtService.generateToken(user, generateExtraClaims((User) user));

       AuthenticationResponseDTO authResPDTO = new AuthenticationResponseDTO();
       authResPDTO.setJwt(jwt);
       return authResPDTO;
    }

    public Boolean validateToken(String jwt) {
        /* Podemos extraer cualquier claim del jwt lo importante es que se extraiga y se valide ese json,
        *  su firma y fecha de expiración
        * */
        try{
            this.jwtService.extractUsername(jwt);
            return true;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
