package org.example.serviciodevice.mapper;
import org.example.serviciodevice.dto.Asignacion;
import org.example.serviciodevice.dto.NumeroSerie;
import org.example.serviciodevice.model.Device;
import org.mapstruct.Mapper;
import org.example.serviciodevice.dto.CreateItem;
import org.example.serviciodevice.dto.Response;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DeviceMapper {
    Asignacion toAsignacion(Device device);
    NumeroSerie toNumSerie(Device entity);
    List<Response> toResponseList(List<Device> entities);
    Device toEntity(CreateItem dto);

}
