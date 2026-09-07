package SpringSecurity.SpringSecurity.dto.auth;


import java.io.Serializable;

// esta clase es un DTO y representa el objeto que debe enviar un usuario para realizar un login
public class AuthenticationRequestDTO implements Serializable {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
