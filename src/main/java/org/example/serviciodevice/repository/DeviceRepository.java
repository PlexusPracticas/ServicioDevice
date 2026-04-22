package org.example.serviciodevice.repository;
import jakarta.transaction.Transactional;
import org.example.serviciodevice.dto.DeviceDTO;
import org.example.serviciodevice.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device,Integer> {
    Optional<Device> findByAssignedTo(Integer assignedTo);
    Optional<Device> findBySerialNumber(String serialNumber);
    List<Device> findBySerialNumberIn(List<String> serialNumbers);

    @Modifying
    @Transactional
    @Query("DELETE FROM Device d WHERE d.serialNumber IN :serialNumbers")
    int deleteBySerialNumberIn(@Param("serialNumbers") List<String> serialNumbers);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Device d SET d.assignedTo = :assignedTo WHERE d.serialNumber = :serialNumber")
    int updateAssignedToBySerialNumber(@Param("assignedTo") Integer assignedTo,
                                       @Param("serialNumber") String serialNumber);
}


