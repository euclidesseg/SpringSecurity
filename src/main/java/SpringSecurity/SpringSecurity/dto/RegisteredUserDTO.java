package SpringSecurity.SpringSecurity.dto;

import java.io.Serializable;

// esta clase se encarga de registrar un usuario con su jwt representa un objeto que se devuelve al usuario
public class RegisteredUserDTO implements Serializable {

    private Long id;
    private String username;
    private String name;
    private String role;
    private String jwt; // Este se debe a que cuando un usuario se registra automáticamente se le genera un jwt

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
//** Nota:
//!! En Java, las clases de tipo Entidad suelen implementar la interfaz
//!! Serializable para permitir que sus objetos puedan ser serializados y deserializados.
//!! La serialización es el proceso de convertir un objeto en un flujo de bytes para que pueda ser almacenado o transmitido,
//!! y la deserialización es el proceso inverso, en el cual se reconstruye el objeto original a partir de esos bytes.