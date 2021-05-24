package com.projects.SalesSystem.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.projects.SalesSystem.entities.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long>{

	@Transactional(readOnly = true)
	Page<Vehicle> findByIdIn(List<Long> vehiclesIds, Pageable page);
	
	@Transactional(readOnly = true)
	Page<Vehicle> findByModelContainingIgnoreCaseAndIdIn(String model, List<Long> vehiclesIds, Pageable page);
	
	@Transactional(readOnly = true) 
	Page<Vehicle> findByLicensePlateIgnoreCaseAndIdIn(String licensePlate, List<Long> vehiclesIds, Pageable page);
}
