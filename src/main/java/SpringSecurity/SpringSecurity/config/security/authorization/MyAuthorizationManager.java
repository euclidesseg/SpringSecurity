package SpringSecurity.SpringSecurity.config.security.authorization;

import SpringSecurity.SpringSecurity.exception.ObjectNotFoundException;
import SpringSecurity.SpringSecurity.persistance.entity.security.Operation;
import SpringSecurity.SpringSecurity.persistance.entity.security.User;
import SpringSecurity.SpringSecurity.persistance.repository.security.IOperationRepository;
import SpringSecurity.SpringSecurity.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class MyAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
    @Autowired
    private IOperationRepository operationRepository;
    @Autowired
    private UserServiceImpl userService;
    @Override
    public  AuthorizationDecision authorize(Supplier<? extends Authentication> authentication, RequestAuthorizationContext requestContext) {

        HttpServletRequest request = requestContext.getRequest();

        String url = extractUrl(request);
        String httpMethod = request.getMethod();
        boolean isPublic = isPublic(url, httpMethod);

        if (isPublic) {
            return new AuthorizationDecision(true);
        }
        boolean isGranted =  isGranted(url, httpMethod, authentication.get());
        return new AuthorizationDecision(isGranted);
    }

    // metodo para extraer la url /products/2
    private String extractUrl(HttpServletRequest request){
        String contextPath = request.getContextPath(); // esto será igual a /api/v1 == tal cual como está en el aplication properties
        String url = request.getRequestURI(); //seria algo como /api/v1/products/2
        url = url.replace(contextPath, ""); // reemplaza las ocurrencias de contextpath dentro de url por una cadena vacia
        System.out.println(url);
        return url;
    }

    // metodo para validar que sea publica una url
    // en este metodo voy a obtener todas las url publicas de la base de datos y
    // recorrerlas para encontrar si alguna coincide con dicha url y si es pública
    private boolean isPublic(String url, String method) {
        List<Operation> publicAccessEndPints = operationRepository.findByPublicAccess();
        publicAccessEndPints.forEach(operation -> {
            System.out.println("OPERACIÓN: " + operation.getName());
            System.out.println("MÓDULO: "
                    + operation.getModule().getBase_path());
            System.out.println("PATH: " + operation.getPath());
            System.out.println("MÉTODO: " + operation.getHttp_method());
            System.out.println("URL RECIBIDA: " + url);
        });
        boolean isPublic = publicAccessEndPints.stream().anyMatch( getPermissionStatus(url, method));

        System.out.println("IS PUBLIC?" + isPublic);
        return isPublic;

    }

    // metodo para definir permiso concedido o no sobre peticiones privadas
    private boolean isGranted(String url, String httpMethod, Authentication authentication) {
        if(authentication == null || !(authentication instanceof UsernamePasswordAuthenticationToken)){
            throw new AuthenticationCredentialsNotFoundException("user not logged in");
        }
        List<Operation> operations = obtainOperations(authentication);

        boolean isGranted = operations.stream().anyMatch(getPermissionStatus(url, httpMethod));

        System.out.println("IS GRANTED?" + isGranted);
        return isGranted;
    }



    private Predicate<? super Operation> getPermissionStatus(String url, String httpMethod) {
        return operation ->{
            String basePath = operation.getModule().getBase_path(); //seria algo como /PRODUCTS O CATEGORY
            Pattern pattern = Pattern.compile(basePath.concat(operation.getPath())); // el patron seria algo como /[0-9] o /authenticate
            Matcher matcher = pattern.matcher(url); // este patron deve hacer match con la url es decir, deven ser iguales
            return matcher.matches() && operation.getHttp_method().equalsIgnoreCase(httpMethod);
        };
    }

    // obtiene las operaciones de cada rol de un usuario logueado
    private List<Operation> obtainOperations(Authentication authentication) { // necesito la lista de operaciones de cada permiso unicamenbte para comparar sus rutas y poder validar correctamente
        UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication;
        String username = (String) authToken.getPrincipal();

        User user = this.userService.findByUsername(username).orElseThrow(() -> new ObjectNotFoundException("user not found with username" + username));
        return user.getRole().getGrantedPermissions().stream()
                .map((permission) -> permission.getOperation()).collect(Collectors.toList()); // estas operaciones solo son las operaciones del rol del cliente logueado
    }

}


// este es el metodo isGranted sin extraer en otro metodo el anyMatch
// private boolean isGranted(String url, String httpMethod, Authentication authentication) {
//     if(authentication == null || !(authentication instanceof UsernamePasswordAuthenticationToken)){
//         throw new AuthenticationCredentialsNotFoundException("user not logged in");
//     }
//     List<Operation> operations = obtainOperations(authentication);

//     boolean isGranted = operations.stream().anyMatch( operation ->{
//         String basePath = operation.getModule().getBase_path();
//         Pattern pattern = Pattern.compile(basePath.concat(operation.getPath()));
//         Matcher matcher = pattern.matcher(url);
//         return matcher.matches() && operation.getHttp_method().equalsIgnoreCase(httpMethod);
//     });

//     System.out.println("IS GRANTED?" + isGranted);
//     return isGranted;
// }