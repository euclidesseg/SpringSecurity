package SpringSecurity.SpringSecurity.config.security.handler;

import SpringSecurity.SpringSecurity.dto.ApiErrorDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;
    public  CustomAccessDeniedHandler(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ApiErrorDTO apiError = new ApiErrorDTO();

        apiError.setBackendMessage(accessDeniedException.getLocalizedMessage());
        apiError.setUrl(request.getRequestURL().toString());
        apiError.setMethod(request.getMethod());
        apiError.setMessage("Acceso denegado. No tienes los permisos necesarios para esta solicitud");
        apiError.setTimeStamp(LocalDateTime.now());


        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());

        // Convierte el objeto ApiError a una cadena JSON gracias a la libreria jaxson
        String apiErrorAsJson = this.objectMapper.writeValueAsString(apiError);

        // Escribe la cadena JSON en la respuesta HTTP
        response.getWriter().write(apiErrorAsJson);
    }
}
/*  cuando se lanza la excepcion utiizando anotaciones ya hemos pasado los filtros de seguridad
 *  y con coincidencias de http las excepciones se lanzan mienstras se atraviezan los filtros de seguridad?
 *  por eso se usan estas clases handler
*/