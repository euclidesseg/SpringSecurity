package SpringSecurity.SpringSecurity.config.security.handler;

import SpringSecurity.SpringSecurity.dto.ApiErrorDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ApiErrorDTO apiError = new ApiErrorDTO();
        // Establece el mensaje del backend con el mensaje localizado de la excepción
        apiError.setBackendMessage(authException.getLocalizedMessage());

        // Establece la URL que el cliente intentó acceder
        apiError.setUrl(request.getRequestURL().toString());

        // Establece el método HTTP utilizado (GET, POST, etc.)
        apiError.setMethod(request.getMethod());

        // Mensaje genérico para el cliente indicando que no se encontraron credenciales
        // de autenticación
        apiError.setMessage(
                "No se encontraron credenciales de autenticación por favor inicie sesion para acceder a esta función");

        // Establece la marca de tiempo actual
        apiError.setTimeStamp(LocalDateTime.now());

        // Configura la respuesta HTTP para que el contenido sea JSON
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // Configura el código de estado HTTP a 401 (No Autorizado)
        response.setStatus(HttpStatus.UNAUTHORIZED.value());


        // Convierte el objeto ApiError a una cadena JSON gracias a la libreria jaxson
        // la cual debemos agregar a nustro pom
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registeredModules(new JavaTimeModule());
        String apiErrorAsJson = objectMapper.writeValueAsString(apiError);

        // Escribe la cadena JSON en la respuesta HTTP
        response.getWriter().write(apiErrorAsJson);
        //TODO terminar el video numero 53 de udemy
    }
}
