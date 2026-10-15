package SpringSecurity.SpringSecurity.service.auth;


import SpringSecurity.SpringSecurity.dto.auth.AuthenticationRequestDTO;
import SpringSecurity.SpringSecurity.dto.auth.AuthenticationResponseDTO;
import SpringSecurity.SpringSecurity.dto.RegisteredUserDTO;
import SpringSecurity.SpringSecurity.dto.SaveUserDTO;
import SpringSecurity.SpringSecurity.exception.ObjectNotFoundException;
import SpringSecurity.SpringSecurity.persistance.entity.security.JwtToken;
import SpringSecurity.SpringSecurity.persistance.entity.security.User;
import SpringSecurity.SpringSecurity.persistance.repository.security.IJwtTokenRepository;
import SpringSecurity.SpringSecurity.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationService {

    private IUserService userService;

    public AuthenticationService (IUserService userService) {
        this.userService = userService;
    }

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IJwtTokenRepository iJwtTokenRepository;
    public RegisteredUserDTO registerOneCustomer(@Valid SaveUserDTO saveUserDTO) {

        User user = userService.createOneCustomer(saveUserDTO);
        String jwt = jwtService.generateToken(user, generateExtraClaims(user));
        saveUserToken(user, jwt);

        RegisteredUserDTO registeredUserDTO = new RegisteredUserDTO();

        registeredUserDTO.setId(user.getId());
        registeredUserDTO.setName(user.getName());
        registeredUserDTO.setUsername(user.getUsername());
        registeredUserDTO.setRole(user.getRole().getName());

        System.out.println("JWT " + jwt);
        registeredUserDTO.setJwt(jwt);
        return registeredUserDTO;
    }


    //Metodo para generar los Claims con el que se genera el token
    private Map<String, Object> generateExtraClaims(User user){
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getName());
        extraClaims.put("role", user.getRole().getName());
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
       saveUserToken((User)user, jwt);

       AuthenticationResponseDTO authResPDTO = new AuthenticationResponseDTO();
       authResPDTO.setJwt(jwt);
       return authResPDTO;
    }


    // Metodo para crear un token en base de datos usado al momento de login y de creación de un usuario;
    private void saveUserToken(User user, String jwt) {
        JwtToken token = new JwtToken();
        token.setToken(jwt);
        token.setUser(user);
        token.setExpiration(this.jwtService.extractExpirationDate(jwt));
        token.setValid(true);
        this.iJwtTokenRepository.save(token);
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

    // obtener el usuario logueado
    public User getLoggedInUser() {
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken)  SecurityContextHolder.getContext().getAuthentication();// obtener el usuario logueado
        String username = (String) auth.getPrincipal(); // obtengo el principal que como se definio en JwtAuthenticationFilter solo es el nombre de usuario
        return this.userService.findByUsername(username).orElseThrow(() -> new ObjectNotFoundException("User not found with username" + username));

    }

    // alternativa al metodo de arriba que se usa en caso de que hallan varios tips de authenticacion
    public User getMyProfile(String username){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof UsernamePasswordAuthenticationToken authTocken) {
            String userAuth = (String) authTocken.getPrincipal();
            if(username.equals(userAuth)){
                System.out.println("Sin son iguales");
                return this.userService.findByUsername(username)
                        .orElseThrow(() -> new ObjectNotFoundException("User not Found with username" + username));
            }else{
                System.out.println("No son iguales");
            }
        }
        return null;
    }

    public void logout(HttpServletRequest httpServletRequest) {
        String jwt = this.jwtService.extractJwtFromRequest(httpServletRequest);
        if(jwt == null || !StringUtils.hasText(jwt)) return;

        Optional<JwtToken> token = iJwtTokenRepository.findByToken(jwt); // buscar el token en db para invalidarlo con invalid false.
        if(token.isPresent() && token.get().isValid()){
            token.get().setValid(false);
            iJwtTokenRepository.save(token.get());
        }
    }
}
