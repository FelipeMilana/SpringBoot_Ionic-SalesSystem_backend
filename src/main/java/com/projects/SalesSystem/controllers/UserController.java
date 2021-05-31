package com.projects.SalesSystem.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projects.SalesSystem.entities.dto.BalanceDTO;
import com.projects.SalesSystem.entities.dto.UserDTO;
import com.projects.SalesSystem.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
		UserDTO user = userService.findById(id);
		return ResponseEntity.ok().body(user);
	}
	
	@GetMapping(value = "/email")
	public ResponseEntity<UserDTO> findById(@RequestParam(value = "value") String email) {
		UserDTO user = userService.findByEmail(email);
		return ResponseEntity.ok().body(user);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody UserDTO objDTO) {
		UserDTO user = userService.insertFromDTO(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody UserDTO objDTO, @PathVariable Long id) {
		userService.update(id, objDTO);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}/updateBalance")
	public ResponseEntity<Void> updateBalance(@Valid @RequestBody BalanceDTO objDTO, @PathVariable Long id, @RequestParam(value = "bank") String bank) {
		userService.updateBalance(objDTO, id, bank);
		return ResponseEntity.noContent().build();
	}
}
