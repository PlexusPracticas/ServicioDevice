package org.example.serviciodevice.service;

import org.example.serviciodevice.dto.CreateItem;
import org.example.serviciodevice.dto.DeletedResult;
import org.example.serviciodevice.dto.CreateRequest;
import org.example.serviciodevice.dto.UpdateAssigned;
import org.example.serviciodevice.mapper.DeviceMapper;
import org.example.serviciodevice.model.Device;
import org.example.serviciodevice.repository.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.dao.DataAccessException;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DeviceServiceImplTest {
/*
    @Mock
    private DeviceRepository repository;

    @Mock
    private DeviceMapper mapper;

    @InjectMocks
    private DeviceServiceImpl service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // TEST saveDevice (POST)
    @Test
    void testSaveDevice_AllCorrect() {
        CreateItem item = new CreateItem();
        item.setSerialNumber("RF8R10KGN7D");
        item.setOperatingSystem("Android");

        CreateRequest req = new CreateRequest();
        req.setDevices(List.of(item));

        Device device = new Device();
        when(mapper.toEntity(any())).thenReturn(device);
        when(repository.save(any())).thenReturn(device);

        DeletedResult result = service.saveDevice(req);

        assertTrue(result.getNotDeleted().isEmpty());
        assertEquals(List.of("RF8R10KGN7D"), result.getDeleted());
    }

    @Test
    void testSaveDevice_InvalidSerial() {
        CreateItem item = new CreateItem();
        item.setSerialNumber("INVALID");
        item.setOperatingSystem("Android");

        CreateRequest req = new CreateRequest();
        req.setDevices(List.of(item));

        DeletedResult result = service.saveDevice(req);

        assertEquals(List.of("INVALID"), result.getNotDeleted());
        assertTrue(result.getDeleted().isEmpty());
    }

    @Test
    void testSaveDevice_InvalidOS() {
        CreateItem item = new CreateItem();
        item.setSerialNumber("RF8R10KGN7D");
        item.setOperatingSystem("xx"); // demasiado corto

        CreateRequest req = new CreateRequest();
        req.setDevices(List.of(item));

        DeletedResult result = service.saveDevice(req);

        assertEquals(List.of("RF8R10KGN7D"), result.getNotDeleted());
        assertTrue(result.getDeleted().isEmpty());
    }

    @Test
    void testSaveDevice_ExceptionDuringSave() {
        CreateItem item = new CreateItem();
        item.setSerialNumber("RF8R10KGN7D");
        item.setOperatingSystem("Android");

        CreateRequest req = new CreateRequest();
        req.setDevices(List.of(item));

        when(mapper.toEntity(any())).thenReturn(new Device());
        when(repository.save(any())).thenThrow(new RuntimeException("DB error"));

        DeletedResult result = service.saveDevice(req);

        assertEquals(List.of("RF8R10KGN7D"), result.getNotDeleted());
    }

    // TEST updateAssignedToBySerialNumber (PUT)
    @Test
    void testUpdateAssigned_AllCorrect() {
        UpdateAssigned item = new UpdateAssigned();
        item.setSerialNumber("RF8R10KGN7D");
        item.setAssignedTo(5);

        when(repository.updateAssignedToBySerialNumber(5, "RF8R10KGN7D")).thenReturn(1);

        List<String> failed = service.updateAssignedToBySerialNumber(List.of(item));

        assertTrue(failed.isEmpty());
    }

    @Test
    void testUpdateAssigned_InvalidSerial() {
        UpdateAssigned item = new UpdateAssigned();
        item.setSerialNumber("INVALID");
        item.setAssignedTo(5);

        List<String> failed = service.updateAssignedToBySerialNumber(List.of(item));

        assertEquals(List.of("INVALID"), failed);
    }

    @Test
    void testUpdateAssigned_NotUpdated() {
        UpdateAssigned item = new UpdateAssigned();
        item.setSerialNumber("RF8R10KGN7D");
        item.setAssignedTo(10);

        when(repository.updateAssignedToBySerialNumber(10, "RF8R10KGN7D"))
                .thenReturn(0); // no actualizado → no existe en BD

        List<String> failed = service.updateAssignedToBySerialNumber(List.of(item));

        assertEquals(List.of("RF8R10KGN7D"), failed);
    }

    @Test
    void testUpdateAssigned_DBError() {
        UpdateAssigned item = new UpdateAssigned();
        item.setSerialNumber("RF8R10KGN7D");
        item.setAssignedTo(10);

        when(repository.updateAssignedToBySerialNumber(any(), any()))
                .thenThrow(new DataAccessException("db fail") {});

        List<String> failed = service.updateAssignedToBySerialNumber(List.of(item));

        assertEquals(List.of("RF8R10KGN7D"), failed);
    }

    // TEST deleteBySerialNumbers (DELETE)
    @Test
    void testDeleteDevices_AllDeleted() {
        List<String> serials = List.of("RF8R10KGN7D");

        Device d = new Device();
        d.setSerialNumber("RF8R10KGN7D");

        when(repository.findBySerialNumberIn(serials)).thenReturn(List.of(d));
        when(repository.deleteBySerialNumberIn(serials)).thenReturn(1);

        DeletedResult result = service.deleteBySerialNumbers(serials);

        assertEquals(List.of("RF8R10KGN7D"), result.getDeleted());
        assertTrue(result.getNotDeleted().isEmpty());
    }

    @Test
    void testDeleteDevices_PartialFail() {
        List<String> serials = List.of("RF8R10KGN7D", "EE8R10KGN7D");

        Device d = new Device();
        d.setSerialNumber("RF8R10KGN7D");

        when(repository.findBySerialNumberIn(serials)).thenReturn(List.of(d));

        DeletedResult result = service.deleteBySerialNumbers(serials);

        assertEquals(List.of("RF8R10KGN7D"), result.getDeleted());
        assertEquals(List.of("EE8R10KGN7D"), result.getNotDeleted());
    }*/
}
