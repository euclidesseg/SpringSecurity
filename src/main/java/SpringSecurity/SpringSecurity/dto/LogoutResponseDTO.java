package SpringSecurity.SpringSecurity.dto;

import java.io.Serializable;

public class LogoutResponseDTO implements Serializable {
    private String message;

    public LogoutResponseDTO(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }
    public void setMessage(String message){
        this.message = message;
    }
}
