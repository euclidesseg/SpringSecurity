package SpringSecurity.SpringSecurity.config.security.filter;

import SpringSecurity.SpringSecurity.persistance.entity.User;
import SpringSecurity.SpringSecurity.service.IUserService;
import SpringSecurity.SpringSecurity.service.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// este es un filtro de seguridad llamado filtro de authenticacion que se injecta a mi cadena de filtros la cual se genera en HttpSecurityConfig
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private IUserService userService;
    @Autowired
    private JwtService jwtService;

    //filterChain es la cadena de filtros de jakarta
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1. Obtener encabezado(header) http llamado autorization
        //   Se verifica si el encabezado Authorization existe y comienza con "Bearer ".
        //   Si no es así, la cadena de filtros continúa sin procesar el token JWT.
        System.out.println("Entró en el filtro jwt");
        String authorizationHeader =  request.getHeader("Authorization"); //Bearer jwt
        if(!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")){ // == Verificar si el token de autorización existe y comienza con "Bearer "
            filterChain.doFilter(request, response);
            return; // retorna el control a quien mando a llamar doFilterInternal
        }
        // en este punto la negacion de acceso lo maneja AuthenticationEntryPoint el cual invita a que nos logueemos
        // y el AceesDeniedAndler es logueo con un permiso de acceso

        //2. Desde el encabezado(Header) sacar el token
        String jwt = authorizationHeader.split(" ")[1];

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

