package SpringSecurity.SpringSecurity.persistance.entity;

import SpringSecurity.SpringSecurity.persistance.util.Role;
import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "\"user\"")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String username;

    private String nombre;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    //getter y stter de la clase
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }
    public Role getRole(){
        return this.role;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(Role role){
        this.role = role;
    }


    // Desde aquí implementamos seguridad
    // Me va a retornar una colección de autoridades es decir los permisos según lo roles del usuario
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(role == null){
            return null;
        }
        if(role.getPermissions() == null){
            return null;
        }
        return role.getPermissions().stream()
                .map(perm -> perm.name())
                .map(perm -> new SimpleGrantedAuthority(perm)).collect(Collectors.toList());
                //.map(permision -> {
                //    String permission = permision.name();
                //    return new SimpleGrantedAuthority(permission);
                //}).collect(Collectors.toList());

    }

    @Override
    public @Nullable String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
/* public: Este es el modificador de acceso del método, lo que significa que el método es accesible desde cualquier otra clase.
 * Collection<? extends GrantedAuthority>:  Este es el tipo de retorno del método.
 * Collection: Indica que el método devuelve una colección, que es una interfaz genérica en Java para un grupo de objetos.
 * <? extends GrantedAuthority>: Es una forma de usar la generics en Java para
 * indicar que la colección puede contener objetos de cualquier tipo que extienda
 * (o implemente) la interfaz GrantedAuthority. La ? es un comodín que representa un tipo desconocido pero que este tipo
 * desconocido debe implementar o extender de GrantedAuthority,
 */
