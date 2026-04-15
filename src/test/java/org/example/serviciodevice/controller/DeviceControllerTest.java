package org.example.serviciodevice.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.serviciodevice.dto.Asignacion;
import org.example.serviciodevice.dto.CreateItem;
import org.example.serviciodevice.dto.CreateRequest;
import org.example.serviciodevice.dto.DeletedResult;
import org.example.serviciodevice.dto.UpdateAssigned;
import org.example.serviciodevice.dto.UpdateAssignedToRequest;
import org.example.serviciodevice.mapper.DeviceMapper;
import org.example.serviciodevice.model.Device;
import org.example.serviciodevice.service.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DeviceController.class)
class DeviceControllerTest {
/*
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DeviceService service;
    @MockBean
    private DeviceMapper mapper;
    @Autowired
    private ObjectMapper objectMapper;
    private Device device;

    @BeforeEach
    void setup() {
        device = new Device();
        device.setSerialNumber("RF8R10KGN7D");
        device.setBrand("Samsung");
        device.setModel("Galaxy A20");
        device.setOperatingSystem("Android");
        device.setAssignedTo(2);
    }

    // GET /devices?page=X&size=Y
    @Test
    void testListAllOk() throws Exception {
        when(service.getAll(any()))
                .thenReturn(org.springframework.data.domain.Page.empty());

        mockMvc.perform(get("/devices?page=0&size=10"))
                .andExpect(status().isOk());
    }

    @Test
    void testListAllBadRequest() throws Exception {
        mockMvc.perform(get("/devices?page=-1&size=10"))
                .andExpect(status().isBadRequest());
    }

    // GET /devices/assignation/{assigned}
    @Test
    void testFindByAssignedOk() throws Exception {
        when(service.findByAssignedTo(2)).thenReturn(Optional.of(device));
        when(mapper.toAsignacion(any())).thenReturn(new Asignacion());

        mockMvc.perform(get("/devices/assignation/2"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindByAssignedNotFound() throws Exception {
        when(service.findByAssignedTo(100)).thenReturn(Optional.empty());

        mockMvc.perform(get("/devices/assignation/100"))
                .andExpect(status().isNotFound());
    }

    // DELETE /devices/serialNumber/{csv}
    @Test
    void testDeleteDevicesOk() throws Exception {
        DeletedResult dr = new DeletedResult(
                List.of("RF8R10KGN7D"),
                List.of()
        );

        when(service.deleteBySerialNumbers(any())).thenReturn(dr);

        mockMvc.perform(delete("/devices/serialNumber/RF8R10KGN7D"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteDevicesInvalidSerial() throws Exception {
        mockMvc.perform(delete("/devices/serialNumber/INVALID"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteDevicesNotFound() throws Exception {
        DeletedResult dr = new DeletedResult(
                List.of(),
                List.of("RF8R10KGN7D")
        );

        when(service.deleteBySerialNumbers(any())).thenReturn(dr);

        mockMvc.perform(delete("/devices/serialNumber/RF8R10KGN7D"))
                .andExpect(status().isNotFound());
    }

    // POST /devices
    @Test
    void testCreateDevicesAllOk() throws Exception {
        CreateItem item = new CreateItem();
        item.setSerialNumber("RF8R10KGN7D");
        item.setOperatingSystem("Android");

        CreateRequest req = new CreateRequest();
        req.setDevices(List.of(item));

        DeletedResult dr = new DeletedResult(
                List.of("RF8R10KGN7D"),
                List.of()
        );

        when(service.saveDevice(any())).thenReturn(dr);

        mockMvc.perform(post("/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateDevicesPartialFail() throws Exception {
        CreateItem item = new CreateItem();
        item.setSerialNumber("INVALID");
        item.setOperatingSystem("Android");

        CreateRequest req = new CreateRequest();
        req.setDevices(List.of(item));

        DeletedResult dr = new DeletedResult(
                List.of(),
                List.of("INVALID")
        );

        when(service.saveDevice(any())).thenReturn(dr);

        mockMvc.perform(post("/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().is(206));
    }

    // PUT /devices
    @Test
    void testUpdateDevicesAllOk() throws Exception {
        UpdateAssigned item = new UpdateAssigned();
        item.setSerialNumber("RF8R10KGN7D");
        item.setAssignedTo(2);

        UpdateAssignedToRequest req = new UpdateAssignedToRequest();
        req.setDevices(List.of(item));

        when(service.updateAssignedToBySerialNumber(any())).thenReturn(List.of());

        mockMvc.perform(put("/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateDevicesPartialFail() throws Exception {
        UpdateAssigned item = new UpdateAssigned();
        item.setSerialNumber("INVALID");
        item.setAssignedTo(2);

        UpdateAssignedToRequest req = new UpdateAssignedToRequest();
        req.setDevices(List.of(item));

        when(service.updateAssignedToBySerialNumber(any()))
                .thenReturn(List.of("INVALID"));

        mockMvc.perform(put("/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().is(206));
    }*/
}
