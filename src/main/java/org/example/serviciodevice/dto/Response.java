package org.example.serviciodevice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "serialNumber",
        "brand",
        "model",
        "operatingSystem",
        "assignedTo"
})
public class Response {
    private Integer id;
    private String serialNumber;
    private String brand;
    private String model;
    private String operatingSystem;
    private Integer assignedTo;
}
