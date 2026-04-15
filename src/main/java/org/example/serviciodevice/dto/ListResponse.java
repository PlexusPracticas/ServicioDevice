package org.example.serviciodevice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "devices",
        "totalPages",
        "totalElements"
})
public class ListResponse {
    private List<Response> devices;
    private int totalPages;
    private long totalElements;
}
