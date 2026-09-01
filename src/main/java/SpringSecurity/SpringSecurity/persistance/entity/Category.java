package SpringSecurity.SpringSecurity.persistance.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Autoincrementable y automatico
    @Column(unique = true, nullable = false)
    // no nulo y unico
    private long id;

    private String nombre;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public CategoryStatus getStatus() {
        return status;
    }

    public void setStatus(CategoryStatus status) {
        this.status = status;
    }

    @Enumerated(EnumType.STRING)
    private CategoryStatus status;

    public static enum CategoryStatus{
        ENABLED,DISABLED
    }
}
