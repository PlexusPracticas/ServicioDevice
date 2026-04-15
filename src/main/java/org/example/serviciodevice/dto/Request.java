package org.example.serviciodevice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Request{
    private Integer id;
    @NotBlank(message = "El número de serie es obligatorio y debe ser un alfanumérico de 11 caracteres")
    private String serialNumber;
    private String brand;
    private String model;
    @NotBlank(message = "OperatingSystem – Obligatorio.")
    @Pattern(regexp="^[A-Za-z0-9]{3,50}$",message = "El sistema operativo es-obligatorio.Puede tener entre 3,50")
    private String operatingSystem;
    private Integer  assignedTo;
}
