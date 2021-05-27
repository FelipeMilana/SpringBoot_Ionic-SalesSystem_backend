package com.projects.SalesSystem.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projects.SalesSystem.entities.dto.ExpenseDTO;
import com.projects.SalesSystem.services.ExpenseService;

@RestController
@RequestMapping(value = "/expenses")
public class ExpenseController {

	@Autowired
	private ExpenseService expService;
	
	@PostMapping(value = "/{id}")
	public ResponseEntity<Void> insert(@Valid @RequestBody ExpenseDTO objDTO, @PathVariable Long id) {
		ExpenseDTO expDTO = expService.insertExpenses(id, objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(expDTO.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		expService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
