package SpringSecurity.SpringSecurity.dto;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

// esta clase representa el DTO que un usuario deve enviar a la api para registrarse por primera vez
public class SaveUserDTO implements Serializable {

    @Size(min = 4)
    private String name;
    @Column(nullable = false)
    private String username;
    @Size(min = 8)
    private String password;
    @Size(min = 8)
    private String repeatedPassword;

    public String getName() {
        return name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword( String password) {
        this.password = password;
    }

    public  String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }
}
