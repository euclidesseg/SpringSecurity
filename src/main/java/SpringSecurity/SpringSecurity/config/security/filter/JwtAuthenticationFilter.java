package SpringSecurity.SpringSecurity.config.security.filter;

import SpringSecurity.SpringSecurity.persistance.entity.security.JwtToken;
import SpringSecurity.SpringSecurity.persistance.entity.security.User;
import SpringSecurity.SpringSecurity.persistance.repository.security.IJwtTokenRepository;
import SpringSecurity.SpringSecurity.service.IUserService;
import SpringSecurity.SpringSecurity.service.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

// este es un filtro de seguridad llamado filtro de authenticacion que se injecta a mi cadena de filtros la cual se genera en HttpSecurityConfig
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private IUserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private IJwtTokenRepository iJwtTokenRepository;

    //filterChain es la cadena de filtros de jakarta
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Entró en filtro Jwt Authentication Filter");
        //ver en este metodo los pasos 1 y 2 para Obtener authorization header y Obtener token
        String jwt = this.jwtService.extractJwtFromRequest(request);

        if (jwt == null || !StringUtils.hasText(jwt)){
            filterChain.doFilter(request, response);
            return;
        }

        //2.1 Obtener token no expirado y valido desde base de datos
        Optional<JwtToken> token = iJwtTokenRepository.findByToken(jwt);
        boolean isValid = validateToken(token);
        if (!isValid){
            filterChain.doFilter(request, response);
            return;
        }


        //3. Obtener el subject/username desde el token
        // Esta accion a su vez valida el formato del token firma y fecha de expiración
        String username = jwtService.extractUsername(jwt);
        
        //4. Setear el objeto authentication dentro de securit cotnext Holder
        // Este objeto representa el usuario logueado está dentro del securityContext y este a su vez dentro del securitycontext holder
        User userDetails = this.userService.findByUsername(username).get();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetails(request));// recibe los detalles de la authentication
        SecurityContextHolder.getContext().setAuthentication(authToken);

        //5. Ejecutar el registro de filtros
        filterChain.doFilter(request, response);
    }

    //Metodo para validar el token que viene de base de datos
    private boolean validateToken(Optional<JwtToken> optionalToken) {
        if(!optionalToken.isPresent()){
            System.out.println("Token no existente");
            return false;
        }
        JwtToken token = optionalToken.get();
        Date now = new Date(System.currentTimeMillis());
        boolean isValid = token.isValid() && token.getExpiration().after(now); // si la fecha de expiración esta despues de la fecha actual
        if(!isValid){
            System.out.println("Token no valido");
            updateTokenStatus(token);
        }
        return isValid;
    }
    private void updateTokenStatus(JwtToken token) {
        token.setValid(false);
        this.iJwtTokenRepository.save(token);
    }
}


//#  OncePerRequestFilter
// Es una clase abstracta de java proporcionada por spring security que simplifica la creacion de filtros personalizados
// para procesar peticones http una sola vez por solicitud
// garantiza que el filtro se aplique una sola vez a la cadena de filtros de spring security lo que evita procesamientos inncecesarios
// o duplicados que podrian ocurrir si se usa un filtro común. Tambien se tiene acceso tanto a HttpServletRequest(Solicitud) como a HttpServeletResponse(respuesta)

//#  Funcionamiento de FilterChain
// Cuando un cliente envía una solicitud a un recurso web (servlet o JSP),
// el contenedor de servlets primero verifica si hay filtros mapeados para
// ese recurso en el descriptor de implementación (web.xml).
// Si se definen filtros, el contenedor crea un objeto FilterChain que contiene la secuencia de filtros que se invocarán.
// El contenedor inicia el procesamiento llamando al método doFilter del primer filtro en la cadena.

//# UsernamePasswordAuthenticationToken
// Si enntramos a la clase nos damos cuenta de que resibe un objeto Object principal y Object es la clase padre de java es decir recibe cualquier tipo de objeto
// en este caso como principal le estamos enviando solo el nombre del usuario ya que con eso es suficiente para mantener el contexto de la aplicacion
// pero tecnicamente podria enviar el objeto Usuario en esta instancia
//
//
//

//# Nota
// El filtro JwtAuthenticationFilter no solo valida el token JWT, sino que también establece
// el contexto de seguridad con la información del usuario autenticado. Esto es crucial para que las partes
// posteriores del flujo de la solicitud, como los controladores o servicios, puedan acceder a la identidad del usuario
// y aplicar las reglas de autorización correspondientes.
// 2. Actualización del contexto de seguridad:

