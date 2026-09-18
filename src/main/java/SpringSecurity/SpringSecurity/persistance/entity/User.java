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

    @Column(unique = true)
    private String username;

    private String name;

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

    public String getName() {
        return name;
    }
    public Role getRole(){
        return this.role;
    }

    public void setName(String name) {
        this.name = name;
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
        List<SimpleGrantedAuthority> authorityList = this.role.getPermissions()
                .stream().map(permission ->{
                    String authority = permission.name();
                    return new SimpleGrantedAuthority(authority);
                }).collect(Collectors.toList());
        authorityList.add(new SimpleGrantedAuthority("ROLE_"+this.role.name()));
        return authorityList;

        //return role.getPermissions().stream()
        //        .map(perm -> perm.name())
        //        .map(perm -> new SimpleGrantedAuthority(perm)).collect(Collectors.toList());
                //.map(permision -> {
                //    String permission = permision.name();
                //    return new SimpleGrantedAuthority(permission);
                //}).collect(Collectors.toList());
    }
    /*
     * authorityList.add(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
     * Se concatena "ROLE_" al crear la GrantedAuthority que representa nuestro rol.
     * Spring Security almacena tanto roles como permisos dentro de las authorities.
     * Cuando usamos hasRole("ADMINISTRATOR"), Spring agrega automáticamente el prefijo "ROLE_".
     * Por lo tanto, hasRole("ADMINISTRATOR") termina comprobando la autoridad "ROLE_ADMINISTRATOR".
     * Esta autoridad debe existir dentro de las authorities del objeto Authentication.
     * En cambio, hasAuthority("CREATE_ONE_PRODUCT") busca exactamente esa autoridad, sin agregar "ROLE_".
     * Por eso podemos tener roles y permisos juntos dentro de la misma lista de authorities.
     * No debemos definir el enum como ROLE_ADMINISTRATOR si usamos hasRole(),
     * porque Spring volvería a agregar "ROLE_" y buscaría "ROLE_ROLE_ADMINISTRATOR".
     * Así, mantenemos ADMINISTRATOR en nuestro enum y lo representamos como ROLE_ADMINISTRATOR en las authorities.
     * Esto ocurre porque Spring Security realiza la autorización principalmente sobre GrantedAuthority.
     * Aunque usamos hasRole(), internamente se termina comprobando una authority específica.
     * Es decir, hasRole("ADMINISTRATOR") no compara directamente el enum Role,
     * sino que busca la GrantedAuthority "ROLE_ADMINISTRATOR" dentro de Authentication.
     */

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
