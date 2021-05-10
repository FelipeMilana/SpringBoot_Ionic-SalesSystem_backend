package com.projects.SalesSystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projects.SalesSystem.entities.Address;
import com.projects.SalesSystem.entities.Person;
import com.projects.SalesSystem.repositories.AddressRepository;
import com.projects.SalesSystem.repositories.PersonRepository;

@Service
public class DBService {

	@Autowired
	private AddressRepository addressRepo;
	@Autowired
	private PersonRepository personRepo;

	public void instantiateDatabase() {
		
		Person person = new Person(null, "Felipe", "Felipe@gmail.com", "456123698", "33126456");

		Address ad1 = new Address(null, "Av. São Carlos", "789", "Centro", "13565789", "São Carlos", "São Paulo", person);
		person.setAddress(ad1);

		addressRepo.save(ad1);
		personRepo.save(person);
	}
}
