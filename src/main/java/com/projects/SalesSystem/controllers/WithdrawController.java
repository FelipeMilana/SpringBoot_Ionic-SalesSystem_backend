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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projects.SalesSystem.entities.dto.WithdrawDTO;
import com.projects.SalesSystem.services.WithdrawService;

@RestController
@RequestMapping(value = "/withdraws")
public class WithdrawController {

	@Autowired
	private WithdrawService withdrawService;
	
	@GetMapping
	public ResponseEntity<Page<WithdrawDTO>> myWithdraws(@PageableDefault(page = 0, size = 10, sort = "date", direction = Sort.Direction.DESC) Pageable page) {
		Page<WithdrawDTO> withdraws = withdrawService.myWithdraws(page);
		return ResponseEntity.ok().body(withdraws);
	}
	
	@GetMapping(value = "/nameOrBank")
	public ResponseEntity<Page<WithdrawDTO>> findByNameorBank(@PageableDefault(page = 0, size = 10, sort = "date", direction = Sort.Direction.DESC) Pageable page,
																@RequestParam(value = "value") String str) {
		Page<WithdrawDTO> withdraws = withdrawService.findByNameOrBank(page, str);
		return ResponseEntity.ok().body(withdraws);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody WithdrawDTO objDTO) {
		WithdrawDTO obj = withdrawService.insertFromDTO(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> insert(@PathVariable Long id) {
		withdrawService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
