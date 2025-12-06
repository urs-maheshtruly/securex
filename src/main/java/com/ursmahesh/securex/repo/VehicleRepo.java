package com.ursmahesh.securex.repo;

import com.ursmahesh.securex.model.Vehicle;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepo extends JpaRepository<Vehicle,String> {
    Optional<Vehicle> findByVehicleId(String vehicleId);

    @Transactional
    @Modifying
  //  @Query("DELETE FROM vehicles v WHERE v.vehicleId = :vehicleId")
    void deleteByVehicleId( @Param("vehicleId") String vehicleId);
}
