package org.example.serviciodevice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="devices")
public class Device {
    @Id
    /**se autoGenera un valor cada que se inserta un dato
     strategy = GenerationType.IDENTITY se autoincrementa el valor*/
    @Column(name="id",nullable = false,unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="brand")
    private String brand;

    @Column(name="model")
    private String model;

    @Column(name="serial_number",nullable = false,unique = true)
    @NotBlank(message = "El número de serie es obligatorio y debe ser un alfanumerico de 11 caracteres")
    @Size(min = 11, max = 11, message = "El número de serie es obligatorio y debe ser un alfanumerico de 11 caracteres")
    @Pattern(regexp = "^[A-Za-z0-9]+$",
            message = "El número de serie es obligatorio y debe ser un alfanumerico de 11 caracteres")
    private String serialNumber;

    @Column(name="os")
    @NotBlank(message = "El sistema operativo es obligatorio. Puede tener entre 3 y 50 caracteres")
    @Size(min = 3, max = 50, message = "El sistema operativo es obligatorio. Puede tener entre 3 y 50 caracteres")
    @Pattern(regexp = "^[A-Za-z0-9 ]+$",
            message = "El sistema operativo es obligatorio. Puede tener entre 3 y 50 caracteres")
    private String operatingSystem;

    @Column(length = 50,name="assigned_to",unique = true)
    private Integer assignedTo;

}
