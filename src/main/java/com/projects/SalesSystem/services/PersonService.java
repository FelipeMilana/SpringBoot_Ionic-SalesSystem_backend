package com.projects.SalesSystem.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.projects.SalesSystem.entities.Person;
import com.projects.SalesSystem.entities.dto.PersonDTO;
import com.projects.SalesSystem.repositories.PersonRepository;
import com.projects.SalesSystem.services.exceptions.DataIntegrity;
import com.projects.SalesSystem.services.exceptions.ObjectNotFound;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepo;
	
	public Person findPersonById(Long id) {
		Optional<Person> obj = personRepo.findById(id);
		return obj.orElseThrow( () -> 
					new ObjectNotFound("Objeto não encontrado! Id: " + id));
	}
	
	public PersonDTO findById(Long id) {
		Person obj = findPersonById(id);
		return new PersonDTO(obj);
	}
	
	public PersonDTO findByCpf(String cpf) {
		Person obj = personRepo.findByCpf(cpf);
		
		if(obj != null) {
			return new PersonDTO(obj); 
		}
		else {
			throw new ObjectNotFound("Objeto não encontrado! CPF: " + cpf);
		}
	}
	
	
	public void insert(Person obj) {
		personRepo.save(obj);
	}
	
	
	public void delete(Long id) {
		findPersonById(id);
		try {
			personRepo.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrity("Não é possível deletar pois essa pessoa tem elementos associados");
		}
	}
}