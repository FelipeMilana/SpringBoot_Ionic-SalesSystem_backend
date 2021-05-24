package com.projects.SalesSystem.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.projects.SalesSystem.entities.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long>{

	@Transactional(readOnly = true)
	Page<Sale> findByIdIn(List<Long> salesIds, Pageable page);
	
	@Transactional(readOnly = true)
	Page<Sale> findByVehicleLicensePlateIgnoreCaseAndIdIn(String licensePlate, List<Long> salesIds, Pageable page);
	
	@Transactional(readOnly = true)
	Page<Sale> findByVehicleModelContainingIgnoreCaseAndIdIn(String model, List<Long> salesIds, Pageable page);
	
	@Transactional(readOnly = true)
	Page<Sale> findByDateBetweenAndIdIn(LocalDate startDate, LocalDate endDate, List<Long> salesIds, Pageable page);
}
