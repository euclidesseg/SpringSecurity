package SpringSecurity.SpringSecurity.persistance.entity.security;

import jakarta.persistence.*;

@Entity
@Table(name = "operation")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Ej: READ_ALL_PRODUCTS
    private String path; // Ej. /{productId}/disabled  el path base estaría en el modulo y sera /products
    private String http_method;
    private boolean permitAll;

    @ManyToOne
    // muchas operaciones estarán relacionadas a un mismo modulo
    @JoinColumn(name = "module_id")
    private Module module;


    //=== setter and setters
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
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public Module getModule() {
        return module;
    }
    public boolean isPermit_all() {
        return permitAll;
    }

    public void setPermit_all(boolean permitAll) {
        this.permitAll = permitAll;
    }

    public void setModule(Module module) {
        this.module = module;
    }
    public String getHttp_method() {
        return http_method;
    }
    public void setHttp_method(String http_method) {
        this.http_method = http_method;
    }

    //=== los paths dependen de cada modulo como
}
