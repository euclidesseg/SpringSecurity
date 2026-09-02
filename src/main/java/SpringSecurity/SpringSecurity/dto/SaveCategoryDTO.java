package SpringSecurity.SpringSecurity.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public class SaveCategoryDTO implements Serializable {

    // @NotBlank validará que este atributo no venga con un valor vacío.
    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
