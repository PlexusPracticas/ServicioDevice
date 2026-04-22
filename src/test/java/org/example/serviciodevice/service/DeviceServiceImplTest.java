package org.example.serviciodevice.service;

import org.example.serviciodevice.dto.*;
import org.example.serviciodevice.mapper.DeviceMapper;
import org.example.serviciodevice.model.Device;
import org.example.serviciodevice.repository.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DeviceServiceImplTest {

    @Mock
    private DeviceRepository repository;

    @Mock
    private DeviceMapper mapper;

    @InjectMocks
    private DeviceServiceImpl service;


    @Test
    void getAll_ok() {
        Pageable pageable = PageRequest.of(0, 10);

        when(repository.findAll(pageable))
                .thenReturn(new PageImpl<>(List.of(new Device())));

        Page<Device> result = service.getAll(pageable);

        assertEquals(1, result.getTotalElements());
    }

    @Test
    void getAll_dataAccessException_throwsRuntime() {
        Pageable pageable = PageRequest.of(0, 10);

        when(repository.findAll(pageable))
                .thenThrow(new DataAccessException("DB error") {});

        assertThrows(RuntimeException.class,
                () -> service.getAll(pageable));
    }


    @Test
    void findByAssignedTo_ok() {
        Device device = new Device();
        device.setAssignedTo(1);

        when(repository.findByAssignedTo(1))
                .thenReturn(Optional.of(device));

        when(mapper.toAsignacion(device))
                .thenReturn(new Asignacion());

        Asignacion result = service.findByAssignedTo(1);

        assertNotNull(result);
    }

    @Test
    void findByAssignedTo_null_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class,
                () -> service.findByAssignedTo(null));
    }


    @Test
    void findByAssignedTo_notFound_returnsNull() {

        when(repository.findByAssignedTo(9)).thenReturn(Optional.empty());
        Asignacion result = service.findByAssignedTo(9);
        assertNull(result);
        verify(repository).findByAssignedTo(9);
    }



    @Test
    void findBySerialNumber_ok() {
        Device device = new Device();
        device.setSerialNumber("ABC12345678");

        when(repository.findBySerialNumber("ABC12345678"))
                .thenReturn(Optional.of(device));

        when(mapper.toNumSerie(device))
                .thenReturn(new NumeroSerie());

        NumeroSerie result =
                service.findBySerialNumber("ABC12345678");

        assertNotNull(result);
    }

    @Test
    void findBySerialNumber_invalid_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class,
                () -> service.findBySerialNumber("123"));
    }

    @Test
    void findBySerialNumber_notFound_returnsNull() {
        when(repository.findBySerialNumber("ABC12345678"))
                .thenReturn(Optional.empty());

        NumeroSerie result =
                service.findBySerialNumber("ABC12345678");

        assertNull(result);
    }


    @Test
    void saveDevice_ok() {
        CreateItem request = new CreateItem();
        request.setSerialNumber("ABC12345678");
        request.setOperatingSystem("Android");

        when(repository.save(any(Device.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Device result = service.saveDevice(request);

        assertEquals("ABC12345678", result.getSerialNumber());
    }



    @Test
    void updateAssignedTo_ok() {

        UpdateAssigned request = new UpdateAssigned();
        request.setSerialNumber("ABC12345678");
        request.setAssignedTo(20);

        Device device = new Device();
        device.setSerialNumber("ABC12345678");
        device.setAssignedTo(20);

        when(repository.updateAssignedToBySerialNumber(20, "ABC12345678"))
                .thenReturn(1);

        when(repository.findBySerialNumber("ABC12345678"))
                .thenReturn(Optional.of(device));

        Device result = service.updateAssignedToBySerialNumber(request);

        assertNotNull(result);
        assertEquals(20, result.getAssignedTo());

        verify(repository).updateAssignedToBySerialNumber(20, "ABC12345678");
        verify(repository).findBySerialNumber("ABC12345678");
    }



    @Test
    void updateAssignedTo_notFound_throwsException() {

        UpdateAssigned request = new UpdateAssigned();
        request.setSerialNumber("ABC12345678");
        request.setAssignedTo(10);

        when(repository.updateAssignedToBySerialNumber(
                any(),
                eq("ABC12345678")
        )).thenReturn(0);

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.updateAssignedToBySerialNumber(request)
        );
        assertTrue(ex.getMessage().contains("Device no encontrado"));
    }


    @Test
    void deleteBySerialNumbers_ok() {
        Device d1 = new Device();
        d1.setSerialNumber("ABC12345678");

        Device d2 = new Device();
        d2.setSerialNumber("DEF12345678");

        when(repository.findBySerialNumberIn(List.of("ABC12345678", "DEF12345678")))
                .thenReturn(List.of(d1, d2));

        when(repository.deleteBySerialNumberIn(List.of("ABC12345678", "DEF12345678")))
                .thenReturn(2);

        DeletedResult result =
                service.deleteBySerialNumbers(List.of("ABC12345678", "DEF12345678"));

        assertEquals(List.of("ABC12345678", "DEF12345678"), result.getDeleted());
        assertTrue(result.getNotDeleted().isEmpty());
    }

    @Test
    void deleteBySerialNumbers_partial() {
        Device d1 = new Device();
        d1.setSerialNumber("ABC12345678");

        when(repository.findBySerialNumberIn(List.of("ABC12345678", "DEF12345678")))
                .thenReturn(List.of(d1));

        when(repository.deleteBySerialNumberIn(List.of("ABC12345678")))
                .thenReturn(1);

        DeletedResult result =
                service.deleteBySerialNumbers(List.of("ABC12345678", "DEF12345678"));

        assertEquals(List.of("ABC12345678"), result.getDeleted());
        assertEquals(List.of("DEF12345678"), result.getNotDeleted());
    }

}
