package SpringSecurity.SpringSecurity.dto;

import java.io.Serializable;
import java.math.BigDecimal;

// Serializable se usa ya que estos datos van a viajar a traves del protocolo http
public class SaveProductDTO implements Serializable {

    private String nombre;
    private BigDecimal price;
    private Long CategoryId;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(Long categoryId) {
        CategoryId = categoryId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
