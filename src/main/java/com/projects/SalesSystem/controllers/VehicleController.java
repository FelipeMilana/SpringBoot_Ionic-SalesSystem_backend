package com.projects.SalesSystem.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projects.SalesSystem.entities.dto.PersonDTO;
import com.projects.SalesSystem.entities.dto.VehicleDTO;
import com.projects.SalesSystem.services.VehicleService;

@RestController
@RequestMapping(value = "/vehicles")
public class VehicleController {

	@Autowired
	private VehicleService vehicleService;
	
	
	@GetMapping(value = "/stock")
	public ResponseEntity<Page<VehicleDTO>> myStock(@PageableDefault(page = 0, size = 10, sort = "brand", direction = Sort.Direction.ASC) Pageable page) {
		Page<VehicleDTO> vehicles = vehicleService.myStock(page);
		return ResponseEntity.ok().body(vehicles);
	}
	
	@GetMapping(value = "/modelOrLicensePlate")
	public ResponseEntity<Page<VehicleDTO>> findByModelOrLicensePlate(@PageableDefault(page = 0, size = 10, sort = "brand", direction = Sort.Direction.ASC) Pageable page,
																@RequestParam(value = "value") String str) {
		Page<VehicleDTO> vehicles = vehicleService.findByModelOrLicensePlate(page, str);
		return ResponseEntity.ok().body(vehicles);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody VehicleDTO objDTO) {
		VehicleDTO vehicle = vehicleService.insertFromDTO(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(vehicle.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody VehicleDTO objDTO, @PathVariable Long id) {
		vehicleService.update(objDTO, id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/seller/{id}")
	public ResponseEntity<Void> updateSeller(@Valid @RequestBody PersonDTO objDTO, @PathVariable Long id) {
		vehicleService.updateSeller(objDTO, id);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		vehicleService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
