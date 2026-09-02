package SpringSecurity.SpringSecurity.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.math.BigDecimal;

// Serializable se usa ya que estos datos van a viajar a traves del protocolo http
public class SaveProductDTO implements Serializable {

    // Estas validaciones son manejadas por la clase de excepcion GlobalExcceptionHandler
    @NotBlank(message = "El nombre del producto no puede estar vacío")
    private String name;
    @DecimalMin(value = "0.01",message = "El precio debe ser mayor a 0.01")
    private BigDecimal price;
    @Min(value = 1)
    private Long categoryId;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
