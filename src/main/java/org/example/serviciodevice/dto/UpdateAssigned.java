package org.example.serviciodevice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateAssigned {
    @NotBlank(message = "El número de serie es obligatorio y debe ser un alfanumérico de 11 caracteres")
    @Pattern(regexp = "^[A-Za-z0-9]{11}$",
            message = "El número de serie debe ser un alfanumérico de 11 caracteres")
    private String serialNumber;
    private Integer assignedTo;
}
