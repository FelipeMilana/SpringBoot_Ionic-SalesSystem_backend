package com.projects.SalesSystem.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projects.SalesSystem.entities.Person;
import com.projects.SalesSystem.entities.dto.PersonDTO;
import com.projects.SalesSystem.repositories.PersonRepository;
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
	
	public Person findPersonByCpf(String cpf) {
		Person obj = personRepo.findByCpf(cpf);
		
		if(obj == null) {
			throw new ObjectNotFound("Objeto não encontrado! CPF: " + cpf);
		}
		
		return obj;
	}
	
	public PersonDTO findByCpf(String cpf) {
		Person obj = findPersonByCpf(cpf);
		return new PersonDTO(obj); 
	}
	
	
	public void insert(Person obj) {
		personRepo.save(obj);
	}
	
	public void update(PersonDTO objDTO, Long id) {
		Person dbPerson = findPersonById(id);
		dbPerson = updating(dbPerson, objDTO);
		insert(dbPerson);
	}
	
	private Person updating(Person obj, PersonDTO objDTO) {
		obj.setName(objDTO.getName());
		obj.setEmail(objDTO.getEmail());
		obj.setTelephone(objDTO.getTelephone());
		obj.setTelephone2(objDTO.getTelephone2());
		obj.getAddress().setStreet(objDTO.getAddress().getStreet());
		obj.getAddress().setNumber(objDTO.getAddress().getNumber());
		obj.getAddress().setDistrict(objDTO.getAddress().getDistrict());
		obj.getAddress().setPostalCode(objDTO.getAddress().getPostalCode());
		obj.getAddress().setCity(objDTO.getAddress().getCity());
		obj.getAddress().setState(objDTO.getAddress().getState());

		return obj;
	}
}