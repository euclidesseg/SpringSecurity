package SpringSecurity.SpringSecurity.persistance.entity.security;

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

    @OneToMany(mappedBy = "role")
    // relacion uno a muchos lo que indica que un rol va a tener muchos permisos asociados
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
