package SpringSecurity.SpringSecurity.persistance.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "product")
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // que se genere automáticamente y que se autoincremente
    @Column(unique = true, nullable = false)
    // que sea único y que no sea nulo
    private Long id;

    private String name;
    private BigDecimal price;

    //# accedida solo a traves de la clase producto
    @Enumerated(EnumType.STRING) // para guardar un valor de string en vez de el valor numerico ordinal
    private ProductStatus status;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public static enum ProductStatus{
        ENABLED,DISABLED
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    // Getter - Setter
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }
}
//** ENUm
//#  En Java, los enums (enumerations) son tipos de datos especiales que permiten definir un conjunto fijo de constantes con nombre.
//#  Esto hace que el código sea más legible, mantenible y menos propenso a errores
//... Los enum en java siempre representan un valor ordinal que comienza por 0, 1, 2
//...