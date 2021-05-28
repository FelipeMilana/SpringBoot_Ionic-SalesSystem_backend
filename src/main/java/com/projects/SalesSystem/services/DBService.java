package com.projects.SalesSystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.projects.SalesSystem.entities.User;
import com.projects.SalesSystem.repositories.UserRepository;

@Service
public class DBService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private BCryptPasswordEncoder encoder;

	public void instantiateDatabase() {
		
		User neto = new User(null, "Neto", "neto@gmail.com", encoder.encode("123"), 200000.00, 500000.00);
		userRepo.save(neto);
	}
}
