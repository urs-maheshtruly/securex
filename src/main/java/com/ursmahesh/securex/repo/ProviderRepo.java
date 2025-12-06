package com.ursmahesh.securex.repo;

import com.ursmahesh.securex.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProviderRepo extends JpaRepository<Provider,Integer> {


   Optional<Provider> findByEmail(String email);


    Optional<Provider> findByMobileNumber(String mobileNumber);


}