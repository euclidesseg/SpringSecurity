package SpringSecurity.SpringSecurity.exception;

import SpringSecurity.SpringSecurity.dto.ApiErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
/* Controla excepciones, cuando esta anotación se coloca esta anotación en una clase,
 * Spring Boot la detecta y la utiliza para manejar excepciones lanzadas
 * desde cualquier controlador REST en la aplicación.
 */
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    // Maneja excepciones que no tienen un handler específico.
    // Actúa como último recurso y normalmente devuelve HTTP 500.
    // ejemplo al guardar un producto con la categoria que no existe
    public ResponseEntity<?> handlerGenericException(HttpServletRequest request, Exception exception){
        ApiErrorDTO apiErrorDTO = new ApiErrorDTO();
        apiErrorDTO.setBackendMessage(exception.getLocalizedMessage());// no está hecho para el cliente final
        apiErrorDTO.setUrl(request.getRequestURL().toString());
        apiErrorDTO.setMethod(request.getMethod());
        apiErrorDTO.setMessage("Error interno en el servidor algún recurso solicitado al servidor no existe.");
        apiErrorDTO.setTimeStamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiErrorDTO);
    }
    

    @ExceptionHandler(MethodArgumentNotValidException.class)
   /* Manejará las excepciones mas especificas como por ejemplo cuando guardamos un producto
    * y no cumpla algunas de las validaciones agregadas por ejemplo @DecimalMin(value = "0.01") o @NotBlank
    * esta excepción se lanza cuando no se logra el binding(bincular) de json hacia objeto java
    */
    public ResponseEntity<?> handlerMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException exception){
        ApiErrorDTO apiErrorDTO = new ApiErrorDTO();
        apiErrorDTO.setBackendMessage(exception.getLocalizedMessage());// no está hecho para el cliente final
        apiErrorDTO.setUrl(request.getRequestURL().toString());
        apiErrorDTO.setMethod(request.getMethod());
        apiErrorDTO.setMessage("Error en la petición enviada" + exception.getAllErrors().stream().map(err-> err.getDefaultMessage()).collect(Collectors.toList()));// Esto podría ser más específico ya que podemos sacar más argumentos del objeto MethodArgumentNotValidException
        apiErrorDTO.setTimeStamp(LocalDateTime.now());
        System.out.println(exception.getAllErrors().stream().map((err) -> {return err.getDefaultMessage();}).collect(Collectors.joining()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorDTO);
    }


}
