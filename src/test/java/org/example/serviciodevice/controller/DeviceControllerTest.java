package org.example.serviciodevice.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.serviciodevice.dto.*;
import org.example.serviciodevice.mapper.DeviceMapper;
import org.example.serviciodevice.model.Device;
import org.example.serviciodevice.service.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DeviceService service;

    @MockBean
    private DeviceMapper mapper;


    @Test
    void listAll_ok() throws Exception {
        Mockito.when(service.getAll(Mockito.any()))
                .thenReturn(new PageImpl<>(List.of()));

        Mockito.when(mapper.toResponseList(Mockito.any()))
                .thenReturn(List.of());

        mockMvc.perform(get("/devices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.devices").exists());
    }

    @Test
    void listAll_badParams() throws Exception {
        mockMvc.perform(get("/devices")
                        .param("page", "-1")
                        .param("size", "0"))
                .andExpect(status().isBadRequest());
    }


    @Test
    void findByAssigned_ok() throws Exception {
        Asignacion asignacion = new Asignacion();

        Mockito.when(service.findByAssignedTo(1))
                .thenReturn(asignacion);

        mockMvc.perform(get("/devices/assignation/1"))
                .andExpect(status().isOk());
    }

    @Test
    void findByAssigned_notNumeric_returns400() throws Exception {
        mockMvc.perform(get("/devices/assignation/abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findByAssigned_notFound_returns404() throws Exception {
        Mockito.when(service.findByAssignedTo(9))
                .thenReturn(null);

        mockMvc.perform(get("/devices/assignation/9"))
                .andExpect(status().isNotFound());
    }


    @Test
    void findBySerialNumber_ok() throws Exception {
        NumeroSerie response = new NumeroSerie();

        Mockito.when(service.findBySerialNumber("ABC12345678"))
                .thenReturn(response);

        mockMvc.perform(get("/devices/serial-number/ABC12345678"))
                .andExpect(status().isOk());
    }

    @Test
    void findBySerialNumber_invalid_returns400() throws Exception {
        mockMvc.perform(get("/devices/serial-number/123"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findBySerialNumber_notFound_returns404() throws Exception {
        Mockito.when(service.findBySerialNumber("ABC12345678"))
                .thenReturn(null);

        mockMvc.perform(get("/devices/serial-number/ABC12345678"))
                .andExpect(status().isNotFound());
    }


    @Test
    void deleteBySerialNumbers_ok() throws Exception {
        DeletedResult result =
                new DeletedResult(List.of("ABC12345678"), List.of("DEF12345678"));

        Mockito.when(service.deleteBySerialNumbers(Mockito.any()))
                .thenReturn(result);

        mockMvc.perform(delete("/devices/serial-number/ABC12345678,DEF12345678"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "Deleted: [ABC12345678]\nNotDeleted: [DEF12345678]"
                ));
    }

    @Test
    void deleteBySerialNumbers_invalid_returns400() throws Exception {
        mockMvc.perform(delete("/devices/serial-number/123,456"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteBySerialNumbers_notFound_returns404() throws Exception {
        DeletedResult result =
                new DeletedResult(List.of(), List.of("ABC12345678"));

        Mockito.when(service.deleteBySerialNumbers(Mockito.any()))
                .thenReturn(result);

        mockMvc.perform(delete("/devices/serial-number/ABC12345678"))
                .andExpect(status().isNotFound());
    }


    @Test
    void createDevices_ok() throws Exception {
        CreateItem item = new CreateItem();
        item.setSerialNumber("ABC12345678");
        item.setOperatingSystem("Android");

        CreateRequest request = new CreateRequest();
        request.setDevices(List.of(item));

        Mockito.when(service.saveDevice(Mockito.any()))
                .thenReturn(new Device());

        mockMvc.perform(post("/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void createDevices_partial_returns206() throws Exception {
        CreateItem item = new CreateItem();
        item.setSerialNumber("ABC12345678");
        item.setOperatingSystem("Android");

        CreateRequest request = new CreateRequest();
        request.setDevices(List.of(item));

        Mockito.when(service.saveDevice(Mockito.any()))
                .thenThrow(new RuntimeException("Error"));

        mockMvc.perform(post("/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isPartialContent())
                .andExpect(jsonPath("$.warning").exists());
    }


    @Test
    void updateDevices_ok() throws Exception {
        UpdateAssigned update = new UpdateAssigned();
        update.setSerialNumber("ABC12345678");
        update.setAssignedTo(1);

        UpdateAssignedToRequest request = new UpdateAssignedToRequest();
        request.setDevices(List.of(update));

        Mockito.when(service.updateAssignedToBySerialNumber(Mockito.any()))
                .thenReturn(new Device());

        mockMvc.perform(put("/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void updateDevices_partial_returns206() throws Exception {
        UpdateAssigned update = new UpdateAssigned();
        update.setSerialNumber("ABC12345678");
        update.setAssignedTo(1);

        UpdateAssignedToRequest request = new UpdateAssignedToRequest();
        request.setDevices(List.of(update));

        Mockito.when(service.updateAssignedToBySerialNumber(Mockito.any()))
                .thenThrow(new RuntimeException("Error"));

        mockMvc.perform(put("/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isPartialContent());
    }

    @Test
    void updateDevices_emptyRequest_returns400() throws Exception {
        UpdateAssignedToRequest request = new UpdateAssignedToRequest();
        request.setDevices(List.of());

        mockMvc.perform(put("/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
