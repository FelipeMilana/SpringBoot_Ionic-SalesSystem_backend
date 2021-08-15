package com.projects.SalesSystem.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projects.SalesSystem.entities.dto.PersonDTO;
import com.projects.SalesSystem.services.PersonService;

@RestController
@RequestMapping(value = "/persons")
public class PersonController {

	@Autowired
	private PersonService personService;
	
	@GetMapping(value = "/cpfCnpj")
	public ResponseEntity<PersonDTO> findByCpfCnpj(@RequestParam(value = "value") String cpfCnpj) {
		PersonDTO person = personService.findByCpfCnpj(cpfCnpj);
		return ResponseEntity.ok().body(person);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody PersonDTO objDTO, @PathVariable Long id) {
		personService.update(objDTO, id);
		return ResponseEntity.noContent().build();
	}
}
