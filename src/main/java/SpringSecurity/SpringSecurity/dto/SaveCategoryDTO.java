package SpringSecurity.SpringSecurity.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public class SaveCategoryDTO implements Serializable {

    // @NotBlank validará que este atributo no venga con un valor vacío.
    @NotBlank
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
