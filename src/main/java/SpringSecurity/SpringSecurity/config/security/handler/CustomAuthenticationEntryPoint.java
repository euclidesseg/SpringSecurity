package SpringSecurity.SpringSecurity.config.security.handler;

import SpringSecurity.SpringSecurity.dto.ApiErrorDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private  final ObjectMapper objectMapper;
    public  CustomAuthenticationEntryPoint(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }
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
        String apiErrorAsJson = this.objectMapper.writeValueAsString(apiError);
        response.getWriter().write(apiErrorAsJson);

        // Antes, se registraba manualmente JavaTimeModule en ObjectMapper para poder serializar LocalDateTime con Jackson 2.
        // Actualmente, con Spring Boot 4 y Jackson 3, el soporte para java.time está integrado y ya no es necesario registrar JavaTimeModule manualmente.
        // Por eso podemos serializar LocalDateTime directamente, aunque es recomendable utilizar el ObjectMapper administrado por Spring en lugar de crear uno con new ObjectMapper().
    }
}
