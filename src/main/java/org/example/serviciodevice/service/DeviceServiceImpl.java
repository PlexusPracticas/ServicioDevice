package org.example.serviciodevice.service;

import org.example.serviciodevice.dto.*;
import org.example.serviciodevice.mapper.DeviceMapper;
import org.example.serviciodevice.model.Device;
import org.example.serviciodevice.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService{

    @Autowired
    private DeviceRepository repository;
    private DeviceMapper mapper;

    public DeviceServiceImpl(DeviceRepository repository,DeviceMapper mapper){
        this.repository=repository;
        this.mapper=mapper;
    }


    @Override
    public Page<Device> getAll(Pageable pageable) {
        try{
            return repository.findAll(pageable);
        }catch(DataAccessException e){
            throw new RuntimeException("Se ha producido un error tecnico, pruebe de nuevo",e);
        }
    }

    @Override
    public Asignacion findByAssignedTo(Integer assigned){
        if(assigned==null){
            throw new IllegalArgumentException("El id del empleado asignado debe ser un valor numerico");
        }
        try {
            Device device=repository.findByAssignedTo(assigned).orElseThrow(()->new RuntimeException("NOT_FOUND"));
            if(device==null){
                return null;
            }
            return mapper.toAsignacion(device);
        }catch(DataAccessException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public NumeroSerie findBySerialNumber(String serialNumber) {
        if(serialNumber == null || !serialNumber.matches("^[A-Za-z0-9]{11}$")){
            throw new IllegalArgumentException("El número de serie debe ser un alfanumérico de longitud 11");
        }
        try {
            Device device= repository.findBySerialNumber(serialNumber).orElse(null);
            if(device==null){
                return null;
            }
            return mapper.toNumSerie(device);
        }catch(DataAccessException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Device saveDevice(CreateItem request){
        Device device=new Device();
        device.setSerialNumber(request.getSerialNumber());
        device.setBrand(request.getBrand());
        device.setModel(request.getModel());
        device.setOperatingSystem(request.getOperatingSystem());
        device.setAssignedTo(request.getAssignedTo());
        return repository.save(device);
    }

    @Override
    public Device updateAssignedToBySerialNumber(UpdateAssigned request) {
        Device device = repository.findBySerialNumber(request.getSerialNumber()).orElseThrow(()->new RuntimeException("Decive no encontrado: "+request.getSerialNumber()));
        if (request.getSerialNumber()!=null)
            device.setSerialNumber(request.getSerialNumber());
        if(request.getAssignedTo()!=null)
            device.setAssignedTo(request.getAssignedTo());
        return repository.save(device);
    }


    @Override
    public DeletedResult deleteBySerialNumbers(List<String> serialNumbers) {
        List<Device> existentes = repository.findBySerialNumberIn(serialNumbers);
        List<String> deleted = existentes.stream().map(Device::getSerialNumber).toList();

        List<String> notDeleted = serialNumbers.stream()
                .filter(sn -> !deleted.contains(sn))
                .toList();

        int deletedCount = 0;
        if (!deleted.isEmpty()) {
            deletedCount = repository.deleteBySerialNumberIn(deleted);
        }

        return new DeletedResult(deleted, notDeleted);
    }

}
