package org.example.serviciodevice.controller;

import jakarta.validation.Valid;
import org.example.serviciodevice.dto.*;
import org.example.serviciodevice.dto.NumeroSerie;
import org.example.serviciodevice.mapper.DeviceMapper;
import org.example.serviciodevice.model.Device;
import org.example.serviciodevice.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    private static final String ERROR_MSG = "Parámetros mal informados. Los valores mínimos aceptados son size=1 y page=0. No introduzca nada para obtener los valores por defecto";
    private DeviceService service;
    private final DeviceMapper mapper;

    @Autowired
    public DeviceController(DeviceService service, DeviceMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<?> listAll(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        /* Validación400 page no  puede ser negativo,size debe ser almenos  1*/
        if (page < 0 || size < 1) {
            return ResponseEntity.badRequest().body(Map.of("message", ERROR_MSG)
            );
        }
        try {
            /*representa la paginacion*/
            Pageable pageable = PageRequest.of(page, size);
            /**Llama a la clase de servicio para obtener los datos paginados*/
            Page<Device> devicePage = service.getAll(pageable);
            ListResponse response = new ListResponse();
            response.setDevices(mapper.toResponseList(devicePage.getContent()));
            response.setTotalPages(devicePage.getTotalPages());
            response.setTotalElements(devicePage.getTotalElements());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("message", "Se ha producido un error técnico, pruebe de nuevo"));
        }
    }

    @GetMapping("/assignation/{assigned}")
    public ResponseEntity<?> findByAssignedTo(@PathVariable String assigned){
        int assignedInt;
        try{
         assignedInt=Integer.parseInt(assigned);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("El id del empleado asignado debe ser un valor numerico");
        }
        try{
            Asignacion response =service.findByAssignedTo(assignedInt);
            if (response == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado ningún dispositivo");
            }
            return ResponseEntity.ok(response);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Se ha producido un error técnico, pruebe de nuevo");
        }
    }

    @GetMapping("/serial-number/{serialNumber}")
    public ResponseEntity<?> findByNumeroSerie(@PathVariable String serialNumber){
        if(serialNumber==null||!serialNumber.matches("^[a-zA-Z0-9]{11}$")){
            return ResponseEntity.badRequest().body("“El número de serie debe ser un alfanumérico de longitud 11");
        }
        try{
            NumeroSerie device=service.findBySerialNumber(serialNumber);
            if(device ==null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado ningún dispositivo");
            }
            return ResponseEntity.ok(device);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Se ha producido un error técnico, pruebe de nuevo");
        }
    }

    @DeleteMapping("/serial-number/{serialNumbers}")
    public ResponseEntity<?> deleteBySerialNumbers(@PathVariable String serialNumbers) {
        if (serialNumbers == null || serialNumbers.isEmpty()) {
            return ResponseEntity.badRequest().body("La lista no puede estar vacia.");
        }
        //Parseo(split por coma) se separa los parametros y se guardan en la lista normalizacion
        List<String> normalizacion = Arrays.stream(serialNumbers.split(",")).toList();
        //normalizacion
        List<String> invalidos = normalizacion.stream().filter(sn-> !sn.matches("^[A-Za-z0-9]{11}$")).toList();
        if(!invalidos.isEmpty()){
            return ResponseEntity.badRequest().body(
                    "Los números de serie deben ser alfanuméricos"
                    +" de longitud 11 y separados por comas");
        }
        DeletedResult eliminado = service.deleteBySerialNumbers(normalizacion);
        if (eliminado.getDeleted().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("numero de serie no encontrado");
        }//
        return ResponseEntity.ok("Deleted: "+eliminado.getDeleted()+
                                        "\nNotDeleted: "+eliminado.getNotDeleted());
    }

    @PostMapping
    public ResponseEntity<?> createDevices(@RequestBody CreateRequest request) {
        List<String> failedSerialNumber = new ArrayList<>();
        List<Device> inserted = new ArrayList<>();
        for(CreateItem req: request.getDevices()){
            try{
                validate(req);
                Device device=service.saveDevice(req);
                inserted.add(device);
            }catch (Exception e){
                failedSerialNumber.add(e.getMessage());
            }
        }
        if(failedSerialNumber.isEmpty()){
            return ResponseEntity.status(HttpStatus.CREATED).body(inserted);
        }
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(Map.of("warning",
                "Not all employees where processed. Please check requested: " +String.join(",",failedSerialNumber)));
    }

    @PutMapping
    public ResponseEntity<?> updateDevices(@RequestBody UpdateAssignedToRequest request) {
        if (request.getDevices() == null || request.getDevices().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("warning", "Debe enviar al menos un dispositivo"));
        }
        List<String> failed = new ArrayList<>();
        List<Device> updated = new ArrayList<>();

        for(UpdateAssigned actu:request.getDevices()){
            try{
                validateUpdate(actu);
                Device device=service.updateAssignedToBySerialNumber(actu);
                updated.add(device);
            }catch(Exception e){
                failed.add(actu.getSerialNumber());
            }
        }
        if (!failed.isEmpty()) {
            return ResponseEntity.status(206).body(Map.of("warning", "Not all devices where updated. Please check requested: "
                    + String.join(", ", failed)));
        }
        // Si todos se actualizan correctamente da 201
        return ResponseEntity.status(201).build();
    }

    private void validate(CreateItem req) {
        if (req.getSerialNumber() == null || !req.getSerialNumber().matches("^[A-Za-z0-9]{11}$")) {
            throw new RuntimeException("El número de serie es obligatorio y debe ser un alfanumerico de 11 caracteres"
            );
        }
        if (req.getOperatingSystem() == null || !req.getOperatingSystem().matches("^[A-Za-z0-9]{3,50}$")) {
            throw new RuntimeException("El sistema operativo es obligatorio. Puede tener entre 3 y 50 caracteres."
            );
        }
    }
    private void validateUpdate(UpdateAssigned request) {
        if (request.getSerialNumber() == null || !request.getSerialNumber().matches("^[A-Za-z0-9]{11}$")) {
            throw new RuntimeException("El número de serie debe ser un alfanumérico de 11 caracteres");
        }
        if (request.getAssignedTo() != null && request.getAssignedTo() < 0) {
            throw new RuntimeException("El id asignado debe ser numérico y positivo");
        }

    }

}
