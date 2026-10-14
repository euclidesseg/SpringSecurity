package SpringSecurity.SpringSecurity.persistance.entity.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String name;

    @JsonIgnore // para que no serialize infinitamente
    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    // relacion uno a muchos lo que indica que un rol va a tener muchos permisos asociados
    //fetch = FetchType.EAGER hará que me traiga todo incluso los grantedPermissions
    // esto se hace porque en las relaciones OneToMany no se optienen todas las relaciones por defecto como en ManyToOne
    private List<GrantedPermission>  grantedPermissions;

    public List<GrantedPermission> getGrantedPermissions() {
        return grantedPermissions;
    }

    public void setGrantedPermissions(List<GrantedPermission> grantedPermissions) {
        this.grantedPermissions = grantedPermissions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
