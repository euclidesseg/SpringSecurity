package SpringSecurity.SpringSecurity.persistance.entity.security;

import jakarta.persistence.*;

@Entity
@Table(name = "module")
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; //PRODUCT, CATEGORY, AUTH,
    private String base_path; // ejemplo /products estos serian los requestmaping de cada controlador

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBase_path() {
        return base_path;
    }

    public void setBase_path(String base_path) {
        this.base_path = base_path;
    }
}
