package org.example.serviciodevice.service;

import org.example.serviciodevice.dto.*;
import org.example.serviciodevice.model.Device;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DeviceService {
    Page<Device> getAll(Pageable pageable);
    Asignacion findByAssignedTo(Integer assigned);
    NumeroSerie findBySerialNumber(String serialNumber);
    Device saveDevice(CreateItem request);
    Device updateAssignedToBySerialNumber(UpdateAssigned request);
    DeletedResult deleteBySerialNumbers(List<String> serialNumbers);
}
