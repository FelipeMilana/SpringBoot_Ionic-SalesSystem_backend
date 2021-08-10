package com.projects.SalesSystem.controllers;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projects.SalesSystem.entities.dto.SaleDTO;
import com.projects.SalesSystem.services.SaleService;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService saleService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleDTO> findById(@PathVariable Long id) {
		SaleDTO sale = saleService.findById(id);
		return ResponseEntity.ok().body(sale);
	}
	
	@GetMapping
	public ResponseEntity<Page<SaleDTO>> mySales(@PageableDefault(page = 0, size = 10, sort = "date", direction = Sort.Direction.DESC) Pageable page) {
		Page<SaleDTO> sales = saleService.mySales(page);
		return ResponseEntity.ok().body(sales);
	}
	
	@GetMapping(value = "/vehicleModelOrLicensePlate")
	public ResponseEntity<Page<SaleDTO>> findByVehicleModelOrLicensePlate(@PageableDefault(page = 0, size = 10, sort = "date", direction = Sort.Direction.DESC) Pageable page,
																		  @RequestParam(value = "value") String str) {
		Page<SaleDTO> sales = saleService.findByVehicleModelOrLicensePlate(page, str);
		return ResponseEntity.ok().body(sales);
	}
	
	@GetMapping(value = "/monthlyReport")
	public ResponseEntity<Page<SaleDTO>> monthlyReport(@PageableDefault(page = 0, size = 10, sort = "date", direction = Sort.Direction.ASC) Pageable page,
													   @RequestParam(value = "startDate") String startDate,
													   @RequestParam(value = "endDate") String endDate) {
		
		LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		Page<SaleDTO> sales = saleService.monthlyReport(page, start, end);
		return ResponseEntity.ok().body(sales);
	}
	
	@PostMapping(value = "/{id}")
	public ResponseEntity<Void> insert(@Valid @RequestBody SaleDTO objDTO, @PathVariable Long id) {
		SaleDTO saleDTO = saleService.insertFromDTO(id, objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saleDTO.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
}
