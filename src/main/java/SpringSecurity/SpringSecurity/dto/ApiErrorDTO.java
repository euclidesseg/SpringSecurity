package SpringSecurity.SpringSecurity.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

// clase para manejar los errores en las peticiones de la aplicación
public class ApiErrorDTO implements Serializable {

    private String backendMessage;
    private String message;
    private LocalDateTime timeStamp; // representa la hora en la que ocurrió el error
    private String url; // Para la url de la petición en la que ocurrió el error
    private String method; // POST DELETE GET


    // getters-setters
    public String getBackendMessage() {
        return backendMessage;
    }

    public void setBackendMessage(String backendMessage) {
        this.backendMessage = backendMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

}
