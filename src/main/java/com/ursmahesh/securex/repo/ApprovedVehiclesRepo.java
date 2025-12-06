package com.ursmahesh.securex.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ursmahesh.securex.model.ApprovedVehicles;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ApprovedVehiclesRepo extends JpaRepository<ApprovedVehicles, String> {

    @Query("SELECT v FROM ApprovedVehicles v WHERE v.vehicleId NOT IN (" +
            "SELECT b.vehicleId FROM Booking b " +
            "WHERE b.startDate < :endDate AND b.endDate > :startDate)")
    List<ApprovedVehicles> findAvailableVehicles(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

}
