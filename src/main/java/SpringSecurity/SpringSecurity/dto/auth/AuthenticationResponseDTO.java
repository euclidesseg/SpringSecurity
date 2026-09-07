package SpringSecurity.SpringSecurity.dto.auth;

import java.io.Serializable;

// esta clase es un DTO representa el objeto que se devuelve al usuario cuando hace un login con usuariio y contraseña
public class AuthenticationResponseDTO implements Serializable {

    private String jwt;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
